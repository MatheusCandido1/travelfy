package travelfy.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CustomerDashboard {

	public String startDate;
	public int numOfPeople;
	public String attraction;
	public Double total;
	public long daysUntil;
	public String image;
	
	

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public int getNumOfPeople() {
		return numOfPeople;
	}
	public void setNumOfPeople(int numOfPeople) {
		this.numOfPeople = numOfPeople;
	}
	public String getAttraction() {
		return attraction;
	}
	public void setAttraction(String attraction) {
		this.attraction = attraction;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public long getDaysUntil() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	Date today = new Date();
        	String todayString = dateFormat.format(today);
        	
            Date startDate = dateFormat.parse(this.startDate);
            Date endDate = dateFormat.parse(todayString);
            long differenceInDays = ChronoUnit.DAYS.between(
                    LocalDate.ofInstant(endDate.toInstant(), ZoneId.systemDefault()),
                    LocalDate.ofInstant(startDate.toInstant(), ZoneId.systemDefault()));
           
            return differenceInDays;
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return 0;
	}
}
