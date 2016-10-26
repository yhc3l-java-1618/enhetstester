package se.coredev.testing.model;

public final class User {

	private final String number;
	private final String username;
	private final String password;
	
	public User(String number, String username, String password) {
		this.number = number;
		this.username = username;
		this.password = password;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public int hashCode() {
		
		int result = 7;
		result += number.hashCode() * 37;
		result += username.hashCode() * 37;
		
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		
		if(this == other){ return true; }
		
		if(other instanceof User) {
			User otherUser = (User) other;
			return number.equals(otherUser.number) && username.equals(otherUser.username);
		}
		
		return false;
	}
	
}









