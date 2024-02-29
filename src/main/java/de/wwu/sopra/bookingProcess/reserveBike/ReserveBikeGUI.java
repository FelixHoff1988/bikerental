package de.wwu.sopra.bookingProcess.reserveBike;

import com.sothawo.mapjfx.Marker.Provided;

import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.map.MapGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 
 */
public class ReserveBikeGUI extends StackPane {

	/**
	 * 
	 */
	private HBox reserveBox;
	/**
	 * 
	 */
	private Label bikeType;
	/**
	 * 
	 */
	private Label bikePrice;
	/**
	 * 
	 */
	private Label bikeFeature;
	/**
	 * 
	 */
	private GridPane bikeInfo;
	/**
	 * 
	 */
	private Label disclaimer;
	/**
	 * 
	 */
	private Button reserveButton;
	/**
	 * 
	 */
	private MapGUI map;
	/**
	 * 
	 */
	private Bike selectedBike;
	
	/**
	 * 
	 */
	public ReserveBikeGUI() {
		build();
		
		this.map.<Bike>onClickMarker(bike -> selectBike(bike), Provided.GREEN);
		
//		this.reserveButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                
//            }
//        });
	}
	
	/**
	 * 
	 */
	public void build() {
		
		HBox.setHgrow(this, Priority.ALWAYS);
		VBox.setVgrow(this, Priority.ALWAYS);
		this.setAlignment(Pos.BOTTOM_RIGHT);
		
		this.map = new MapGUI();
		HBox.setHgrow(map, Priority.ALWAYS);
		VBox.setVgrow(map, Priority.ALWAYS);
		
		this.reserveBox = new HBox();
		this.bikeInfo = new GridPane();
		this.bikeType = new Label("Fahrradtyp: ");
		this.disclaimer = new Label("Bitte wählen Sie ein Fahrrad aus.");
		this.bikePrice = new Label("Preis: ");
		this.bikeFeature = new Label();
		this.reserveButton = new Button("Reservieren");
		var spacer = new Pane();
		var insetBox = new FlowPane();
		
		this.reserveBox.setMaxSize(510, 70);
		this.reserveBox.setPadding(new Insets(10, 10, 10, 10));
		this.reserveBox.setStyle(
		"-fx-background-color: #e6e6e6;"
		+ "-fx-border-radius: 10;"
		+ "-fx-border-style: solid;"
		+ "-fx-border-color: #bfbfbf;"
		+ "-fx-background-radius: 10;"
		+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
		this.reserveBox.setAlignment(Pos.CENTER);
		
		this.reserveButton.setStyle("-fx-font-size:22");
		this.bikeType.setStyle("-fx-font-size:22");
		this.bikePrice.setStyle("-fx-font-size:22");
		this.disclaimer.setStyle("-fx-font-size:22");
		
		this.bikeInfo.setMinWidth(300);
		this.disclaimer.setMinWidth(300);
		
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		this.reserveButton.setDisable(true);
		
		insetBox.setPadding(new Insets(8, 8, 8, 8));
		insetBox.setMaxSize(510, 70);
		insetBox.setAlignment(Pos.BOTTOM_RIGHT);
		
		this.bikeInfo.addRow(0, bikeType);
		this.bikeInfo.addRow(1, bikePrice);
		this.bikeInfo.addRow(2, bikeFeature);
		this.reserveBox.getChildren().addAll(disclaimer, spacer, reserveButton);
		insetBox.getChildren().add(reserveBox);
		this.getChildren().addAll(map, insetBox);
		
		var ctrl = new ReserveBikeCTRL();
		this.map.displayMarkers(ctrl.availableBikes(), bike -> bike.getLocation(), Provided.ORANGE);
	}

	/**
	 * @param bike
	 */
	private void selectBike(Bike bike) {
	    
	    if (this.selectedBike == bike) {
	        this.reserveBox.getChildren().removeFirst();
            this.reserveBox.getChildren().addFirst(this.disclaimer);
            
            this.reserveButton.setDisable(true);
            
            this.selectedBike = null;
	    } else {
	        this.selectedBike = bike;
	        
	        String type = this.selectedBike.getType().getModel();
	        
	        int cent = this.selectedBike.getType().getPrice();
	        int euro = cent/100;
	        cent %= 100;
	        String centString = String.valueOf(cent);
	        centString = "0" + centString;
	        if (centString.length() >= 2) {
	            centString = centString.substring(centString.length() - 2);
	        }
	        
	        String price = euro + "," + centString + "€";
	        
	        String feature = this.selectedBike.getType().getFeatureDescription();
	        
	        this.bikeType.setText("Fahrradtyp: " + type);
	        this.bikePrice.setText("Preis: " + price + "/h");
	        this.bikeFeature.setText(feature);
	        
	        if(feature == null) {
	            this.bikeFeature.setVisible(false);
	        } else {
	            this.bikeFeature.setVisible(true);
	        }
	        
	        this.reserveBox.getChildren().removeFirst();
	        this.reserveBox.getChildren().addFirst(this.bikeInfo);
	        
	        this.reserveButton.setDisable(false);
	    }
	}
}