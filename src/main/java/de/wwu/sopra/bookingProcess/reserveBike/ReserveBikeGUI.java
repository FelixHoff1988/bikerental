package de.wwu.sopra.bookingProcess.reserveBike;

import java.nio.charset.StandardCharsets;

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
	public ReserveBikeGUI() {
		build();
	}
	
	/**
	 * 
	 */
	public void build() {
		
		HBox.setHgrow(this, Priority.ALWAYS);
		VBox.setVgrow(this, Priority.ALWAYS);
		this.setAlignment(Pos.BOTTOM_RIGHT);
		
		var map = new MapGUI();
		HBox.setHgrow(map, Priority.ALWAYS);
		VBox.setVgrow(map, Priority.ALWAYS);
		
		var reserveBox = new HBox();
		var bikeInfo = new GridPane();
		var bikeType = new Label("Fahrradtyp: ");
		var disclaimer = new Label(new String("Bitte wählen Sie ein Fahrrad aus.".getBytes(), StandardCharsets.UTF_8));
		var price = new Label("Preis: ");
		var reserveButton = new Button("Reservieren");
		var spacer = new Pane();
		var insetBox = new FlowPane();
		
		reserveBox.setMaxSize(510, 70);
		reserveBox.setPadding(new Insets(10, 10, 10, 10));
		reserveBox.setStyle(
		"-fx-background-color: #e6e6e6;"
		+ "-fx-border-radius: 10;"
		+ "-fx-border-style: solid;"
		+ "-fx-border-color: #bfbfbf;"
		+ "-fx-background-radius: 10;"
		+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
		reserveBox.setAlignment(Pos.CENTER);
		
		reserveButton.setStyle("-fx-font-size:22");
		bikeType.setStyle("-fx-font-size:22");
		price.setStyle("-fx-font-size:22");
		disclaimer.setStyle("-fx-font-size:22");
		
		bikeInfo.setMinWidth(300);
		disclaimer.setMinWidth(300);
		
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		reserveButton.setDisable(true);
		
		insetBox.setPadding(new Insets(10, 10, 10, 10));
		insetBox.setMaxSize(510, 70);
		insetBox.setAlignment(Pos.BOTTOM_RIGHT);
		
		bikeInfo.addRow(0, bikeType);
		bikeInfo.addRow(1, price);
		reserveBox.getChildren().addAll(disclaimer, spacer, reserveButton);
		insetBox.getChildren().add(reserveBox);
		this.getChildren().addAll(map, insetBox);
		
		var ctrl = new ReserveBikeCTRL();
		map.displayBikes(ctrl.availableBikes());
	}	
}