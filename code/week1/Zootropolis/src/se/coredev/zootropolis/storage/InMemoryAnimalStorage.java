package se.coredev.zootropolis.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import se.coredev.zootropolis.model.Animal;

public final class InMemoryAnimalStorage implements AnimalStorage {

	private final Map<String, Animal> animals;
	private final boolean allowDangerousAnimals;

	public InMemoryAnimalStorage() {
		this(true);
	}

	public InMemoryAnimalStorage(boolean allowDangerousAnimals) {
		this.allowDangerousAnimals = allowDangerousAnimals;
		this.animals = new HashMap<>();
	}

	@Override
	public void addAnimal(Animal animal) {
		
		if (animal.getNumber().length() < 3) {
			throw new StorageException("Number too short");
		}
		
		if (!allowDangerousAnimals && animal.isDangerous()) { 
			throw new StorageException("Dangerous animals not allowed"); 
		}
		animals.put(animal.getNumber(), animal);
	}

	@Override
	public Set<Animal> getAllAnimals() {
		return null;
	}

	@Override
	public Animal getAnimalByNumber(String number) {
		return animals.get(number);
	}

	@Override
	public Animal removeAnimal(String number) {
		return null;
	}

}
