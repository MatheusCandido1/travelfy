package travelfy.models;

import java.util.Date;

public class Reservation {
	String id;
	Date startDate;
	Date endDate;
	String customerId;
	String attractionId;
	int numOfPeople;
	double subtotal;
	double tax;
	double total;
	
	
	Reservation() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(String attractionId) {
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
