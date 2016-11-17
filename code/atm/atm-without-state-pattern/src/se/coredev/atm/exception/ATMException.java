package se.coredev.atm.exception;

public final class ATMException extends Exception {

	private static final long serialVersionUID = -2736015900390902291L;

	public ATMException(String message, Throwable cause) {
		super(message, cause);
	}

	public ATMException(String message) {
		super(message);
	}
	
}
