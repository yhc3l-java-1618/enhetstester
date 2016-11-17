package se.coredev.atm.service;

import se.coredev.atm.bank.Bank;
import se.coredev.atm.exception.ATMException;

abstract class ATMOperation {

	static final ATMOperation TERMINATED = new ATMOperation(null, null) {};
	
	protected final Bank bank;
	protected final String accountHolderId;

	protected ATMOperation(Bank bank, String accountHolderId) {
		this.bank = bank;
		this.accountHolderId = accountHolderId;
	}

	public long withdrawAmount(int amount, ATMContext context) throws ATMException {
		throw new ATMException("Invalid operation");
	}

	public ATMReceipt requestReceipt(long transactionId, ATMContext context) throws ATMException {
		throw new ATMException("Invalid operation");
	}

	public long checkBalance(ATMContext context) throws ATMException {
		throw new ATMException("Invalid operation");
	}

}
