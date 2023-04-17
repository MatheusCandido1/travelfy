package travelfy.models;

public class Customer extends User {
	String id;
	String firstName;
	String lastName;
	
	public Customer() {}

	public String getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
