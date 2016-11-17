package se.coredev.atm.service;

import se.coredev.atm.exception.ATMException;

public final class ATMContext {

	private ATMOperation operation;
	
	// Package private
	ATMContext(ATMOperation startOperation) {
		operation = startOperation;
	}

	// Package private
	void setOperaiton(ATMOperation newOperation) {
		operation = newOperation;
	}

	public long withdrawAmount(int amount) throws ATMException {
		if (amount % 100 == 0 && amount >= 100 && amount <= 10000) {
			return operation.withdrawAmount(amount, this);
		}
		throw new ATMException("Invalid amount");
	}

	public ATMReceipt requestReceipt(long transactionId) throws ATMException {
		return operation.requestReceipt(transactionId, this);
	}

	public long checkBalance() throws ATMException {
		return operation.checkBalance(this);
	}

}
