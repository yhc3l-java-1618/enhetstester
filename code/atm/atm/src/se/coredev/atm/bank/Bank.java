package se.coredev.atm.bank;

public interface Bank {
	
	String getBankId();

	long getBalance(String accountHolderId);

	long withdrawAmount(int amount);

	BankReceipt requestReceipt(long transactionId);
	
}
