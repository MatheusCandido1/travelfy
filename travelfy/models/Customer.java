package travelfy.models;

public class Customer extends User {
	int customerId;
	String firstName;
	String lastName;
	
	public Customer() {}

	public int getCustomerId() {
		return id;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
