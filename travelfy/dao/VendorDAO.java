package travelfy.dao;

import java.util.ArrayList;

import travelfy.models.Vendor;

public interface VendorDAO {
	   public Vendor create(Vendor vendor);
	   public Vendor update(Vendor vendor);
	   public Vendor delete(Vendor vendor);
	   public Dashboard getDashboardStats(int vendorId);
}
