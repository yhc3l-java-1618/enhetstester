package se.coredev.atm.model;

import java.util.Date;

public final class ATMReceipt {

	private final long transactionId;
	private final int amount;
	private final Date date;
	
	public ATMReceipt(BankReceipt bankReceipt) {
		transactionId = bankReceipt.getTransactionId();
		amount = bankReceipt.getAmount();
		date = new Date(bankReceipt.getDate().getTime());
	}

	public long getTransactionId() {
		return transactionId;
	}

	public int getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}
}
