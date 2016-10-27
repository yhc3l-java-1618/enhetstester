package se.coredev.zootropolis.storage;

import java.util.Set;

import se.coredev.zootropolis.model.Animal;

public interface AnimalStorage {

	void addAnimal(Animal animal);
	
	Set<Animal> getAllAnimals();
	
	Animal getAnimalByNumber(String number);
	
	Animal removeAnimal(String number);
	
}
