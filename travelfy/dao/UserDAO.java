package travelfy.dao;

import travelfy.models.User;

public interface UserDAO {
	   public User signIn(String email, String password);
	   public User register(String email, String password, String phone, String userType, String information1, String information2);
}
