package travelfy.db;

import java.util.Arrays;
import java.util.List;

/**
 * This class defines the strings used to establish a database connection
 *
 */
public final class Constants {
	static public final String DB_URL = "jdbc:mysql://localhost:3306/db_travelfy";
    static public final String USER_NAME = "root";
    static public final String PASSWORD = "";

	static public final String USER_TABLE_NAME = "users";
	static public final String CUSTOMER_TABLE_NAME = "customers";
	
	 static public final List<String> CITIES = Arrays.asList("Los Angeles", "Anaheim", "San Diego");
	 static public final List<String> STATES = Arrays.asList("California", "Oregon");
	 static public final List<String> TYPES = Arrays.asList("Theme Park", "Zoo", "Club");
}
