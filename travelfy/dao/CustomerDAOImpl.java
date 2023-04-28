package travelfy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

	@Override
	public CustomerDashboard getDashboardStats(int customerId) {
		Connection conn = Utils.getDBConnection(); 
		try {
			CustomerDashboard customerDashboard = new CustomerDashboard();
			
			// Revenue
			String query = "select r.start_date, r.num_of_people, a.name as attraction, a.image as image, r.total from reservations r "
					+ "inner join customers c on c.id = r.customer_id "
					+ "inner join attractions a on a.id = r.attraction_id "
					+ "where c.id = ? and r.status = 'Approved' and r.start_date > now()";

	        // instantiate a PrepareStatement object using the SQL command
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, customerId);
			
	        ResultSet result = preparedStmt.executeQuery();
	        if(result.next()) {
	        	customerDashboard.setStartDate(result.getString("start_date"));
	        	customerDashboard.setNumOfPeople(result.getInt("num_of_people"));
	        	customerDashboard.setAttraction(result.getString("attraction"));
	        	customerDashboard.setTotal(result.getDouble("total"));
	        	customerDashboard.setImage(result.getString("image"));
	        }
	        /*
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
	        } */
	        
	        return customerDashboard;
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}
}
