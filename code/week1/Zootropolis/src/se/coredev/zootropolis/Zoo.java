package se.coredev.zootropolis;

import se.coredev.zootropolis.storage.AnimalStorage;

public final class Zoo {

	private final AnimalStorage storage;
	
	public Zoo(AnimalStorage storage){
		this.storage = storage;
	}
}
