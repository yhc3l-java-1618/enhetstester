package se.coredev.testing.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class UserTest {
	
	@BeforeClass
	public static void setupClass(){
		System.out.println("setupClass()");
	}
	
	@Before
	public void setup(){
		System.out.println("setup()");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("tearDownClass()");
	}
	
	@After
	public void tearDown(){
		System.out.println("tearDown()");
	}
	
	@Test
	public void usersWithIdenticalValuesShouldBeEqual() {
		System.out.println("usersWithIdenticalValuesShouldBeEqual");
		User user1 = new User("1001", "luke", "secret");
		User user2 = new User("1001", "luke", "secret");
		                                                        
		assertEquals(user1, user2);
	}

	@Test
	public void usersThatAreEqualShouldProduceSameHashCode() {
		System.out.println("usersThatAreEqualShouldProduceSameHashCode");
		User user1 = new User("1001", "luke", "secret");
		User user2 = new User("1001", "luke", "secret");
		
		assertEquals(user1.hashCode(), user2.hashCode());
	}
	
}
