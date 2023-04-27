package travelfy.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import travelfy.db.Utils;
import travelfy.models.Reservation;

public class ReservationDAOImpl implements ReservationDAO {

	static public final String RESERVATION_TABLE_NAME = "reservations";

	@Override
	public String create(Reservation reservation) { 
		Connection conn = Utils.getDBConnection();
	    try {
	    	
	    	
	    	//String query = "SELECT * FROM reservations WHERE attraction_id = ?";
	    	String query ="SELECT * FROM reservations WHERE attraction_id = ? AND (start_date <= ? AND end_date > ?)";
	        PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, reservation.getAttractionId());
			preparedStmt.setString(2,reservation.getStartDate());
			preparedStmt.setString(3,reservation.getEndDate());
			
	        ResultSet result = preparedStmt.executeQuery();
	        
	        if(result.next()) {
	        	return "INVALID_DATE";
	        }

	    	
	    	
	        String query2 = "INSERT INTO " + RESERVATION_TABLE_NAME + " (start_date, end_date, customer_id, attraction_id, num_of_people, subtotal, tax, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement preparedStmt2 = conn.prepareStatement(query2);

	        preparedStmt2.setString(1,reservation.getStartDate());
	        preparedStmt2.setString(2, reservation.getEndDate());
	        preparedStmt2.setInt(3, reservation.getCustomerId());
	        preparedStmt2.setInt(4, reservation.getAttractionId());
	        preparedStmt2.setInt(5, reservation.getNumOfPeople());
	        preparedStmt2.setDouble(6, reservation.getSubtotal());
	        preparedStmt2.setDouble(7, reservation.getTax());
	        preparedStmt2.setDouble(8, reservation.getTotal());

	        int rowsInserted = preparedStmt2.executeUpdate();
	        
	        if (rowsInserted > 0) {
	            Utils.closeDBConnection(conn);
	            return "RESERVATION_CREATED";
	        }
	    } catch (SQLException ex) {
	        System.out.println("ERROR: " + ex.getMessage());
	    } finally {
	        Utils.closeDBConnection(conn);
	    }
	    return "ERROR";
	}

	@Override
	public ArrayList<Reservation> getReservartionByAttractionId(int attractionId) {

		Connection conn = Utils.getDBConnection();
		
		try {
			ArrayList<Reservation> reservations = new ArrayList();
			Statement stmt = conn.createStatement();
			
			String query = "SELECT * FROM " + RESERVATION_TABLE_NAME + " where attraction_id = " + attractionId;
			
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(result.getInt("id"));
				reservation.setStartDate(result.getString("start_date"));
				reservation.setEndDate(result.getString("end_date"));
				reservation.setCustomerId(result.getInt("customer_id"));
				reservation.setAttractionId(result.getInt("attraction_id"));
				reservation.setNumOfPeople(result.getInt("num_of_people"));
				reservation.setSubtotal(result.getDouble("subtotal"));
				reservation.setTax(result.getDouble("tax"));
				reservation.setTotal(result.getDouble("total"));
				
				reservations.add(reservation);
			}
			
			return reservations;
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}
	
	


}
