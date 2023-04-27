package travelfy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import travelfy.db.Utils;
import travelfy.models.Customer;
import travelfy.models.User;

public class CustomerDAOImpl implements CustomerDAO {

	static public final String CUSTOMER_TABLE_NAME = "customers";
	static public final String USER_TABLE_NAME = "users";
	
	@Override
	public Customer create(Customer customer) {
		Connection conn = Utils.getDBConnection();
		
		try {
			Statement stmt = conn.createStatement();
			
			conn.setAutoCommit(false);
			 
			String query = "INSERT INTO " + USER_TABLE_NAME + " (phone, email, password) VALUES (?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			preparedStmt.setString(1, customer.getPhone());
			preparedStmt.setString(2, customer.getEmail());
			preparedStmt.setString(3, customer.getPassword());
			preparedStmt.execute();

			ResultSet result = preparedStmt.getGeneratedKeys();
			
			if(result.next()) {
				Long userId = result.getLong(1);
				
				String query2 = "INSERT INTO " + CUSTOMER_TABLE_NAME + " (first_name, last_name, user_id) VALUES (?, ?, ?)";

				PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			
				preparedStmt2.setString(1, customer.getFirstName());
				preparedStmt2.setString(2, customer.getLastName());
				preparedStmt2.setString(3, userId.toString());
				
				preparedStmt2.execute();
				
				conn.commit();
			}
			
            stmt.close();
            Utils.closeDBConnection(conn);
            
            return customer;
			
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
	public Customer update(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer delete(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
}
