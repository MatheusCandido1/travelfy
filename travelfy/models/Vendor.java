package travelfy.models;

public class Vendor extends User {
	String name;
	String businessIdentificationNumber;
	
	public Vendor() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessIdentificationNumber() {
		return businessIdentificationNumber;
	}

	public void setBusinessIdentificationNumber(String businessIdentificationNumber) {
		this.businessIdentificationNumber = businessIdentificationNumber;
	}
}
