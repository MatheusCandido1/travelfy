package travelfy.models;

public class User {
	String id;
	String phone;
	String email;
	String password;
	
	public User() {}

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
