package travelfy.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import travelfy.dao.AttractionDAOImpl;
import travelfy.dao.CustomerDAOImpl;
import travelfy.dao.CustomerDashboard;
import travelfy.dao.Dashboard;
import travelfy.dao.ReservationDAOImpl;
import travelfy.dao.ReviewDAOImpl;
import travelfy.dao.VendorDAOImpl;
import travelfy.models.Attraction;
import travelfy.models.Customer;
import travelfy.models.Reservation;
import travelfy.models.Review;
import travelfy.models.User;
import travelfy.models.Vendor;

public class CustomerDashboardController {

	User customer =  new User();

	Attraction selectedAttraction = null;
	Reservation selectedReservation = null;
	Review selectedReview = null;
	
	@FXML
	private Button dashboardMenuButton;

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
    private Button resetAttractionButton;

    @FXML
    private Pane attractionPane;
    
    @FXML
    private Pane dashboardPane;
    @FXML
    private Label reviewCommentLabel;

    @FXML
    private TextArea reviewCommentTextArea;

    @FXML
    private Label reviewRateLabel;

    @FXML
    private Slider reviewRateSlider;

    @FXML
    private Button reviewSendReviewButton;

    @FXML
    private Pane reservationsPane;
    
    @FXML
    private Label reviewRatingLabel;
    
    @FXML
    private Label reviewMainLabel;
    
    @FXML
    private TableView<Reservation> reservationsTableView;
    
    @FXML
    private TableColumn<Reservation, String> resevartionsAttractionTableColumn;

    @FXML
    private TableColumn<Reservation, Integer> resevartionsNumOfPeopleTableColumn;

    @FXML
    private TableColumn<Reservation, String> resevartionsStartDateTableColumn;

    @FXML
    private TableColumn<Reservation, String> resevartionsStatusTableColumn;

    @FXML
    private TableColumn<Reservation, Double> resevartionsTotalTableColumn;
    

    @FXML
    private Label dashboardDateLabel;

    @FXML
    private Label dashboardNumOfPeopleLabel;

    @FXML
    private Label dashboardReviewAuthor;

    @FXML
    private Label dashboardReviewComment1;

    @FXML
    private Label dashboardReviewRate;

    @FXML
    private Label dashboardTotalLabel;
    
    @FXML
    private Label dashboardAttractionLabel;
    
    @FXML
    private Label nextTripLabel;
    
    @FXML
    private ImageView dashboardAttractionImageImageView;

    
    SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat currency = new DecimalFormat("0.00");
    
    static final String SELECTED_MENU = "-fx-background-color: white; -fx-text-fill: #004643; -fx-font-size: 20";
    static final String NON_SELECTED_MENU = "-fx-background-color: #004643; -fx-text-fill: white; -fx-font-size: 20";
    
    @FXML
    void reviewSendReviewButtonListener(ActionEvent event) {
    	if(reviewCommentTextArea.getText().isEmpty()) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Review");
	    	alert.setHeaderText("Create Review");
	    	alert.setContentText("Are you sure you want to send your review as blank?");
	    	
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK)	{
	        	createReview();
	        	return;
	    	} 
    	} else {
        	createReview();
    	}
    }
    
    public void createReview() {

    	ReviewDAOImpl ReviewDAO = new ReviewDAOImpl();
    	
    	Review review = new Review();
    	review.setRate(reviewRateSlider.getValue());
    	review.setComment(reviewCommentTextArea.getText());
    	review.setReservationId(selectedReservation.getId());
    	
    	if(ReviewDAO.create(review)) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Create Review");
	    	alert.setContentText("Review created.");

	    	alert.showAndWait();
	    	
	    	selectedReview = review;

			reviewCommentTextArea.setText(selectedReview.getComment());
			reviewCommentTextArea.setDisable(true);
			reviewRateSlider.setValue(selectedReview.getRate());
			reviewRateSlider.setDisable(true);
			reviewRatingLabel.setText("Rating: " + selectedReview.getRate());
			reviewSendReviewButton.setDisable(true);
    	} else {
    		Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Create Review");
	    	alert.setContentText("Error on create review. Try again later.");

	    	alert.showAndWait();
    	}
    }
    
    public void loadDashboardStats() {
    	CustomerDAOImpl CustomerDAO = new CustomerDAOImpl();
    	
    	CustomerDashboard dashboard = new CustomerDashboard();
    	 
    	dashboard = CustomerDAO.getDashboardStats(((Customer) customer).getCustomerId());
    	
    	dashboardDateLabel.setText("Date: " + dashboard.getStartDate());
    	dashboardNumOfPeopleLabel.setText("Number of People: " + dashboard.getNumOfPeople());
    	dashboardAttractionLabel.setText("Date: " + dashboard.getAttraction());
    	dashboardTotalLabel.setText("Total: " + currency.format(dashboard.getTotal()));
    	nextTripLabel.setText("Your next trip is in "+ dashboard.getDaysUntil() + " days");
    	if(!dashboard.getImage().isEmpty()) {
    		dashboardAttractionImageImageView.setVisible(true);
    		dashboardAttractionImageImageView.setImage(new Image(dashboard.getImage()));
    	} else {
    		dashboardAttractionImageImageView.setVisible(false);
    	}
    	
    }
    
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
    	
    	reviewRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		reviewRatingLabel.setText("Rating: " + newValue.intValue());
        });
    	

    	
    	reservationsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	resevartionsStartDateTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("startDate"));
    	resevartionsStatusTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("status"));
    	resevartionsAttractionTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("attractionName"));
    	resevartionsNumOfPeopleTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numOfPeople"));
    	resevartionsTotalTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Double>("total"));
    	resevartionsTotalTableColumn.setCellFactory(tc -> new TableCell<Reservation, Double>() {
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
    	
    	reservationsTableView.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 1) { // check if it was a single click
    	    	selectedReservation = reservationsTableView.getSelectionModel().getSelectedItem();
    	        if (selectedReservation != null) {
    	        	showReviewSection();
    	        }
    	    }
    	});
	}
	
	public void hideReviewSection() {
		 reviewCommentLabel.setVisible(false);
		 reviewCommentTextArea.setVisible(false);
		 reviewRateLabel.setVisible(false);
		 reviewRateSlider.setVisible(false);
		 reviewSendReviewButton.setVisible(false);
		 reviewRatingLabel.setVisible(false);
		 reviewMainLabel.setVisible(false);
	}
	
	public void showReviewSection() {
		 reviewCommentLabel.setVisible(true);
		 reviewCommentTextArea.setVisible(true);
		 reviewRateLabel.setVisible(true);
		 reviewRateSlider.setVisible(true);
		 reviewSendReviewButton.setVisible(true);
		 reviewRatingLabel.setVisible(true);
		 reviewMainLabel.setVisible(true);
		 reviewSendReviewButton.setStyle("-fx-background-color: #004643; -fx-text-fill: white");
		 reviewSendReviewButton.setText("Send Review");
		 

		 if(!selectedReservation.getStatus().equals("Approved")) {
			 reviewCommentTextArea.setDisable(true);
			 reviewRateSlider.setDisable(true);
			 reviewSendReviewButton.setDisable(true);
			 reviewSendReviewButton.setStyle("-fx-background-color: #850007; -fx-text-fill: white");
			 reviewSendReviewButton.setText("You cannot send a review for this reservation");
			 return;
		 }
		 
		 ReviewDAOImpl ReviewDAO = new ReviewDAOImpl();
		 selectedReview = ReviewDAO.getReviewByReservationId(selectedReservation.getId());
		 if(selectedReview != null) {
			 reviewCommentTextArea.setText(selectedReview.getComment());
			 reviewCommentTextArea.setDisable(true);
			 reviewRateSlider.setValue(selectedReview.getRate());
			 reviewRateSlider.setDisable(true);
			 reviewRatingLabel.setText("Rating: " + selectedReview.getRate());
			 reviewSendReviewButton.setDisable(true);
		 } else {
			 reviewCommentTextArea.setText("");
			 reviewCommentTextArea.setDisable(false);
			 reviewRateSlider.setValue(1);
			 reviewRateSlider.setDisable(false);
			 reviewRatingLabel.setText("Rating: 1");
			 reviewSendReviewButton.setDisable(false);
		 }
		
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
		reservationsPane.setVisible(false);
		reservationsPane.setLayoutX(288);
		reservationsPane.setLayoutY(735);
		
		
	}
	
	public void showAttractionDetails() {
		attractionCityLabel.setVisible(true);
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
		
		if(!selectedAttraction.getImage().isEmpty()) {
    		attractionImageImageView.setVisible(true);
    		attractionImageImageView.setImage(new Image(selectedAttraction.getImage()));
    	} else {
    		attractionImageImageView.setVisible(false);
    	}
	}
	
	public void hideAttractionDetails() {
		attractionCityLabel.setVisible(false);
		attractionInformationLabel.setVisible(false);
		attractionNameLabel.setVisible(false);
		attractionPriceLabel.setVisible(false);
		attractionStateLabel.setVisible(false);
		attractionTypeLabel.setVisible(false);
		attractionImageImageView.setVisible(false);
		attractionImageImageView.setImage(null);
		makeReservationButton.setVisible(false);
	}
	
	public void loadReservationsTable(int customerId) {
    	ReservationDAOImpl ReservationDAO = new ReservationDAOImpl();
    	
    	List<Reservation> reservationList = new ArrayList<Reservation>();
    	
    	reservationList = ReservationDAO.getReservartionByCustomerId(customerId);
    	
    	ObservableList<Reservation> observableReservations = FXCollections.observableArrayList(reservationList);
    	
    	reservationsTableView.setItems(observableReservations);
    	
    }

	@FXML
	void attractionSearchButtonListener(ActionEvent event) {
		loadAttractionsTable(attractionNameTextField.getText());
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
		loadDashboardStats();
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
		dashboardPane.setVisible(false);
		dashboardPane.setLayoutX(1141);
		dashboardPane.setLayoutY(66);

		attractionPane.setVisible(false);
		attractionPane.setLayoutX(288);
		attractionPane.setLayoutY(735);
		
		reservationsPane.setVisible(true);
		reservationsPane.setLayoutX(282);
		reservationsPane.setLayoutY(66);

		reservationsMenuButton.setStyle(SELECTED_MENU);
		dashboardMenuButton.setStyle(NON_SELECTED_MENU);
		attractionsMenuButton.setStyle(NON_SELECTED_MENU);
		
		hideReviewSection();
		loadReservationsTable(((Customer)customer).getCustomerId());
	}
	
	public void attractionsMenuButtonListener(ActionEvent e) {
		dashboardPane.setVisible(false);
		dashboardPane.setLayoutX(1141);
		dashboardPane.setLayoutY(66);

		attractionPane.setVisible(true);
		attractionPane.setLayoutX(282);
		attractionPane.setLayoutY(66);
		
		reservationsPane.setVisible(false);
		reservationsPane.setLayoutX(288);
		reservationsPane.setLayoutY(735);

		reservationsMenuButton.setStyle(NON_SELECTED_MENU);
		dashboardMenuButton.setStyle(NON_SELECTED_MENU);
		attractionsMenuButton.setStyle(SELECTED_MENU);
		
	}
	
	public void dashboardMenuButtonListener(ActionEvent e) {
		attractionPane.setVisible(false);
		attractionPane.setLayoutX(1141);
		attractionPane.setLayoutY(66);

		dashboardPane.setVisible(true);
		dashboardPane.setLayoutX(282);
		dashboardPane.setLayoutY(66);

		reservationsPane.setVisible(false);
		reservationsPane.setLayoutX(288);
		reservationsPane.setLayoutY(735);

		reservationsMenuButton.setStyle(NON_SELECTED_MENU);
		dashboardMenuButton.setStyle(SELECTED_MENU);
		attractionsMenuButton.setStyle(NON_SELECTED_MENU);
		loadDashboardStats();
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
