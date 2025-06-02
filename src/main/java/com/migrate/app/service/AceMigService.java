package com.migrate.app.service;

import java.util.List;

public interface AceMigService {
	
	void pushCustomerToDatabase(List<Long> list);
	
	void processEligibleCustomers();

}
