package travelfy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import travelfy.db.Utils;
import travelfy.models.Attraction;

public class AttractionDAOImpl implements AttractionDAO {

	static public final String ATTRACTION_TABLE_NAME = "attractions";

	@Override
	public boolean create(Attraction attraction) {
		Connection conn = Utils.getDBConnection();
		try {
			Statement stmt = conn.createStatement();
			
			String query = "INSERT INTO " + ATTRACTION_TABLE_NAME + " (name, type, price, city, state, vendor_id, image) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setString(1, attraction.getName());
			preparedStmt.setString(2, attraction.getType());
			preparedStmt.setDouble(3, attraction.getPrice());
			preparedStmt.setString(4, attraction.getCity());
			preparedStmt.setString(5, attraction.getState()); 
			preparedStmt.setInt(6, attraction.getVendorId());
			preparedStmt.setString(7, attraction.getImage());
			preparedStmt.execute();
			
            stmt.close();
            Utils.closeDBConnection(conn);
            
            return true;
            
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return false;
	}

	@Override
	public ArrayList<Attraction> getAttractionListByVendorId(int vendorId) {

		Connection conn = Utils.getDBConnection();
		
		try {
			ArrayList<Attraction> attractions = new ArrayList();
			Statement stmt = conn.createStatement();
			
			String query = "SELECT * FROM " + ATTRACTION_TABLE_NAME + " where vendor_id = " + vendorId;
			
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()) {
				Attraction attraction = new Attraction();
				attraction.setId(result.getInt("id"));
				attraction.setName(result.getString("name"));
				attraction.setType(result.getString("type"));
				attraction.setState(result.getString("state"));
				attraction.setCity(result.getString("city"));
				attraction.setPrice(result.getDouble("price"));
				attraction.setVendorId(result.getInt("vendor_id"));
				attraction.setImage(result.getString("image"));
				
				attractions.add(attraction);
			}
			
			return attractions;
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean delete(int attractionId) {
		Connection conn = Utils.getDBConnection();
		try {
			String sqlStatement = "DELETE FROM " + ATTRACTION_TABLE_NAME + " WHERE id = ?";

	        // instantiate a PrepareStatement object using the SQL command
	        PreparedStatement prepStmt = conn.prepareStatement(sqlStatement);
	        
	        // provide the values for Update command.
	        prepStmt.setInt(1, attractionId);
	        // Send the UPDATE statement to the DBMS.
	        if(prepStmt.executeUpdate() == 1) {
	        	return true;
	        };
	        return false;

			
		} catch(Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return false;
	}
	
	@Override
	public boolean update(Attraction attraction) {
		Connection conn = Utils.getDBConnection();
	    try (
	         PreparedStatement preparedStmt = conn.prepareStatement(
	                 "UPDATE " + ATTRACTION_TABLE_NAME +
	                 " SET name=?, type=?, price=?, city=?, state=?,  image=? WHERE id=?")
	    ) {
	        preparedStmt.setString(1, attraction.getName());
	        preparedStmt.setString(2, attraction.getType());
	        preparedStmt.setDouble(3, attraction.getPrice());
	        preparedStmt.setString(4, attraction.getCity());
	        preparedStmt.setString(5, attraction.getState());
	        preparedStmt.setString(6, attraction.getImage());
	        preparedStmt.setInt(7, attraction.getId());
	        int rowsUpdated = preparedStmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            return true;
	        }
	    } catch (SQLException ex) {
	        System.out.println("ERROR: " + ex.getMessage());
	    }
	    return false;
	}
	
	@Override
	public ArrayList<Attraction> getAttractionListByName(String name) {

		Connection conn = Utils.getDBConnection();
		
		try {
			ArrayList<Attraction> attractions = new ArrayList();
			Statement stmt = conn.createStatement();
			
			String query = "SELECT * from " + ATTRACTION_TABLE_NAME
					+" where name LIKE '%"+name+"%'";
			
			
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()) {
				Attraction attraction = new Attraction();
				attraction.setId(result.getInt("id"));
				attraction.setName(result.getString("name"));
				attraction.setType(result.getString("type"));
				attraction.setState(result.getString("state"));
				attraction.setCity(result.getString("city"));
				attraction.setPrice(result.getDouble("price"));
				attraction.setVendorId(result.getInt("vendor_id"));
				attraction.setImage(result.getString("image"));
				
				attractions.add(attraction);
			}
			
			return attractions;
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}


}
