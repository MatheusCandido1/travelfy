package travelfy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import travelfy.db.Utils;
import travelfy.models.Customer;
import travelfy.models.User;
import travelfy.models.Vendor;

public class UserDAOImpl implements UserDAO {

	static public final String USER_TABLE_NAME = "users";
	@Override
	public User signIn(String email, String password) {
		Connection conn = Utils.getDBConnection();
		
		try {
			String query = "SELECT u.id as user_id, u.phone, u.email, u.password, c.id as customer_id, c.first_name, c.last_name"
					+ " FROM customers as c" 
					+ " INNER JOIN " + USER_TABLE_NAME + "  as u on c.user_id = u.id"
					+ " WHERE u.email = ? AND u.password = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, password);
			
			ResultSet result = preparedStmt.executeQuery();

			Customer customer = new Customer();
			
			if(result.next()) {
				customer.setId(result.getInt("user_id"));
				customer.setEmail(result.getString("email"));
				customer.setPassword(result.getString("password"));
				customer.setPhone(result.getString("phone"));
				customer.setFirstName(result.getString("first_name"));
				customer.setLastName(result.getString("last_name"));
				customer.setCustomerId(result.getInt("customer_id"));
				
				return customer;
			}
			else {
				String query2 = "SELECT u.id as user_id, u.phone, u.email, u.password, v.id as vendor_id, v.name, v.business_identification_number"
						+ " FROM vendors as v"
						+ " INNER JOIN users as u on v.user_id = user_id"
						+ " WHERE u.email = ? AND u.password = ?";
				
				PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
				
				preparedStmt2.setString(1, email);
				preparedStmt2.setString(2, password);
				

				ResultSet result2 = preparedStmt2.executeQuery();
				

				Vendor vendor = new Vendor();
				
				if(result2.next()) {
					vendor.setId(result2.getInt("user_id"));
					vendor.setEmail(result2.getString("email"));
					vendor.setPassword(result2.getString("password"));
					vendor.setPhone(result2.getString("phone"));
					vendor.setName(result2.getString("name"));
					vendor.setBusinessIdentificationNumber(result2.getString("business_identification_number"));
					vendor.setVendorId(result2.getInt("vendor_id"));
					
					return vendor;
				}
			}

			preparedStmt.close();
            Utils.closeDBConnection(conn);
            
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}
	@Override
	public User register(String email, String password, String phone, String userType, String information1, String information2) {
		try {
			if(userType == "Customer") {
				CustomerDAOImpl CustomerDAO = new CustomerDAOImpl();
				Customer customer = new Customer();
				customer.setEmail(email);
				customer.setPassword(password);
				customer.setPhone(phone);
				customer.setFirstName(information1);
				customer.setLastName(information2);
				
				CustomerDAO.create(customer);
				
				return customer;
			} else {
				VendorDAOImpl VendorDAO = new VendorDAOImpl();
				Vendor vendor = new Vendor();
				vendor.setEmail(email);
				vendor.setPassword(password);
				vendor.setPhone(phone);
				vendor.setName(information1);
				vendor.setBusinessIdentificationNumber(information2);
				
				VendorDAO.create(vendor);
				
				return vendor;
			}
			
		}
		catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		
		return null;
	}

}
