package travelfy.models;

public class Vendor extends User {
	int vendorId;
	String name;
	String businessIdentificationNumber;
	
	public Vendor() {}
	
	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

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
