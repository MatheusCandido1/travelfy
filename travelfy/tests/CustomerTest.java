package travelfy.tests;

import com.github.javafaker.Faker;

import travelfy.dao.CustomerDAOImpl;
import travelfy.models.Customer;

public class CustomerTest {

	public static void main(String[] args) {
		create(); 
	}
	
	public static void create() {
		try {

			Faker faker = new Faker();
			Customer customer = new Customer();
			
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			
			String email = firstName +  lastName + "@email.com";
			customer.setEmail(email.toLowerCase());
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setPassword("secret");
			customer.setPhone(faker.phoneNumber().cellPhone());

			CustomerDAOImpl customerDAO = new CustomerDAOImpl();
			customerDAO.create(customer);
			
			System.out.println("CREATE CUSTOMER - PASSED");
			
		} catch (Exception ex) {
			System.out.println("CREATE CUSTOMER - FAILED");
		}
	}

}
