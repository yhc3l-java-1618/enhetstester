package se.coredev.atm.exception;

public final class ATMSecurityException extends RuntimeException {

	private static final long serialVersionUID = -6376870940290812999L;

	public ATMSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public ATMSecurityException(String message) {
		super(message);
	}

}
