package se.coredev.atm.service;

import se.coredev.atm.exception.ATMException;
import se.coredev.atm.model.ATMReceipt;

public interface ATMSession {

	long withdrawAmount(int amount) throws ATMException;

	ATMReceipt requestReceipt(long transactionId) throws ATMException;

	long checkBalance() throws ATMException;

}
