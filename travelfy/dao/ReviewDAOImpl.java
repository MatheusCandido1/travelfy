package travelfy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import travelfy.db.Utils;
import travelfy.models.Review;

public class ReviewDAOImpl implements ReviewDAO {

	@Override
	public Review getReviewByReservationId(int reservationId) {
		Connection conn = Utils.getDBConnection();
		
		try {
			Statement stmt = conn.createStatement();
			
			String query = "select v.id, v.comment, v.rate from reviews v "
					+ "inner join reservations r on r.id = v.reservation_id "
					+ "where r.id = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, reservationId);
			
	        ResultSet result = preparedStmt.executeQuery();
			if(result.next()) {
				Review review = new Review();
				review.setComment(result.getString("comment"));
				review.setRate(result.getInt("rate"));
				
				return review;
			} else {
				return null;
			}
			
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return null;
	}

	@Override
	public ArrayList<Review> getReviewsByAttraction(int attractionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Review review) {
		Connection conn = Utils.getDBConnection();
		try {
			Statement stmt = conn.createStatement();
			
			String query = "INSERT INTO reviews (reservation_id, comment, rate) VALUES (?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setInt(1, review.getReservationId());
			preparedStmt.setString(2, review.getComment());
			preparedStmt.setDouble(3, review.getRate());
			preparedStmt.execute();
			
            stmt.close();
            Utils.closeDBConnection(conn);
            
            return true;
            
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return false;
	}

}
