package travelfy.dao;

import java.util.ArrayList;

import travelfy.models.Review;

public interface ReviewDAO {
	   public Review getReviewByReservationId(int reservationId);
	   public boolean create(Review review);
	   public ArrayList<Review> getReviewsByAttraction(int attractionId);
}
