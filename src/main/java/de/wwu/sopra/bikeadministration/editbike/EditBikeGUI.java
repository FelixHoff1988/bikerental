package de.wwu.sopra.bikeadministration.editbike;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.PasswordHashing.PasswordHash;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.register.RegisterCTRL;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



/**
 * GUI-Klasse zum Editieren eines Benutzers. (als Admin)
 */
public class EditBikeGUI extends HBox {
	
	private EditBikeCTRL ctrl = new EditBikeCTRL();
	
	 /**
     * Konstruktor.
     */
	public EditBikeGUI(){
		init();
	}
	
	  /**
     * Initialisiert das GUI-Layout für die Benutzer-Editierung.
     */
	public void init() {
		
		TableView<Bike> tableView = new TableView<Bike>();

		TableColumn<Bike,String> frameIdColumn = new TableColumn<>("Rahmennummer");
		TableColumn<Bike,String> availabilityColumn = new TableColumn<>("Status");
		TableColumn<Bike,String> coordinateColumn = new TableColumn<>("Standort");
		TableColumn<Bike,String> modelColumn = new TableColumn<>("Modell");
		TableColumn<Bike,String> typeColumn = new TableColumn<>("Typ");
		
		frameIdColumn.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getFrameId());
        });
		availabilityColumn.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getAvailability().toString());
        });
		coordinateColumn.setCellValueFactory(data -> {
		    String s = "";
		    if(data.getValue().getLocation()!= null) {
		        s = String.valueOf(data.getValue().getLocation().getLatitude())+" | "+ 
		                String.valueOf(data.getValue().getLocation().getLongitude()); 
		    }
        	return new ReadOnlyStringWrapper(s);
        });
		modelColumn.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getType().getModel());
        });
		typeColumn.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getType().getTypeString());
        });
        
		tableView.getColumns().add(frameIdColumn);
        tableView.getColumns().add(availabilityColumn);
        tableView.getColumns().add(coordinateColumn);
        tableView.getColumns().add(modelColumn);
        tableView.getColumns().add(typeColumn);

        frameIdColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        availabilityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        coordinateColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.12));
        modelColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        typeColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        
        ObservableList<Bike> Bikes = FXCollections.observableArrayList(ctrl.loadBikes());
        tableView.setItems(Bikes);
        
		
		var innerBox = new GridPane();
		innerBox.setHgap(30);
		innerBox.setPadding(new Insets(25, 25, 25, 25));
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
        
        //Button zum hinzufügen und zum zurückkehren auf AdminGUI
        var createButton = new Button("Fahhrad hinzufügen");
        var backButton = new Button("Zurück");
        var saveButton = new Button("Speichern");
        var deleteButton = new Button("Löschen");
        
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
            Availability availability = availabilityBox.getValue();

            Bike newBike = ctrl.createButtonAction(frameId, bikeType, model, availability);
            if(newBike!=null)
                Bikes.add(newBike);
            tableView.setItems(Bikes);
        });
        
        backButton.setOnAction(evt ->{
            ctrl.backButtonAction();
        });
        
        saveButton.setOnAction(evt ->{
            String frameId = frameIdTextField.getText();
            String bikeType = bikeTypeBox.getValue();
            String model = modelTextField.getText();
            Availability availability = availabilityBox.getValue();
            Bike currentBike = tableView.getSelectionModel().getSelectedItem();
            
            Bike newBike = ctrl.saveButtonAction(currentBike, frameId, bikeType, model, availability);
            if(newBike!=null)
                Bikes.set(Bikes.indexOf(currentBike),newBike);
            tableView.setItems(Bikes);
        });
        
        deleteButton.setOnAction(evt ->{
            Bike bike = tableView.getSelectionModel().getSelectedItem();
            if(!(ctrl.removeBike(bike) )) {
                deleteButton.setStyle("-fx-background-color: #FFA59D;");
            }
            if(bike!=null)
                Bikes.remove(bike);
            tableView.setItems(Bikes);
        });
        
        innerBox.add(frameIdLabel, 0, 0);
        innerBox.add(frameIdTextField, 1, 0);
        
        innerBox.add(bikeTypeLabel, 0, 1);
        innerBox.add(bikeTypeBox, 1, 1);
        
        innerBox.add(availabilityLabel, 0, 2);
        innerBox.add(availabilityBox, 1, 2);
        
        innerBox.add(modelLabel, 0, 3);
        innerBox.add(modelTextField, 1, 3);
        
        innerBox.add(backButton, 0, 4);
        innerBox.add(saveButton, 1, 4);
        innerBox.add(deleteButton, 2, 4);
        innerBox.add(createButton, 3, 4);
        
        
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
			    frameIdTextField.setText(newSelection.getFrameId());
			    bikeTypeBox.setValue(newSelection.getType().getTypeString());
			    availabilityBox.setValue(newSelection.getAvailability());
			    modelTextField.setText(newSelection.getType().getModel());
			    
			    tableView.setItems(Bikes);
			}
		});
		
		VBox vbox = new VBox(innerBox, tableView);
		vbox.setFillWidth(true);
		
		this.getChildren().addAll(vbox);
		this.setAlignment(Pos.CENTER);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		
		
	}
}

