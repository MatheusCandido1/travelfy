package travelfy.controllers;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import travelfy.dao.ReservationDAOImpl;
import travelfy.models.Attraction;
import travelfy.models.Customer;
import travelfy.models.Reservation;
import travelfy.models.Vendor;

public class CreateReservationController {

    @FXML
    private Label attractionCityLabel;

    @FXML
    private ImageView attractionImageImageView;

    @FXML
    private Label attractionNameLabel;

    @FXML
    private Label attractionPriceLabel;

    @FXML
    private Label attractionStateLabel;

    @FXML
    private Label attractionTypeLabel;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField numOfPeopleTextField;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker startDateDatePicker;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label totalLabel;
    
    Customer currentCustomer = null;
    Attraction currentAttraction = null;
    Reservation reservation = new Reservation();

    DecimalFormat currency = new DecimalFormat("0.00");
    
    
    public void initialize() {
    	
    }
    
    public void initData(Customer customer, Attraction selectedAttraction) {
    	currentCustomer = customer;
    	currentAttraction = selectedAttraction;
		attractionNameLabel.setText("Name: " + selectedAttraction.getName());
		attractionPriceLabel.setText("Price: " + currency.format(selectedAttraction.getPrice()));
		attractionTypeLabel.setText("Type: " + selectedAttraction.getType());
		attractionStateLabel.setText("State: " + selectedAttraction.getState());
		attractionCityLabel.setText("City: " + selectedAttraction.getCity()); 
		if(!selectedAttraction.getImage().isEmpty()) {
    		attractionImageImageView.setVisible(true);
    		attractionImageImageView.setImage(new Image(selectedAttraction.getImage()));
    	} else {
    		attractionImageImageView.setVisible(false);
    	}
	}
    
    public void navigate(ActionEvent e) {
    	try {
        	// Instantiate the FXMLLoader object for loading the UI 
        	FXMLLoader loader = new FXMLLoader();
        	// specify the file location
        	
        		loader.setLocation(getClass().getResource("/travelfy/views/CustomerDashboard.fxml"));
        	
    		// the object representing the root node of the scene; load the UI
    		Parent parent = loader.load();
   	
			// set the scene
			Scene scene = new Scene(parent);

        
    		CustomerDashboardController controller = loader.getController();
    		controller.initData((Customer) currentCustomer);
			
	    	// change the title of the window
	    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    	// change the title of the window
	    	stage.setTitle("Travelfy - Dashboard");
	    	// set the scene for the stage
	    	stage.setResizable(false);
	    	stage.setScene(scene);
	    	
	    	stage.show(); 
    		} catch(Exception ex) {
    			System.out.print(ex.getMessage());
    		}
    }

    @FXML
    public void goBackButtonListener(ActionEvent e) {
    		navigate(e);
    }

    @FXML
    public void saveButtonListener(ActionEvent event) {
    	if(numOfPeopleTextField.getText().isEmpty()) {

			Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Create Reservation");
	    	alert.setContentText("The number of people is required");

	    	alert.showAndWait();
	    	
    		return;
    	}

    	ReservationDAOImpl ReservationDAO = new ReservationDAOImpl();

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date today = new Date();
    	
    	reservation.setAttractionId(currentAttraction.getId());
    	reservation.setStartDate(startDateDatePicker.getValue().format(formatter));
    	reservation.setCustomerId(currentCustomer.getCustomerId());
    	reservation.setAttractionId(currentAttraction.getId());
    	reservation.setNumOfPeople(Integer.parseInt(numOfPeopleTextField.getText()));
    	reservation.setStatus("Pending");
    	
    	String result = ReservationDAO.create(reservation);
    	
    	if(result.equals("INVALID_DATE")) {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Create Reservation");
	    	alert.setContentText("The selected dates are invalid. Please select new ones.");
	    	
	    	alert.showAndWait();
	    	startDateDatePicker.setValue(null);
    		return;
    	}
    	
    	if(result.equals("RESERVATION_CREATED")) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Create Reservation");
	    	alert.setContentText("Reservation created.");

	    	alert.showAndWait();
	    	
    		navigate(event);
    		return;
    	}
    	
    	if(result.equals("ERROR")) {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Create Reservation");
	    	alert.setContentText("Error on creating new reservation. Try again later.");
	    	
	    	alert.showAndWait();
    		return;
    	}
    
    }
    
    public void calculateTax() {
    	if(!numOfPeopleTextField.getText().isEmpty()) {
    		Double subtotal = Double.parseDouble(numOfPeopleTextField.getText()) * currentAttraction.getPrice();
    		subtotalLabel.setText("Subtotal: $ " + currency.format(subtotal));
    		reservation.setSubtotal(subtotal);
    		
    		Double tax = 0.0825 * subtotal;
    		taxLabel.setText("Tax: $ " + currency.format(tax));
    		reservation.setTax(tax);
    		
    		Double total = subtotal + tax;
    		totalLabel.setText("Total: $ " + currency.format(total));
    		reservation.setTotal(total);
    	}
    }

}
