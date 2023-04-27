package travelfy.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import travelfy.dao.AttractionDAOImpl;
import travelfy.db.Constants;
import travelfy.models.Attraction;
import travelfy.models.User;
import travelfy.models.Vendor;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

public class VendorDashboardController {

	User vendor = new User();
	
	boolean isUpdatingAttraction = false;
	Attraction selectedAttraction = null;

    @FXML
    private Button DashboardButton;

    @FXML
    private Button attractionsMenuButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button reservationsMenuButton;
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private ComboBox<String> attractionCityComboBox;

    @FXML
    private ImageView attractionImageImageView;

    @FXML
    private TextField attractionImageURLTextField;

    @FXML
    private TextField attractionNameTextField;

    @FXML
    private Pane attractionPane;

    @FXML
    private TextField attractionPriceTextField;

    @FXML
    private ComboBox<String> attractionStateComboBox;

    @FXML
    private ComboBox<String> attractionTypeComboBox;

    @FXML
    private TableView<Attraction> attractionsTableView;

	@FXML
    private TableColumn<Attraction, String> nameTableColumn;
    @FXML
    private TableColumn<Attraction, String> typeTableColumn;
    @FXML
    private TableColumn<Attraction, Double> priceTableColumn;
    @FXML
    private TableColumn<Attraction, String> stateTableColumn;
    @FXML
    private TableColumn<Attraction, String> cityTableColumn;
    
    @FXML
    private Button attractionDeleteButton;

    @FXML
    private Button attractionResetButton;

    DecimalFormat currency = new DecimalFormat("0.00");
    
    public void initialize() {
    	attractionDeleteButton.setVisible(false);
    	// set up the columns in the table
    	attractionsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	nameTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("name"));
    	typeTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("type"));
    	cityTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("state"));
    	stateTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("city"));

    	priceTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, Double>("price"));
    	priceTableColumn.setCellFactory(tc -> new TableCell<Attraction, Double>() {
    	    @Override
    	    protected void updateItem(Double value, boolean empty) {
    	        super.updateItem(value, empty) ;
    	        if (empty) {
    	            setText(null);
    	        } else {
    	            setText("U$ " + currency.format(value.doubleValue()));
    	        }
    	    }
    	});
    	
    	loadTypeComboBox();
    	loadCityComboBox();
    	loadStateComboBox();
    	
    	attractionsTableView.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 1) { // check if it was a single click
    	        selectedAttraction = attractionsTableView.getSelectionModel().getSelectedItem();
    	        if (selectedAttraction != null) {
    	        	prepareFormToUpdateAttraction();
    	        }
    	    }
    	});
    	
    	
    	
    }
    
    public void prepareFormToUpdateAttraction() {
    	resetFields();
    	isUpdatingAttraction = true;
    	
    	attractionNameTextField.setText(selectedAttraction.getName());
    	attractionPriceTextField.setText(String.valueOf(selectedAttraction.getPrice()));
    	attractionImageURLTextField.setText(selectedAttraction.getImage());
    	attractionTypeComboBox.setValue(selectedAttraction.getType());
    	attractionCityComboBox.setValue(selectedAttraction.getCity());
    	attractionStateComboBox.setValue(selectedAttraction.getState());
    	
		if(!attractionImageURLTextField.getText().isEmpty()) {
    		attractionImageImageView.setVisible(true);
	    	attractionImageImageView.setImage(new Image(attractionImageURLTextField.getText()));
    	}
		

    	attractionDeleteButton.setVisible(true);
    }

    public void initData(User loggedUser) {
    	vendor = (Vendor) loggedUser;
		welcomeLabel.setText("Welcome, " + ((Vendor) vendor).getName());

    	loadAttractionTable(((Vendor)vendor).getVendorId());
	}

    
    public void loadTypeComboBox() {
    	attractionTypeComboBox.getItems().addAll(Constants.TYPES);
    }
    
    public void loadCityComboBox() {
    	attractionCityComboBox.getItems().addAll(Constants.CITIES);
    }
    
    public void loadStateComboBox() {
    	attractionStateComboBox.getItems().addAll(Constants.STATES);
    }
    
    public void attractionSaveButtonListener() {
    	Attraction attraction = new Attraction();
    	AttractionDAOImpl AttractionDAO = new AttractionDAOImpl();
    	 
    	attraction.setName(attractionNameTextField.getText());
    	attraction.setCity(attractionCityComboBox.getValue());
    	attraction.setState(attractionStateComboBox.getValue());
    	attraction.setType(attractionTypeComboBox.getValue());
    	attraction.setPrice(Double.parseDouble(attractionPriceTextField.getText()));
    	Vendor loggedVendor = (Vendor) vendor;
    	attraction.setVendorId(loggedVendor.getVendorId());
    	attraction.setImage(attractionImageURLTextField.getText());
    	
    	if(isUpdatingAttraction) {
    		attraction.setId(selectedAttraction.getId());
    		if(AttractionDAO.update(attraction)) {
        		Alert alert = new Alert(AlertType.INFORMATION);
    	    	alert.setTitle("Update Attraction");
    	    	alert.setContentText("Attraction updated.");

    	    	alert.showAndWait();
    	    	resetFields();
            	loadAttractionTable(((Vendor)vendor).getVendorId());
            	isUpdatingAttraction = false;
        	} else {
        		Alert alert = new Alert(AlertType.WARNING);
    	    	alert.setTitle("Updated Attraction");
    	    	alert.setContentText("Error on updated attraction. Try again later.");

    	    	alert.showAndWait();
        	}
    	} else {
    		if(AttractionDAO.create(attraction)) {
        		Alert alert = new Alert(AlertType.INFORMATION);
    	    	alert.setTitle("Create Attraction");
    	    	alert.setContentText("Attraction created.");

    	    	alert.showAndWait();
    	    	resetFields();
            	loadAttractionTable(((Vendor)vendor).getVendorId());
        	} else {
        		Alert alert = new Alert(AlertType.WARNING);
    	    	alert.setTitle("Create Attraction");
    	    	alert.setContentText("Error on creating attraction. Try again later.");

    	    	alert.showAndWait();
        	}
    	}
    	
    	
    }
    
    public void resetFields() {
    	attractionNameTextField.setText("");
    	attractionPriceTextField.setText("");
    	attractionImageURLTextField.setText("");
    	attractionTypeComboBox.setValue(null);
    	attractionCityComboBox.setValue(null);
    	attractionStateComboBox.setValue(null);
		attractionImageImageView.setVisible(false);
    	attractionDeleteButton.setVisible(false);
    }
    
    
    
    public void loadAttractionTable(int vendorId) {
    	AttractionDAOImpl AttractionDAO = new AttractionDAOImpl();
    	
    	List<Attraction> attractionList = new ArrayList<Attraction>();
    	
    	attractionList = AttractionDAO.getAttractionListByVendorId(vendorId);
    	
    	ObservableList<Attraction> observableAttractions = FXCollections.observableArrayList(attractionList);
    	
    	attractionsTableView.setItems(observableAttractions);
    	
    }
    
    public void LogoutButtonListener(ActionEvent e) {
    	try {
        	// Instantiate the FXMLLoader object for loading the UI 
        	FXMLLoader loader = new FXMLLoader();
        	// specify the file location
        	loader.setLocation(getClass().getResource("/travelfy/views/Login.fxml"));
    		// the object representing the root node of the scene; load the UI
    		Parent parent = loader.load();
   	
			// set the scene
			Scene scene = new Scene(parent);
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
    
    public void attractionDeleteButtonListener() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Attraction");
    	alert.setHeaderText("Delete Attraction");
    	alert.setContentText("Are you sure you want to delete " + selectedAttraction.getName() + "?");
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
        	AttractionDAOImpl AttractionDAO = new AttractionDAOImpl();
        	AttractionDAO.delete(selectedAttraction.getId());
        	selectedAttraction = null;
        	resetFields();
        	loadAttractionTable(((Vendor)vendor).getVendorId());
    	} 
    	
    	
    } 
    
    public void attractionResetButtonListener() {
    	selectedAttraction = null;
    	resetFields();
    	loadAttractionTable(((Vendor)vendor).getVendorId());
    }
    
    public void imageURLTextFieldListener() {
    	if(!attractionImageURLTextField.getText().isEmpty()) {
    		attractionImageImageView.setVisible(true);
	    	attractionImageImageView.setImage(new Image(attractionImageURLTextField.getText()));
    	} else {
    		attractionImageImageView.setVisible(false);
    	}
    }
	


}
