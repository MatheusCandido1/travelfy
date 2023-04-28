package travelfy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import travelfy.db.Utils;
import travelfy.models.Attraction;
import travelfy.models.User;
import travelfy.models.Vendor;

public class VendorDAOImpl implements VendorDAO {
	static public final String VENDOR_TABLE_NAME = "vendors";
	static public final String USER_TABLE_NAME = "users";

	@Override
	public Vendor create(Vendor vendor) {
		Connection conn = Utils.getDBConnection();
		
		try {
			Statement stmt = conn.createStatement();
			
			conn.setAutoCommit(false);
			
			String query = "INSERT INTO " + USER_TABLE_NAME + " (phone, email, password) VALUES (?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			preparedStmt.setString(1, vendor.getPhone());
			preparedStmt.setString(2, vendor.getEmail());
			preparedStmt.setString(3, vendor.getPassword());
			preparedStmt.execute();

			ResultSet result = preparedStmt.getGeneratedKeys();
			
			if(result.next()) {
				Long userId = result.getLong(1);
				
				String query2 = "INSERT INTO " + VENDOR_TABLE_NAME + " (name, business_identification_number, user_id) VALUES (?, ?, ?)";

				PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			
				preparedStmt2.setString(1, vendor.getName());
				preparedStmt2.setString(2, vendor.getBusinessIdentificationNumber());
				preparedStmt2.setString(3, userId.toString());
				
				preparedStmt2.execute();
				
				conn.commit();
			}
			
            stmt.close();
            Utils.closeDBConnection(conn);
            
            return vendor;
			
		} catch (Exception ex) {
			
			try {
				conn.rollback();
			} catch (SQLException e) {
				System.out.println("MYSQL ERROR: " + e.getMessage());
				
			}
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}

	@Override
	public Vendor update(Vendor vendor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vendor delete(Vendor vendor) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Dashboard getDashboardStats(int vendorId) {
		Connection conn = Utils.getDBConnection(); 
		try {
			Dashboard dashboard = new Dashboard();
			
			// Revenue
			String query = "select sum(r.total) as total from reservations as r "
					+ "inner join attractions a on a.id = r.attraction_id "
					+ "inner join vendors v on v.id = a.vendor_id "
					+ "where r.status = 'APPROVED' and v.id = ?";

	        // instantiate a PrepareStatement object using the SQL command
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, vendorId);
			
	        ResultSet result = preparedStmt.executeQuery();
	        if(result.next()) {
	        	dashboard.setTotalRevenue(result.getDouble("total"));
	        }
	        
	        String query2 = "select count(*) as pending from reservations r "
	        		+ "inner join attractions a on a.id = r.attraction_id "
	        		+ "inner join vendors v on v.id = a.vendor_id "
	        		+ "where v.id = ? and r.status = 'Pending'";

	        // instantiate a PrepareStatement object using the SQL command
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);

			preparedStmt2.setInt(1, vendorId); 
			
	        ResultSet result2 = preparedStmt2.executeQuery();
	        
	        if(result2.next()) {
	        	dashboard.setPendingReservations(result2.getInt("pending"));
	        } else {
	        	dashboard.setPendingReservations(0);
	        }
	        
	        String query3 = "select count(*) as approved from reservations r "
	        		+ "inner join attractions a on a.id = r.attraction_id "
	        		+ "inner join vendors v on v.id = a.vendor_id "
	        		+ "where v.id = ? and r.status = 'Approved'";

	        // instantiate a PrepareStatement object using the SQL command
			PreparedStatement preparedStmt3 = conn.prepareStatement(query3); 

			preparedStmt3.setInt(1, vendorId); 
			
	        ResultSet result3 = preparedStmt3.executeQuery();
	        
	        if(result3.next()) { 
	        	dashboard.setApprovedReservations(result3.getInt("approved"));
	        } else {
	        	dashboard.setApprovedReservations(0);
	        }
	        
	        ArrayList<String> attractions = new ArrayList();
	        attractions.add("Disneyland");
	        attractions.add("Hollywood Tour");
	        attractions.add("Universal Studios");
	        
	        dashboard.setMostFamousAttractions(attractions);
	        
	        return dashboard;
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}

}
