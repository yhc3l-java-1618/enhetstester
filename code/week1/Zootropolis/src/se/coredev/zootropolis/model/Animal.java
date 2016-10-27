package se.coredev.zootropolis.model;

public abstract class Animal {

	private final String number;
	private final boolean dangerous;
	
	protected Animal(String number, boolean dangerous) {
		this.number = number;
		this.dangerous = dangerous;
	}

	public String getNumber() {
		return number;
	}
	
	public boolean isDangerous() {
		return dangerous;
	}
}
