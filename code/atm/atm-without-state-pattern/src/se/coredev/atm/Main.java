package se.coredev.atm;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import se.coredev.atm.exception.ATMException;
import se.coredev.atm.model.ATMCard;
import se.coredev.atm.model.ATMReceipt;
import se.coredev.atm.model.BankReceipt;
import se.coredev.atm.service.ATM;
import se.coredev.atm.service.ATMSession;
import se.coredev.atm.service.Bank;

public final class Main {

	public static void main(String[] args) throws ATMException {
		
		List<Bank> banks = Arrays.asList(new FakeBank());
		ATM atm = new ATM(banks);
		
		ATMCard card = new ATMCard("1001", "2002", 1234);
		ATMSession session = atm.verifyPin(1234, card);
		long transactionId = session.withdrawAmount(500);
		session.checkBalance();
		ATMReceipt receipt = session.requestReceipt(transactionId);
//		long balance = session.checkBalance();
		System.out.println(receipt);
				
	}

	private static final class FakeBank implements Bank {

		@Override
		public String getBankId() {
			return "2002";
		}

		@Override
		public long getBalance(String accountHolderId) {
			return 1000;
		}

		@Override
		public long withdrawAmount(int amount) {
			return 123456789;
		}

		@Override
		public BankReceipt requestReceipt(long transactionId) {
			return new BankReceipt(getBankId(), transactionId, 1000, new Date());
		}
	}
}
