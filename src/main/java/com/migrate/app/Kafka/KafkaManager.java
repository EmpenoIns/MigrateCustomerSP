package com.migrate.app.Kafka;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.migrate.app.entity.AceMigMaster;
import com.migrate.app.service.AceMigService;

@Component
public class KafkaManager {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaManager.class);
	
	@Autowired
	AceMigService aceMigService;
	
	@KafkaListener(topics = "migForCust", groupId = "cust-migration-group")
public void readFromKafkaQueue(@Payload AceMigMaster message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
		logger.info("Received message with key: {} and value: {}", key, message);
		
		if (message.getLgcCustID() != null) {
			List<Long> ids = Collections.singletonList(message.getLgcCustID());
			aceMigService.pushCustomerToDatabase(ids);
			logger.info("Stored legecyCustomerId {} into DB with genarated TAR ID.", message.getLgcCustID());
		}else {
			logger.warn("Invalid message received: {}", message);
		}

	}
}
