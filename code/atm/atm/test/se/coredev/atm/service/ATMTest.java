package se.coredev.atm.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.coredev.atm.bank.Bank;
import se.coredev.atm.bank.BankReceipt;
import se.coredev.atm.bank.BankReposiotry;
import se.coredev.atm.exception.ATMException;
import se.coredev.atm.exception.ATMSecurityException;

@RunWith(MockitoJUnitRunner.class)
public final class ATMTest {

	@Mock
	private Bank bank;

	@Mock
	private BankReposiotry bankReposiotry;

	@InjectMocks
	private ATM atm;

	private ATMCard card;

	private final String accountHolderId = "1001";
	private final String bankId = "100001";
	private final int pin = 1234;
	private final long transactionId = 1122334455;
	private final long balance = 20000;
	private final int amount = 1000;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setup() {

		// Setup mock
		when(bankReposiotry.getBank(bankId)).thenReturn(Optional.of(bank));
		when(bank.getBankId()).thenReturn(bankId);
		when(bank.getBalance(accountHolderId)).thenReturn(balance);
		when(bank.requestReceipt(transactionId)).thenReturn(new BankReceipt(bankId, transactionId, amount, new Date()));
		when(bank.withdrawAmount(amount)).thenReturn(transactionId);

		// Setup test objects
		card = new ATMCard(accountHolderId, bankId, pin);
	}

	@Test
	public void canVerifyPin() {

		ATMContext session = atm.verifyPin(pin, card);
		assertThat(session, is(notNullValue()));

		verify(bankReposiotry).getBank(bankId);
	}

	@Test
	public void willThrowExceptionOnInvalidPin() {

		expectedException.expect(ATMSecurityException.class);
		expectedException.expectMessage("Invalid pin");

		int invalidPin = 1000;
		atm.verifyPin(invalidPin, card);
	}

	@Test
	public void canWithdrawAmount() throws ATMException {

		ATMContext session = atm.verifyPin(pin, card);
		long transactionId = session.withdrawAmount(1000);

		assertThat(transactionId, is(this.transactionId));
	}

	@Test
	public void shouldTerminateSessionAfterWithdraw() throws ATMException {

		expectedException.expect(ATMException.class);
		expectedException.expectMessage("Invalid operation");

		ATMContext session = atm.verifyPin(pin, card);

		session.withdrawAmount(amount);
		session.withdrawAmount(amount);
	}

	@Test
	public void shouldTerminateSessionAfterCheckBalance() throws ATMException {
		
		expectedException.expect(ATMException.class);
		expectedException.expectMessage("Invalid operation");
		
		ATMContext session = atm.verifyPin(pin, card);
		session.checkBalance();
		session.withdrawAmount(amount);
	}

	@Test
	public void canRequestReceiptAfterWithdraw() throws ATMException {

		ATMContext session = atm.verifyPin(pin, card);
		long transactionId = session.withdrawAmount(amount);
		ATMReceipt receipt = session.requestReceipt(transactionId);

		assertThat(receipt, is(notNullValue()));
		assertThat(receipt.getAmount(), is(amount));
		assertThat(receipt.getTransactionId(), is(transactionId));
	}

}
