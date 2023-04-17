package travelfy.db;


import java.sql.*;


public class Utils {
	/**
	 * This method establishes the DB connection
	 * @return a database connection
	 * @throws ClassNotFoundException 
	 */
	public static Connection getDBConnection(){

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER_NAME, Constants.PASSWORD);
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		
		
		
		return conn;
		
	}

	/**
	 * This method closes the DB connection
	 * @param the connection to be closed
	 */
	public static void closeDBConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception ex) {
				System.out.println("ERROR: " + ex.getMessage());
			}
		}
	}

	
}
