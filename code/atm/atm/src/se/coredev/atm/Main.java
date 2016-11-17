package se.coredev.atm;

import java.util.Date;
import java.util.Optional;

import se.coredev.atm.bank.Bank;
import se.coredev.atm.bank.BankReceipt;
import se.coredev.atm.bank.BankReposiotry;
import se.coredev.atm.exception.ATMException;
import se.coredev.atm.service.ATM;
import se.coredev.atm.service.ATMCard;
import se.coredev.atm.service.ATMContext;

public final class Main {

	public static void main(String[] args) throws ATMException {
		
		BankReposiotry bankReposiotry = (bankId) -> Optional.of(new FakeBank(bankId));

		ATM atm = new ATM(bankReposiotry);

		ATMCard card = new ATMCard("1001", "2002", 1234);
		ATMContext session = atm.verifyPin(1234, card);

		long transactionId = session.withdrawAmount(1000);

		System.out.println("Transaction id: " + transactionId);
		System.out.println(session.requestReceipt(transactionId));
	}

	private static final class FakeBank implements Bank {

		private final String bankId;

		public FakeBank(String bankId) {
			this.bankId = bankId;
		}

		@Override
		public String getBankId() {
			return bankId;
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
