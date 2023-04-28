package travelfy.dao;

import java.util.ArrayList;

import travelfy.models.Attraction;
import travelfy.models.Reservation;

public interface ReservationDAO {
	   public String create(Reservation reservation);
	   public ArrayList<Reservation> getReservartionByAttractionId(int attractionId);
	   public ArrayList<Reservation> getReservartionByVendorId(int vendorId);
	   public ArrayList<Reservation> getReservartionByCustomerId(int customerId);
	   public boolean updateStatus(Reservation reservation, String status);
}
