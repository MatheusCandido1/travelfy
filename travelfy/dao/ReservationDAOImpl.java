package travelfy.dao;

import java.sql.Connection;
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
	    	
	        String query2 = "INSERT INTO " + RESERVATION_TABLE_NAME + " (start_date, customer_id, attraction_id, num_of_people, subtotal, tax, total, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement preparedStmt2 = conn.prepareStatement(query2);

	        preparedStmt2.setString(1,reservation.getStartDate());
	        preparedStmt2.setInt(2, reservation.getCustomerId());
	        preparedStmt2.setInt(3, reservation.getAttractionId());
	        preparedStmt2.setInt(4, reservation.getNumOfPeople());
	        preparedStmt2.setDouble(5, reservation.getSubtotal());
	        preparedStmt2.setDouble(6, reservation.getTax());
	        preparedStmt2.setDouble(7, reservation.getTotal());
	        preparedStmt2.setString(8, reservation.getStatus());

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

	@Override
	public ArrayList<Reservation> getReservartionByVendorId(int vendorId) {
			Connection conn = Utils.getDBConnection();
		
		try {
			ArrayList<Reservation> reservations = new ArrayList();
			Statement stmt = conn.createStatement();
			
			String query = "SELECT r.id as id, r.start_date, r.status, a.name as attraction_name, u.email as customer_email, r.num_of_people, r.total "
					+ "FROM reservations r "
					+ "INNER JOIN attractions a ON a.id = r.attraction_id "
					+ "INNER JOIN vendors v ON v.id = a.vendor_id "
					+ "INNER JOIN customers c ON c.id = r.customer_id "
					+ "INNER JOIN users u ON u.id = c.user_id "
					+ "WHERE v.id = ?";
			
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, vendorId);
			
	        ResultSet result = preparedStmt.executeQuery();
	        
			while(result.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(result.getInt("id"));
				reservation.setStartDate(result.getString("start_date"));
				reservation.setCustomerEmail(result.getString("customer_email"));
				reservation.setAttractionName(result.getString("attraction_name"));
				reservation.setNumOfPeople(result.getInt("num_of_people"));
				reservation.setStatus(result.getString("status"));
				reservation.setTotal(result.getDouble("total"));
				
				reservations.add(reservation);
			}
			
			return reservations;
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}
	
	@Override
	public ArrayList<Reservation> getReservartionByCustomerId(int customerId) {
			Connection conn = Utils.getDBConnection();
		
		try {
			ArrayList<Reservation> reservations = new ArrayList();
			Statement stmt = conn.createStatement();
			
			String query = "SELECT r.id as id, r.start_date, r.status, a.name as attraction_name, a.id as attraction_id, u.email as customer_email, r.num_of_people, r.total "
					+ "FROM reservations r "
					+ "INNER JOIN attractions a ON a.id = r.attraction_id "
					+ "INNER JOIN customers c ON c.id = r.customer_id "
					+ "INNER JOIN users u ON u.id = c.user_id "
					+ "WHERE c.id = ?";
			
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, customerId);
			
	        ResultSet result = preparedStmt.executeQuery();
	        
			while(result.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(result.getInt("id"));
				reservation.setStartDate(result.getString("start_date"));
				reservation.setCustomerEmail(result.getString("customer_email"));
				reservation.setAttractionName(result.getString("attraction_name"));
				reservation.setAttractionId(result.getInt("attraction_id"));
				reservation.setNumOfPeople(result.getInt("num_of_people"));
				reservation.setStatus(result.getString("status"));
				reservation.setTotal(result.getDouble("total"));
				
				reservations.add(reservation);
			}
			
			return reservations;
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean updateStatus(Reservation reservation, String status) {
		Connection conn = Utils.getDBConnection();
	    try (
	         PreparedStatement preparedStmt = conn.prepareStatement(
	                 "UPDATE " + RESERVATION_TABLE_NAME +
	                 " SET status = ? WHERE id=?")
	    ) {
	        preparedStmt.setString(1, status);
	        preparedStmt.setInt(2, reservation.getId());
	        int rowsUpdated = preparedStmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            return true;
	        }
	    } catch (SQLException ex) {
	        System.out.println("ERROR: " + ex.getMessage());
	    }
	    return false;
	}
	
	


}
