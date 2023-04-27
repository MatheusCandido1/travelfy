package travelfy.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import travelfy.dao.AttractionDAOImpl;
import travelfy.dao.ReservationDAOImpl;
import travelfy.models.Attraction;
import travelfy.models.Customer;
import travelfy.models.Reservation;
import travelfy.models.User;

public class CustomerDashboardController {

	User customer =  new User();

	Attraction selectedAttraction = null;
	
	@FXML
	private Button DashboardButton;

	@FXML
	private TextField attractionNameTextField;

	@FXML
	private Button attractionSearchButton;

	@FXML
    private TableColumn<Attraction, String> attractionNameTableColumn;
    @FXML
    private TableColumn<Attraction, String> attractionTypeTableColumn;
    @FXML
    private TableColumn<Attraction, Double> attractionPriceTableColumn;
    @FXML
    private TableColumn<Attraction, String> attractionStateTableColumn;
    @FXML
    private TableColumn<Attraction, String> attractionCityTableColumn;

	@FXML
	private Button attractionsMenuButton;

	@FXML
	private TableView<Attraction> attractionsTableView;

	@FXML
	private Button logoutButton;

	@FXML
	private Button reservationsMenuButton;

	@FXML
	private Label welcomeLabel;
	

    @FXML
    private Label attractionCityLabel;


    @FXML
    private Label attractionDatesInformation;

    @FXML
    private ImageView attractionImageImageView;

    @FXML
    private Label attractionInformationLabel;

    @FXML
    private Label attractionNameLabel;


    @FXML
    private Label attractionPriceLabel;


    @FXML
    private Label attractionStateLabel;

    @FXML
    private Label attractionTypeLabel;
    

    @FXML
    private Button makeReservationButton;
    
    @FXML
    private TableColumn<Reservation, String> reservationEndDateColumn;

    @FXML
    private TableColumn<Reservation, Integer> reservationNumOfPeopleColumn;

    @FXML
    private TableColumn<Reservation, String> reservationStartDateColumn;

    @FXML
    private TableView<Reservation> reservationTableView;
    
    @FXML
    private Button resetAttractionButton;

    @FXML
    private Pane attractionPane;
    
    @FXML
    private Pane dashboardPane;
    
    SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat currency = new DecimalFormat("0.00");
    
    static final String SELECTED_MENU = "-fx-background-color: white; -fx-text-fill: #004643; -fx-font-size: 20";
    static final String NON_SELECTED_MENU = "-fx-background-color: #004643; -fx-text-fill: white; -fx-font-size: 20";
    
	public void initialize() {
		setLayout();
		hideAttractionDetails();
		attractionsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		attractionNameTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("name"));
		attractionTypeTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("type"));
		attractionCityTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("state"));
    	attractionStateTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("city"));

    	attractionPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Attraction, Double>("price"));
    	attractionPriceTableColumn.setCellFactory(tc -> new TableCell<Attraction, Double>() {
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
    	
    	attractionsTableView.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 1) { // check if it was a single click
    	        selectedAttraction = attractionsTableView.getSelectionModel().getSelectedItem();
    	        if (selectedAttraction != null) {
    	        	showAttractionDetails();
    	        }
    	    }
    	});
    	

    	reservationTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	reservationStartDateColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("startDate"));
    	reservationEndDateColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("endDate"));
    	reservationNumOfPeopleColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numOfPeople"));
	}
	
	public void setLayout() {
		DashboardButton.setStyle(SELECTED_MENU);
		dashboardPane.setVisible(true);
		dashboardPane.setLayoutX(282);
		dashboardPane.setLayoutY(66);
		
		attractionsMenuButton.setStyle(NON_SELECTED_MENU);
		attractionPane.setVisible(false);
		attractionPane.setLayoutX(1141);
		attractionPane.setLayoutY(66);
	}
	
	public void showAttractionDetails() {
		attractionCityLabel.setVisible(true);
		attractionDatesInformation.setVisible(true);
		attractionInformationLabel.setVisible(true);
		attractionNameLabel.setVisible(true);
		attractionPriceLabel.setVisible(true);
		attractionStateLabel.setVisible(true);
		attractionTypeLabel.setVisible(true);
		makeReservationButton.setVisible(true);
		

		attractionNameLabel.setText("Name: " + selectedAttraction.getName());
		attractionPriceLabel.setText("Price: " + currency.format(selectedAttraction.getPrice()));
		attractionTypeLabel.setText("Type: " + selectedAttraction.getType());
		attractionStateLabel.setText("State: " + selectedAttraction.getState());
		attractionCityLabel.setText("City: " + selectedAttraction.getCity());

		reservationTableView.setVisible(true);
		
		if(!selectedAttraction.getImage().isEmpty()) {
    		attractionImageImageView.setVisible(true);
    		attractionImageImageView.setImage(new Image(selectedAttraction.getImage()));
    	} else {
    		attractionImageImageView.setVisible(false);
    	}
		
		loadReservationsTable();
	}
	
	public void hideAttractionDetails() {
		attractionCityLabel.setVisible(false);
		attractionDatesInformation.setVisible(false);
		attractionInformationLabel.setVisible(false);
		attractionNameLabel.setVisible(false);
		attractionPriceLabel.setVisible(false);
		attractionStateLabel.setVisible(false);
		attractionTypeLabel.setVisible(false);
		attractionImageImageView.setVisible(false);
		attractionImageImageView.setImage(null);
		makeReservationButton.setVisible(false);
		reservationTableView.setVisible(false);
	}

	@FXML
	void attractionSearchButtonListener(ActionEvent event) {
		loadAttractionsTable(attractionNameTextField.getText());
	}
	
	public void loadReservationsTable() {

		ReservationDAOImpl ReservationDAO = new ReservationDAOImpl();
    	List<Reservation> reservationList = new ArrayList<Reservation>();
    	reservationList = ReservationDAO.getReservartionByAttractionId(selectedAttraction.getId());
    	
    	ObservableList<Reservation> observableReservations = FXCollections.observableArrayList(reservationList);
    	
    	reservationTableView.setItems(observableReservations);
	}
	
	 public void loadAttractionsTable(String name) {
	    	AttractionDAOImpl AttractionDAO = new AttractionDAOImpl();
	    	
	    	List<Attraction> attractionList = new ArrayList<Attraction>();
	    	
	    	attractionList = AttractionDAO.getAttractionListByName(name);
	    	
	    	ObservableList<Attraction> observableAttractions = FXCollections.observableArrayList(attractionList);
	    	
	    	attractionsTableView.setItems(observableAttractions);
	    	
	    }
	
	public void initData(User loggedUser) {
		customer = (Customer) loggedUser;
		welcomeLabel.setText("Welcome, " + ((Customer) customer).getFirstName());
	}
	
	public void resetAttractionButtonListener(ActionEvent e) {
		selectedAttraction = null;
		hideAttractionDetails();
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
	
	public void reservationsMenuButtonListener(ActionEvent e) {
		
	}
	
	public void attractionsMenuButtonListener(ActionEvent e) {
		dashboardPane.setVisible(false);
		dashboardPane.setLayoutX(1141);
		dashboardPane.setLayoutY(66);

		attractionPane.setVisible(true);
		attractionPane.setLayoutX(282);
		attractionPane.setLayoutY(66);

		DashboardButton.setStyle(NON_SELECTED_MENU);
		attractionsMenuButton.setStyle(SELECTED_MENU);
		
	}
	
	public void DashboardButtonListener(ActionEvent e) {
		attractionPane.setVisible(false);
		attractionPane.setLayoutX(1141);
		attractionPane.setLayoutY(66);

		dashboardPane.setVisible(true);
		dashboardPane.setLayoutX(282);
		dashboardPane.setLayoutY(66);

		DashboardButton.setStyle(SELECTED_MENU);
		attractionsMenuButton.setStyle(NON_SELECTED_MENU);
	}

	
	@FXML
    void makeReservationButtonListener(ActionEvent e) {
		try {
	    	// Instantiate the FXMLLoader object for loading the UI 
	    	FXMLLoader loader = new FXMLLoader();
	    	// specify the file location
	    	loader.setLocation(getClass().getResource("/travelfy/views/CreateReservation.fxml"));
			// the object representing the root node of the scene; load the UI
			Parent parent = loader.load();
		
			// set the scene
			Scene scene = new Scene(parent);
		   	
	    	// get the current window; i.e. the stage
			CreateReservationController controller = loader.getController();

			controller.initData((Customer) customer ,selectedAttraction);
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
}
