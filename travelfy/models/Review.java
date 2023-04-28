package travelfy.models;

public class Review {
	String id;
	double rate;
	String comment;
	int reservationId;
	
	public Review() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double d) {
		this.rate = d;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	
	
}
