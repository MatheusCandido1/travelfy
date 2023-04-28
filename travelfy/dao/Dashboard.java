package travelfy.dao;

import java.util.ArrayList;

public class Dashboard {
	double totalRevenue = 0;
	ArrayList<String> mostFamousAttractions;
	int approvedReservations = 0;
	int pendingReservations = 0; 
	
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public ArrayList<String> getMostFamousAttractions() {
		return mostFamousAttractions;
	}
	public void setMostFamousAttractions(ArrayList<String> mostFamousAttractions) {
		this.mostFamousAttractions = mostFamousAttractions;
	}
	public int getApprovedReservations() {
		return approvedReservations;
	}
	public void setApprovedReservations(int reservations) {
		this.approvedReservations = reservations;
	}
	public int getPendingReservations() {
		return pendingReservations;
	}
	public void setPendingReservations(int pendingReservations) {
		this.pendingReservations = pendingReservations;
	}
}
