package travelfy.tests;

import com.github.javafaker.Faker;

import travelfy.dao.VendorDAOImpl;
import travelfy.models.Vendor;

public class VendorTest {

	public static void main(String[] args) {
		create(); 
	}
	
	public static void create() {
		try {

			Faker faker = new Faker();
			Vendor vendor = new Vendor();
			
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			
			String email = firstName +  lastName + "@email.com";
			vendor.setEmail(email.toLowerCase());
			vendor.setName(firstName + " LLC");
			vendor.setBusinessIdentificationNumber(faker.business().creditCardNumber());
			vendor.setPassword("secret");
			vendor.setPhone(faker.phoneNumber().cellPhone());

			VendorDAOImpl VendorDAO = new VendorDAOImpl();
			VendorDAO.create(vendor);
			
			System.out.println("CREATE VENDOR - PASSED");
			
		} catch (Exception ex) {
			System.out.println("CREATE VENDOR - FAILED");
		}
	}

}
