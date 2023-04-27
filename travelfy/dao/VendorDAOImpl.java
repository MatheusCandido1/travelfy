package travelfy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import travelfy.db.Utils;
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

}
