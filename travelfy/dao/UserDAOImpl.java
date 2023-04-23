package travelfy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import travelfy.db.Utils;
import travelfy.models.User;

public class UserDAOImpl implements UserDAO {

	static public final String USER_TABLE_NAME = "users";
	@Override
	public User signIn(String email, String password) {
		Connection conn = Utils.getDBConnection();
		
		try {

			String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE email = ? and password = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, password);

			ResultSet result = preparedStmt.executeQuery();

			User user = new User();
			
			if(result.next()) {
				user.setId(result.getString("id"));
				user.setEmail(result.getString("email"));
				user.setPassword(result.getString("password"));
				user.setPhone(result.getString("phone"));
			} else {
				user = null;
			}

			preparedStmt.close();
            Utils.closeDBConnection(conn);

			return user;
			
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

}
