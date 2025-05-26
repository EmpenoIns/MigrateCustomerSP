package com.migrate.app.concurrency;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.migrate.app.entity.AceMigMaster;
import com.migrate.app.repository.AceMigMasterRepository;

@EnableScheduling
public class ConsumerProcessingThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerProcessingThread.class);

	List<AceMigMaster> list;
	AceMigMasterRepository aceMigMasterRepository;
	AceMigMaster aceMigMaster;

	public ConsumerProcessingThread(List<AceMigMaster> list, AceMigMasterRepository aceMigMasterRepository,
			AceMigMaster aceMigMaster) {
		this.list = Collections.synchronizedList(list);
		this.aceMigMasterRepository = aceMigMasterRepository;
		this.aceMigMaster = aceMigMaster;
	}

	@Override
	public void run() {

		try {
			aceMigMaster.setProcInd("Locked");
			aceMigMaster.setUpdateDate(Date.valueOf(LocalDate.now()));
			aceMigMaster.setExecSeq(Thread.currentThread().getName());
			aceMigMasterRepository.save(aceMigMaster);
			logger.info("Locked Customer {} in thread {}", aceMigMaster.getLgcCustID(),
					Thread.currentThread().getName());
		} catch (Exception e) {
			logger.error("Failed to process customer {}: {}", aceMigMaster.getLgcCustID());
		}

	}

}
