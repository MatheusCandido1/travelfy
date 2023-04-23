package travelfy.dao;

import travelfy.models.User;

public interface UserDAO {
	   public User signIn(String email, String password);
}
