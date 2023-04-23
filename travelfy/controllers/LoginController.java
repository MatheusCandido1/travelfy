package travelfy.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import travelfy.dao.UserDAOImpl;
import travelfy.models.User;

public class LoginController {

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button signInButton;
    
    public void navigate(User loggedUser, ActionEvent e) {
    	// Instantiate the FXMLLoader object for loading the UI 
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("/travelfy/views/Dashboard.fxml"));
    	
    	// specify the file location for the FXML file for the next window
    	try {
    		// the object representing the root node of the scene; load the UI
    		Parent parent = loader.load();
   	
			// set the scene
			Scene scene = new Scene(parent);
    	   	
	    	// get the current window; i.e. the stage
			DashboardController controller = loader.getController();
			
			controller.initData(loggedUser);
	    	// change the title of the window
	    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    	// change the title of the window
	    	stage.setTitle("Travelfy - Dashboard");
	    	// set the scene for the stage
	    	stage.setScene(scene);
	    	
	    	stage.show();
			} catch (IOException ex) {
				System.out.print(ex.getMessage());
			}
    }
    
    public void signInButtonListener(ActionEvent e) {
    	if(passwordTextField.getText().isEmpty() || emailTextField.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Sign In");
	    	alert.setContentText("The email and password fields are required.");

	    	alert.showAndWait();
    	} else {
    		UserDAOImpl userDAO = new UserDAOImpl();
    		
    		User loggedUser = new User();
    		
    		loggedUser = userDAO.signIn(emailTextField.getText(), passwordTextField.getText());
    		
    		
    		if(loggedUser != null) {
    			navigate(loggedUser, e);
    		} else {
    			Alert alert = new Alert(AlertType.WARNING);
    	    	alert.setTitle("Sign In");
    	    	alert.setContentText("The email or the password are incorrect");

    	    	alert.showAndWait();
    	    	
    	    	passwordTextField.setText("");
    		}
    		
    	}
    }

}

