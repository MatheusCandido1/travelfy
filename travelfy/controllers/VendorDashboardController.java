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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import travelfy.dao.AttractionDAOImpl;
import travelfy.dao.Dashboard;
import travelfy.dao.ReservationDAOImpl;
import travelfy.dao.VendorDAOImpl;
import travelfy.db.Constants;
import travelfy.models.Attraction;
import travelfy.models.Reservation;
import travelfy.models.User;
import travelfy.models.Vendor;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

public class VendorDashboardController {

	User vendor = new User();
	
	boolean isUpdatingAttraction = false;
	Attraction selectedAttraction = null;
	Reservation selectedReservation = null;

    @FXML
    private Button dashboardMenuButton;

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
    
    @FXML
    private Pane reservationPane;
    @FXML
    private Pane dashboardPane;
    // SIZE  W 1150
    // SIZE H 707

    @FXML
    private TableView<Reservation> reservationsTableView;

    @FXML
    private TableColumn<Reservation, String> startDateTableColumn;

    @FXML
    private TableColumn<Reservation, String> statusTableColumn;
    
    @FXML
    private TableColumn<Reservation, String> attractionNameTableColumn;

    @FXML
    private TableColumn<Reservation, Double> totalTableColumn;

    @FXML
    private TableColumn<Reservation, String> customerEmailTableColumn;

    @FXML
    private TableColumn<Reservation, Integer> numOfPeopleTableColumn;
    
    @FXML
    private Button reservationApproveButton;

    @FXML
    private Label reservationAttractionLabel;

    @FXML
    private Label reservationCustomerEmailLabel;

    @FXML
    private Button reservationDenyButton;

    @FXML
    private Label reservationNumOfPeopleLabel;

    @FXML
    private Button reservationResetButton;

    @FXML
    private Label reservationStartDateLabel;

    @FXML
    private Label reservationTotalLabel;
    
    @FXML
    private Pane reservationStatusPane;

    @FXML
    private Text reservationStatusText;

    @FXML
    private Label dashboardTotalRevenueLabel;
    
    @FXML
    private Label dashboardApprovedReservationsLabel;

    @FXML
    private Label dashboardAttration1Label;

    @FXML
    private Label dashboardAttration2Label;

    @FXML
    private Label dashboardAttration3Label;

    @FXML
    private Button dashboardPendingReservationsButton;


    DecimalFormat currency = new DecimalFormat("0.00");

    static final String SELECTED_MENU = "-fx-background-color: white; -fx-text-fill: #004643; -fx-font-size: 20";
    static final String NON_SELECTED_MENU = "-fx-background-color: #004643; -fx-text-fill: white; -fx-font-size: 20";
    
    public void reservationsMenuButtonListener(ActionEvent e) {

    	vendor = (Vendor) vendor;
    	dashboardMenuButton.setStyle(NON_SELECTED_MENU);
		dashboardPane.setVisible(false);
		dashboardPane.setLayoutX(1141);
		dashboardPane.setLayoutY(66);
		loadReservationTable(((Vendor)vendor).getVendorId());

		attractionsMenuButton.setStyle(NON_SELECTED_MENU);
		attractionPane.setVisible(false);
		attractionPane.setLayoutX(284);
		attractionPane.setLayoutY(700);

		reservationsMenuButton.setStyle(SELECTED_MENU);
		reservationPane.setVisible(true);
		reservationPane.setLayoutX(282);
		reservationPane.setLayoutY(66); 
		
		reservationApproveButton.setVisible(false);
		reservationAttractionLabel.setVisible(false);
		reservationCustomerEmailLabel.setVisible(false);
		reservationDenyButton.setVisible(false);
		reservationNumOfPeopleLabel.setVisible(false);
		reservationResetButton.setVisible(false);
		reservationStartDateLabel.setVisible(false);
		reservationTotalLabel.setVisible(false);
		reservationStatusPane.setVisible(false);
		reservationStatusText.setVisible(false);
	}
	
	public void attractionsMenuButtonListener(ActionEvent e) {
		dashboardMenuButton.setStyle(NON_SELECTED_MENU);
		dashboardPane.setVisible(false);
		dashboardPane.setLayoutX(1141);
		dashboardPane.setLayoutY(66);

		attractionsMenuButton.setStyle(SELECTED_MENU);
		attractionPane.setVisible(true);
		attractionPane.setLayoutX(282);
		attractionPane.setLayoutY(66);

		reservationsMenuButton.setStyle(NON_SELECTED_MENU);
		reservationPane.setVisible(false);
		reservationPane.setLayoutX(284);
		reservationPane.setLayoutY(700);
	}
	
	public void dashboardMenuButtonListener(ActionEvent e) {
		dashboardMenuButton.setStyle(SELECTED_MENU);
		dashboardPane.setVisible(true);
		dashboardPane.setLayoutX(282);
		dashboardPane.setLayoutY(66);
		
		attractionsMenuButton.setStyle(NON_SELECTED_MENU);
		attractionPane.setVisible(false);
		attractionPane.setLayoutX(1141);
		attractionPane.setLayoutY(66);
		
		reservationsMenuButton.setStyle(NON_SELECTED_MENU);
		reservationPane.setVisible(false);
		reservationPane.setLayoutX(284);
		reservationPane.setLayoutY(700);

    	vendor = (Vendor) vendor;
		loadDashboardStats();

	}
    
    public void initialize() {
    	setLayout();
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
    	
    	reservationsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	startDateTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("startDate"));
    	statusTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("status"));
    	attractionNameTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("attractionName"));
    	customerEmailTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerEmail"));
    	numOfPeopleTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numOfPeople"));
    	totalTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Double>("total"));
    	totalTableColumn.setCellFactory(tc -> new TableCell<Reservation, Double>() {
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
    	
    	
    	reservationsTableView.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 1) { // check if it was a single click
    	        selectedReservation = reservationsTableView.getSelectionModel().getSelectedItem();
    	        if (selectedReservation != null) {
    	        	prepareFormToUpdateReservation();
    	        }
    	    }
    	});
    }
    
	public void setLayout() {
		dashboardMenuButton.setStyle(SELECTED_MENU);
		dashboardPane.setVisible(true);
		dashboardPane.setLayoutX(282);
		dashboardPane.setLayoutY(66);
		
		attractionsMenuButton.setStyle(NON_SELECTED_MENU);
		attractionPane.setVisible(false);
		attractionPane.setLayoutX(1141);
		attractionPane.setLayoutY(66);
		
		reservationsMenuButton.setStyle(NON_SELECTED_MENU);
		reservationPane.setVisible(false);
		reservationPane.setLayoutX(284);
		reservationPane.setLayoutY(700);
	}
	
	public void prepareFormToUpdateReservation() {
		
		reservationAttractionLabel.setVisible(true);
		reservationCustomerEmailLabel.setVisible(true);
		reservationNumOfPeopleLabel.setVisible(true);
		reservationResetButton.setVisible(true);
		reservationStartDateLabel.setVisible(true);
		reservationTotalLabel.setVisible(true);
		if(selectedReservation.getStatus().equals("Pending")) {
			reservationApproveButton.setVisible(true);
			reservationDenyButton.setVisible(true);
			reservationStatusPane.setVisible(false);
			reservationStatusText.setVisible(false);
		} else {
			reservationStatusPane.setVisible(true);
			reservationStatusText.setVisible(true);
			reservationApproveButton.setVisible(false);
			reservationDenyButton.setVisible(false);
			if(selectedReservation.getStatus().equals("Approved")) {
				reservationStatusPane.setStyle("-fx-background-color: #004643");
				reservationStatusText.setText("Approved");
			} else {
				reservationStatusPane.setStyle("-fx-background-color: #850007");
				reservationStatusText.setText("Not Approved");
			}
		}
		
		reservationStartDateLabel.setText("Date: " + selectedReservation.getStartDate());
		reservationAttractionLabel.setText("Attraction: " + selectedReservation.getAttractionName());
		reservationNumOfPeopleLabel.setText("Number of People: " + selectedReservation.getNumOfPeople());
		reservationTotalLabel.setText("Total: $" + currency.format(selectedReservation.getTotal()));
		reservationCustomerEmailLabel.setText("Customer Email: " + selectedReservation.getCustomerEmail());
	}
	
	
	
	   @FXML
	    void reservationApproveButtonListener(ActionEvent event) {
		   Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Reservation");
	    	alert.setHeaderText("Approve Reservation");
	    	alert.setContentText("Are you sure you want to approve the reservation made by " + selectedReservation.getCustomerEmail() + "?");
	    	
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK){
	        	ReservationDAOImpl ReservationDAO = new ReservationDAOImpl();
	        	ReservationDAO.updateStatus(selectedReservation, "Approved");
	        	selectedReservation = null;
	        	resetReservationDetails();
	        	loadReservationTable(((Vendor)vendor).getVendorId());
	    	} 
	    }

	    @FXML
	    void reservationDenyButtonListener(ActionEvent event) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Reservation");
	    	alert.setHeaderText("Deny Reservation");
	    	alert.setContentText("Are you sure you want to deny the reservation made by " + selectedReservation.getCustomerEmail() + "?");
	    	
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK){
	        	ReservationDAOImpl ReservationDAO = new ReservationDAOImpl();
	        	ReservationDAO.updateStatus(selectedReservation, "Not Approved");
	        	selectedReservation = null;
	        	resetReservationDetails();
	        	loadReservationTable(((Vendor)vendor).getVendorId());
	    	} 
	    }
	    
	    public void resetReservationDetails() {
	    	selectedReservation = null;
	    	reservationApproveButton.setVisible(false);
			reservationAttractionLabel.setVisible(false);
			reservationCustomerEmailLabel.setVisible(false);
			reservationDenyButton.setVisible(false);
			reservationNumOfPeopleLabel.setVisible(false);
			reservationResetButton.setVisible(false);
			reservationStartDateLabel.setVisible(false);
			reservationTotalLabel.setVisible(false);
			reservationStatusPane.setVisible(false);
			reservationStatusText.setVisible(false);
	    }

	    @FXML
	    void reservationResetButtonListener(ActionEvent event) {
	    	resetReservationDetails();
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
    	loadReservationTable(((Vendor)vendor).getVendorId());
    	loadDashboardStats();
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
    
    public void loadReservationTable(int vendorId) {
    	ReservationDAOImpl ReservationDAO = new ReservationDAOImpl();
    	
    	List<Reservation> reservationList = new ArrayList<Reservation>();
    	
    	reservationList = ReservationDAO.getReservartionByVendorId(vendorId);
    	
    	ObservableList<Reservation> observableReservations = FXCollections.observableArrayList(reservationList);
    	
    	reservationsTableView.setItems(observableReservations);
    	
    }
    
    public void loadDashboardStats() {
    	VendorDAOImpl VendorDAO = new VendorDAOImpl();
    	
    	
    	
    	Dashboard dashboard = new Dashboard();
    	 
    	dashboard = VendorDAO.getDashboardStats(((Vendor)vendor).getVendorId());
    	dashboardTotalRevenueLabel.setText("$ " + currency.format(dashboard.getTotalRevenue())); 
    	dashboardAttration1Label.setText("1 - " + dashboard.getMostFamousAttractions().get(0));
    	dashboardAttration2Label.setText("2 - " + dashboard.getMostFamousAttractions().get(1));
    	dashboardAttration3Label.setText("3 - " + dashboard.getMostFamousAttractions().get(2));
    	dashboardApprovedReservationsLabel.setText(" "+dashboard.getApprovedReservations());
    	dashboardPendingReservationsButton.setText("(" + dashboard.getPendingReservations() + ") pending reservation(s)");
    	
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
