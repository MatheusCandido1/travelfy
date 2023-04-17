package travelfy.dao;

import travelfy.models.Vendor;

public interface VendorDAO {
	   public Vendor create(Vendor vendor);
	   public Vendor update(Vendor vendor);
	   public Vendor delete(Vendor vendor);
}
