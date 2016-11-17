package se.coredev.atm.bank;

import java.util.Optional;

@FunctionalInterface
public interface BankReposiotry {

	Optional<Bank> getBank(String bankId);
	
}
