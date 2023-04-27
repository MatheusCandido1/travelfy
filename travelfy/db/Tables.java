package travelfy.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Tables {

	static Connection conn = Utils.getDBConnection();
	static Statement stmt;
	
	public static void main(String[] args) {
		createUsersTable();
		createCustomersTable();
		createVendorsTable();
		createAttractionsTable();
		createReservationsTable();
		createReviewsTable();
	}
	
	
	
	public static void createUsersTable() {
		try {
			System.out.println("Creating users table...");
			stmt = conn.createStatement();
			// Create the table.
			stmt.execute(
				"CREATE TABLE users (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,phone VARCHAR(20) NOT NULL, email VARCHAR(30) NOT NULL, password VARCHAR(30) NOT NULL);"
			);   
		} catch (SQLException e) {
			System.out.println("Error creating users table.");
			System.out.println(e.getMessage());
		}
	}
	
	public static void createCustomersTable() {
		try {
			System.out.println("Creating customers table...");
			stmt = conn.createStatement();
			// Create the table.
			stmt.execute(
				"CREATE TABLE customers (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,first_name VARCHAR(40) NOT NULL, last_name VARCHAR(40) NOT NULL, user_id INT NOT NULL, FOREIGN KEY(user_id) REFERENCES users(id));"
			);   
		} catch (SQLException e) {
			System.out.println("Error creating customers table.");
			System.out.println(e.getMessage());
		}
	}
	
	public static void createVendorsTable() {
		try {
			System.out.println("Creating vendors table...");
			stmt = conn.createStatement();
			// Create the table.
			stmt.execute(
				"CREATE TABLE vendors (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,name VARCHAR(40) NOT NULL, business_identification_number VARCHAR(20) NOT NULL, user_id INT NOT NULL, FOREIGN KEY(user_id) REFERENCES users(id));"
			);   
		} catch (SQLException e) {
			System.out.println("Error creating vendors table.");
			System.out.println(e.getMessage());
		}
	}
	
	public static void createAttractionsTable() {
		try {
			System.out.println("Creating attractions table...");
			stmt = conn.createStatement();
			// Create the table.
			stmt.execute(
				"CREATE TABLE attractions (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(60) NOT NULL, type varchar(50) NOT NULL, price DOUBLE(9,2) NOT NULL, city varchar(50) NOT NULL, state varchar(50) NOT NULL, vendor_id int NOT NULL, image varchar(255), FOREIGN KEY(vendor_id) REFERENCES vendors(id));"
			);   
		} catch (SQLException e) {
			System.out.println("Error creating attractions table.");
			System.out.println(e.getMessage());
		}
	}
	
	public static void createReservationsTable() {
		try {
			System.out.println("Creating reservations table...");
			stmt = conn.createStatement();
			// Create the table.
			stmt.execute(
				"CREATE TABLE reservations (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, start_date DATE NOT NULL, end_date DATE NOT NULL, customer_id int NOT NULL, attraction_id int NOT NULL, num_of_people int NOT NULL, subtotal DOUBLE(9,2) NOT NULL, tax DOUBLE(9,2) NOT NULL, total DOUBLE(9,2) NOT NULL, FOREIGN KEY(customer_id) REFERENCES customers(id), FOREIGN KEY(attraction_id) REFERENCES attractions(id));"
			);   
		} catch (SQLException e) {
			System.out.println("Error creating reservations table.");
			System.out.println(e.getMessage());
		}
	}
	
	public static void createReviewsTable() {
		try {
			System.out.println("Creating reviews table...");
			stmt = conn.createStatement();
			// Create the table.
			stmt.execute(
				"CREATE TABLE reviews (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, reservation_id int NOT NULL, rate int NOT NULL, comment text, FOREIGN KEY(reservation_id) REFERENCES reservations(id));"
			);   
		} catch (SQLException e) {
			System.out.println("Error creating reviews table.");
			System.out.println(e.getMessage());
		}
	}

}
