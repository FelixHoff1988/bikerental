package de.wwu.sopra.bikeadministration.addbike;

import java.awt.event.ActionListener;

import de.wwu.sopra.entity.Availability;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AddBikeGUI extends HBox{
	
	/**
	 * AddBike Steuerungsklasse
	 */
	private AddBikeCTRL controller = new AddBikeCTRL();
	
    /**
     * Standardkonstruktor: Initialisiert das Basislayout
     */
    public AddBikeGUI() {
        init();
    }
    
    /**
     * Erzeugt das Layout der GUI für AddBike
     */
    public void init() {
    	
    	var innerBox = new GridPane();
		innerBox.setHgap(30);
		//innerBox.setPadding(new Insets(25, 25, 25, 25));
		innerBox.setAlignment(Pos.CENTER);
		innerBox.setVgap(5);
		
		//Eingabe der Rahmennummer
		var frameIdLabel = new Label("Rahmennummer: ");
		var frameIdTextField = new TextField("");
		
		//Eingabe des Fahrradtyp´s
		var bikeTypeLabel = new Label("Fahrrad-Typ: ");
		var bikeTypeBox = new ComboBox<String>();
		bikeTypeBox.getItems().addAll("Standard", "Cargo", "EBike");
		
		//Eingabe des Zustand´s
		var availabilityLabel = new Label("Zustand: ");
		var availabilityBox = new ComboBox<Availability>();
		availabilityBox.getItems().addAll(
				Availability.AVAILABLE,
				Availability.BLOCKED,
				Availability.BOOKED,
				Availability.FAULTY,
				Availability.MAINTENANCE,
				Availability.RESERVED);
		
		//Eingabe der Größe
		var sizeLabel = new Label("Größe: ");
		var sizeBox = new ComboBox<Integer>();
		for(Integer i=12;i<=25;i++) {
			sizeBox.getItems().add(i);
		}
		//Eingabe des Modell´s
		var modelLabel = new Label("Modell: ");
		var modelTextField = new TextField();
		
		//Button zum hinzufügen
		var createButton = new Button("Fahhrad hinzufügen");
		
		//Label der Kapazität für CargoBikes
		var capacityLabel = new Label("Kapazität: ");
		var capacityTextField = new TextField();
		capacityLabel.setVisible(false);
		capacityTextField.setVisible(false);
		
		//Label für charge von EBikes
		var chargeLabel = new Label("AkkuKapazität");
		var chargeTextField = new TextField();
		chargeLabel.setVisible(false);
		chargeTextField.setVisible(false);
		
		//Extra Eingaben für CargoBikes und EBikes
		bikeTypeBox.setOnAction(evt ->{
			switch(bikeTypeBox.getValue()) {
			case "Cargo":
				chargeLabel.setVisible(false);
				chargeTextField.setVisible(false);
				capacityLabel.setVisible(true);
				capacityTextField.setVisible(true);
				break;
			
			case "EBike":
				chargeLabel.setVisible(true);
				chargeTextField.setVisible(true);
				capacityLabel.setVisible(false);
				capacityTextField.setVisible(false);
				break;
				
			default:
				chargeLabel.setVisible(false);
				chargeTextField.setVisible(false);
				capacityLabel.setVisible(false);
				capacityTextField.setVisible(false);
				break;
			
			}
		});
		
		createButton.setOnAction(evt ->{
			String frameId = frameIdTextField.getText();
			String bikeType = bikeTypeBox.getValue();
			String model = modelTextField.getText();
			int size = (int) sizeBox.getValue();
			Availability availability = availabilityBox.getValue();
			int charge = -1;
			int capacity = -1;
			try {
				charge = (int) Integer.valueOf(chargeTextField.getText());
			}	catch (NumberFormatException e) {
	            System.out.println("Invalid integer input");
	        }
			try {
				capacity = (int) Integer.valueOf(capacityTextField.getText());
			}	catch (NumberFormatException e) {
	            System.out.println("Invalid integer input");
	        }
			
			controller.addBike(frameId, bikeType, model, size, availability, charge, capacity);
		});
		
		
		innerBox.add(frameIdLabel, 0, 0);
		innerBox.add(frameIdTextField, 1, 0);
		
		innerBox.add(bikeTypeLabel, 0, 1);
		innerBox.add(bikeTypeBox, 1, 1);
		
		innerBox.add(availabilityLabel, 0, 2);
		innerBox.add(availabilityBox, 1, 2);
		
		innerBox.add(sizeLabel, 0, 3);
		innerBox.add(sizeBox, 1, 3);
		
		innerBox.add(modelLabel, 0, 4);
		innerBox.add(modelTextField, 1, 4);
		
		innerBox.add(chargeLabel, 0, 5);
		innerBox.add(chargeTextField, 1, 5);
		
		innerBox.add(capacityLabel, 0, 5);
		innerBox.add(capacityTextField, 1, 5);
		
		innerBox.add(createButton, 2, 6);
		
		this.getChildren().addAll(innerBox);
		this.setAlignment(Pos.CENTER);
		VBox.setVgrow(this, Priority.ALWAYS);

    }
	
}
