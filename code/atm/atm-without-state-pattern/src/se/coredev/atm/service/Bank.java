package se.coredev.atm.service;

import se.coredev.atm.model.BankReceipt;

public interface Bank {
	
	String getBankId();

	long getBalance(String accountHolderId);

	long withdrawAmount(int amount);

	BankReceipt requestReceipt(long transactionId);
	
}
