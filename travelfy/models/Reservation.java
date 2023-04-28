package travelfy.models;

public class Reservation {
	int id;
	String startDate;
	String status;
	int customerId;
	int attractionId;
	int numOfPeople;
	double subtotal;
	double tax;
	double total;
	String customerEmail;
	String attractionName;
	
	
	public Reservation() {}
	
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}
	
	public String getAttractionName() {
		return attractionName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(int attractionId) {
		this.attractionId = attractionId;
	}
	public int getNumOfPeople() {
		return numOfPeople;
	}
	public void setNumOfPeople(int numOfPeople) {
		this.numOfPeople = numOfPeople;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
	
}
