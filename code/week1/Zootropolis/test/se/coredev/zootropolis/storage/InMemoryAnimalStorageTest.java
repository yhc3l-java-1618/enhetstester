package se.coredev.zootropolis.storage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.coredev.zootropolis.model.Animal;
import se.coredev.zootropolis.model.Horse;

public class InMemoryAnimalStorageTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none(); 
	
//	@Test(expected = StorageException.class)
	@Test
	public void shouldNotAllowDangerousAnimals() {
		
		expectedException.expect(StorageException.class);
		expectedException.expectMessage("Dangerous animals not allowed");
		
		AnimalStorage storage = new InMemoryAnimalStorage(false);
		storage.addAnimal(new Cobra("z-4"));
	}

	@Test
	public void canAddAnimal() {

		AnimalStorage storage = new InMemoryAnimalStorage();
		String number = "G-17";

		Animal animal = new Horse(number);
		storage.addAnimal(animal);

		Animal animalFromStorage = storage.getAnimalByNumber(number);
		
//		assertEquals(animal, fromStorage);
//		assertThat(animal, equalTo(animalFromStorage));
		assertThat(animal, is(animalFromStorage));
	}

}














