package travelfy.dao;

import java.sql.SQLException;

import travelfy.models.Customer;

public interface CustomerDAO {
	   public Customer create(Customer customer);
	   public Customer update(Customer customer);
	   public Customer delete(Customer customer);
}

