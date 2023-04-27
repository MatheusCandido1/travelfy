package travelfy.dao;

import java.util.ArrayList;

import travelfy.models.Attraction;

public interface AttractionDAO {
	   public boolean create(Attraction attraction);
	   public boolean update(Attraction attraction);
	   public ArrayList<Attraction> getAttractionListByVendorId(int vendorId);
	   public ArrayList<Attraction> getAttractionListByName(String name);
	   public boolean delete(int id);
}
