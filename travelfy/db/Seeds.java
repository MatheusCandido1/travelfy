package travelfy.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import travelfy.dao.CustomerDAOImpl;
import travelfy.dao.VendorDAOImpl;
import travelfy.models.Customer;
import travelfy.models.Vendor;

public class Seeds {

	static Connection conn = Utils.getDBConnection();
	static Statement stmt;
	
	public static void main(String[] args) {
		seedUserTable();
	}
	
	public static void seedUserTable() {
		Customer customer = new Customer();
		Vendor vendor = new Vendor();
		
		customer.setEmail("customer@email.com");
		customer.setFirstName("Customer");
		customer.setLastName("Smith");
		customer.setPassword("secret");
		customer.setPhone("(702) 123-4567");

		CustomerDAOImpl customerDAO = new CustomerDAOImpl();
		customerDAO.create(customer);
		
		vendor.setEmail("vendor@email.com");
		vendor.setName("Vendor LLC");
		vendor.setBusinessIdentificationNumber("123321892137");
		vendor.setPassword("secret");
		vendor.setPhone("(725)123-3456");

		VendorDAOImpl VendorDAO = new VendorDAOImpl();
		VendorDAO.create(vendor);
		
	}
}
	