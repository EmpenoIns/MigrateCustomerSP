package com.migrate.app.impl;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migrate.app.concurrency.ThreadManager;
import com.migrate.app.entity.AceMigMaster;
import com.migrate.app.repository.AceMigMasterRepository;
import com.migrate.app.service.AceMigService;

@Service
public class AceMigServiceImpl implements AceMigService {
	private static final Logger logger = LoggerFactory.getLogger(AceMigServiceImpl.class);

	@Autowired
	AceMigMasterRepository aceMigMasterRepository;

	@Override
	public void pushCustomerToDatabase(List<Long> list) {
		List<Long> existingId = aceMigMasterRepository.findAll()
				.stream().map(AceMigMaster::getLgcCustID)
				.collect(Collectors.toList());

		for (Long id : list) {
			if (!existingId.contains(id)) {
				String tarId = generateTargetCustomerId();
				AceMigMaster newEntry = new AceMigMaster();
				Date date = Date.valueOf(LocalDate.now());
				newEntry.setLgcCustID(id);
				newEntry.setTarCustID(tarId);
				newEntry.setCreateDate(date);
				newEntry.setProcInd("IN");
				newEntry.setProcDescription("Customer Intilized");
				newEntry.setExecSeq(Thread.currentThread().getName());
				aceMigMasterRepository.save(newEntry);
				logger.info("Inserted legecyCustomerId {} with TAR Id {} into DB.", id, newEntry.getLgcCustID());
			} else {
				logger.info("legecyCustomerId {} already exists, skipping inserted.", id);
			}
		}

	}

	// To Generate Tar_Cust_Id
	private String generateTargetCustomerId() {
		int length = 30;
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	@Override
	public void processEligibleCustomers() {
		List<AceMigMaster> eligible = aceMigMasterRepository.findByProcInd("IN");
		
		ThreadManager threadManager = new ThreadManager();
		for(AceMigMaster ace : eligible) {
			threadManager.proceesMessage(eligible, ace, aceMigMasterRepository);
		}
		threadManager.shutdown();
		logger.info("Proccesing of eligible customers completed.");

	}

}
