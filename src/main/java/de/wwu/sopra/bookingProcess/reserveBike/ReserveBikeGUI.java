package de.wwu.sopra.bookingProcess.reserveBike;

import java.nio.charset.StandardCharsets;

import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.map.MapGUI;
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
	private Label price;
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
	public ReserveBikeGUI() {
		build();
		
		this.map.onClickBike(bike -> selectBike(bike));
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
		var bikeInfo = new GridPane();
		this.bikeType = new Label("Fahrradtyp: ");
		this.disclaimer = new Label(new String("Bitte wählen Sie ein Fahrrad aus.".getBytes(), StandardCharsets.UTF_8));
		this.price = new Label("Preis: ");
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
		this.price.setStyle("-fx-font-size:22");
		this.disclaimer.setStyle("-fx-font-size:22");
		
		bikeInfo.setMinWidth(300);
		this.disclaimer.setMinWidth(300);
		
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		this.reserveButton.setDisable(true);
		
		insetBox.setPadding(new Insets(10, 10, 10, 10));
		insetBox.setMaxSize(510, 70);
		insetBox.setAlignment(Pos.BOTTOM_RIGHT);
		
		bikeInfo.addRow(0, bikeType);
		bikeInfo.addRow(1, price);
		this.reserveBox.getChildren().addAll(disclaimer, spacer, reserveButton);
		insetBox.getChildren().add(reserveBox);
		this.getChildren().addAll(map, insetBox);
		
		var ctrl = new ReserveBikeCTRL();
		this.map.displayBikes(ctrl.availableBikes());
	}

	/**
	 * @param bike
	 */
	private void selectBike(Bike bike) {
		String type = bike.getType().getModel();
		int cent = bike.getType().getPrize();
		int euro = cent/100;
		cent %= 100;
		
//		String price
	}
}