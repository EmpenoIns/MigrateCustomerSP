package com.migrate.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.migrate.app.entity.AceMigMaster;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

		@Bean
		public ConsumerFactory<String, AceMigMaster> consumerFactory() {
		    JsonDeserializer<AceMigMaster> deserializer = new JsonDeserializer<>(AceMigMaster.class);
		    deserializer.setRemoveTypeHeaders(false);
		    deserializer.addTrustedPackages("*");
		    deserializer.setUseTypeMapperForKey(true);

		    Map<String, Object> configProps = new HashMap<>();
		    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "cust-migration-group");
		    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//		    configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//		    configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.migrate.app.entity.AceMigMaster");

		    return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
		}

		@Bean
		public ConcurrentKafkaListenerContainerFactory<String, AceMigMaster> kafkaListenerContainerFactory() {
		    ConcurrentKafkaListenerContainerFactory<String, AceMigMaster> factory =
		        new ConcurrentKafkaListenerContainerFactory<>();

		    factory.setConsumerFactory(consumerFactory());
		    return factory; 
		}

}
