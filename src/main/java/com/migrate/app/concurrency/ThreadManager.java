package com.migrate.app.concurrency;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.migrate.app.entity.AceMigMaster;
import com.migrate.app.repository.AceMigMasterRepository;

public class ThreadManager {
	
	
	private static final int MAX_THREADS = 10;
	private ExecutorService executorService;
	
	public ThreadManager() {
		this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
	}
	
	public void proceesMessage(List<AceMigMaster> list, AceMigMaster aceMigMaster, AceMigMasterRepository aceMigMasterRepository) {
		executorService.execute(new ConsumerProcessingThread(list, aceMigMasterRepository, aceMigMaster));
	}
	
	public void shutdown() {
		executorService.shutdown();
	}

}
