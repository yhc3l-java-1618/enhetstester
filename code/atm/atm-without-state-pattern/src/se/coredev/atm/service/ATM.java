package se.coredev.atm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import se.coredev.atm.exception.ATMException;
import se.coredev.atm.exception.ATMSecurityException;
import se.coredev.atm.model.ATMCard;
import se.coredev.atm.model.ATMReceipt;
import se.coredev.atm.model.BankReceipt;

public final class ATM {

	private final Map<String, Bank> banks;

	public ATM(List<Bank> banks) {
		if (banks == null || banks.isEmpty()) { throw new IllegalArgumentException("Banks must not be null or empty"); }
		this.banks = new HashMap<>();
		banks.forEach(b -> this.banks.put(b.getBankId(), b));
	}

	public ATMSession verifyPin(int pin, ATMCard card) {
		if (card.verifyPin(pin)) { 
			return new ATMSessionImpl(getBank(card), card.getAccountHolderId()); 
		}
		throw new ATMSecurityException("Invalid pin");
	}

	private Bank getBank(ATMCard card) {
		if (banks.containsKey(card.getBankId())) { 
			return banks.get(card.getBankId()); 
		}
		throw new ATMSecurityException("Missing bank");
	}

	private final class ATMSessionImpl implements ATMSession {

		private final Bank bank;
		private final String accountHolderId;
		private final AtomicBoolean sessionAlive;
		private final AtomicBoolean receiptPossible;

		private ATMSessionImpl(Bank bank, String accountHolderId) {
			this.bank = bank;
			this.accountHolderId = accountHolderId;
			this.sessionAlive = new AtomicBoolean(true);
			this.receiptPossible = new AtomicBoolean(false);
		}

		@Override
		public long withdrawAmount(int amount) throws ATMException {

			if (sessionAlive.getAndSet(false)) {
				long transactionId = bank.withdrawAmount(amount);
				receiptPossible.set(true);

				return transactionId;
			}
			throw new ATMException("Session has expired");
		}

		@Override
		public ATMReceipt requestReceipt(long transactionId) throws ATMException {

			if (receiptPossible.getAndSet(false)) {
				BankReceipt bankReceipt = bank.requestReceipt(transactionId);
				return new ATMReceipt(bankReceipt);
			}
			throw new ATMException("Session has expired or request receipt not possible");
		}

		@Override
		public long checkBalance() throws ATMException {

			if (sessionAlive.getAndSet(false)) {
				return bank.getBalance(accountHolderId); 
			}
			throw new ATMException("Session has expired");
		}
	}

}
