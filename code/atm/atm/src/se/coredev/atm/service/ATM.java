package se.coredev.atm.service;

import se.coredev.atm.bank.Bank;
import se.coredev.atm.bank.BankReposiotry;
import se.coredev.atm.exception.ATMException;
import se.coredev.atm.exception.ATMSecurityException;

public final class ATM {

	private final BankReposiotry bankReposiotry;

	public ATM(BankReposiotry bankReposiotry) {
		this.bankReposiotry = bankReposiotry;
	}

	public ATMContext verifyPin(int pin, ATMCard card) {
		if (card.verifyPin(pin)) {
			return new ATMContext(new ATMStartOperation(getBank(card), card.getAccountHolderId()));
		}
		throw new ATMSecurityException("Invalid pin");
	}

	private Bank getBank(ATMCard card) {
		return bankReposiotry.getBank(card.getBankId()).orElseThrow(() -> new ATMSecurityException("Missing bank"));
	}

	// Operations
	private static final class ATMStartOperation extends ATMOperation {

		public ATMStartOperation(Bank bank, String accountHolderId) {
			super(bank, accountHolderId);
		}

		@Override
		public long withdrawAmount(int amount, ATMContext context) throws ATMException {
			long transactionId = bank.withdrawAmount(amount);
			context.setOperaiton(new ATMReceiptOperation(bank, accountHolderId));
			return transactionId;
		}

		@Override
		public long checkBalance(ATMContext context) throws ATMException {
			long balance = bank.getBalance(accountHolderId);
			context.setOperaiton(ATMOperation.TERMINATED);
			return balance;
		}
	}

	private static final class ATMReceiptOperation extends ATMOperation {

		public ATMReceiptOperation(Bank bank, String accountHolderId) {
			super(bank, accountHolderId);
		}

		@Override
		public ATMReceipt requestReceipt(long transactionId, ATMContext context) throws ATMException {
			ATMReceipt receipt = new ATMReceipt(bank.requestReceipt(transactionId));
			context.setOperaiton(ATMOperation.TERMINATED);
			return receipt;
		}
	}

}