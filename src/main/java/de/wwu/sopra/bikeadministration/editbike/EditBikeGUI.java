package de.wwu.sopra.bikeadministration.editbike;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
		
	    //Tabelle mit den Spalten erstellen
		TableView<Bike> tableView = new TableView<Bike>();
		TableColumn<Bike,String> frameIdColumn = new TableColumn<>("Rahmennummer");
		TableColumn<Bike,String> availabilityColumn = new TableColumn<>("Status");
		TableColumn<Bike,String> coordinateColumn = new TableColumn<>("Standort");
		TableColumn<Bike,String> modelColumn = new TableColumn<>("Modell");
		TableColumn<Bike,String> typeColumn = new TableColumn<>("Typ");
		
		//Werte für die Spalten der Tabelle konfigurieren
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
        
		//Spalten zur Tabelle hinzufügen
		tableView.getColumns().add(frameIdColumn);
        tableView.getColumns().add(availabilityColumn);
        tableView.getColumns().add(coordinateColumn);
        tableView.getColumns().add(modelColumn);
        tableView.getColumns().add(typeColumn);

        //Breite der Spalten festlegen
        frameIdColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        availabilityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        coordinateColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.12));
        modelColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        typeColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        
        //Liste zum Verwalten der Fahrräder, verknüpft mit der Tabelle
        ObservableList<Bike> Bikes = FXCollections.observableArrayList(ctrl.loadBikes());
        tableView.setItems(Bikes);
        
		//Box zum hinzufügen
		var innerBox = new GridPane();
		innerBox.setHgap(30);
		innerBox.setPadding(new Insets(25, 25, 25, 25));
		innerBox.setAlignment(Pos.CENTER);
		innerBox.setVgap(5);
		
        //Eingabe der Rahmennummer
        var frameIdLabel = new Label("Rahmennummer: ");
        var frameIdTextField = new TextField("");
        
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
        
        //Eingabe des Modell´s
        var modelLabel = new Label("Modell: ");
        var modelBox = new ComboBox<String>();
        modelBox.getItems().addAll(ctrl.loadModels());  
        
        //Buttons zum verwalten der Fahrräder, ein zurück Button zum AdminGUI
        var createButton = new Button("Fahhrad hinzufügen");
        var backButton = new Button("Zurück");
        var saveButton = new Button("Speichern");
        var deleteButton = new Button("Löschen");
        
        //Aktion beim drücken auf den Hinzufügen Button
        createButton.setOnAction(evt ->{
            String frameId = frameIdTextField.getText();
            String model = modelBox.getValue();
            Availability availability = availabilityBox.getValue();

            for(Bike b:Bikes) {
                if(b.getFrameId().equals(frameId)) {
                    AppContext.getInstance().showMessage("Fahrrad hinzufügen fehlgeschlagen! \n"
                            + "Rahmennummer bereits vergeben", 5, "#FFCCDD");
                    return;
                }
                    
            }
            
            Bike newBike = ctrl.createButtonAction(frameId, model, availability);
            if(newBike!=null)
                Bikes.add(newBike);
            tableView.setItems(Bikes);
        });
        
        //Aktion beim drücken des zurück Buttons
        backButton.setOnAction(evt ->{
            ctrl.backButtonAction();
        });
        
        //Aktion beim drücken des Speichern Button
        saveButton.setOnAction(evt ->{
            String frameId = frameIdTextField.getText();
            String model = modelBox.getValue();
            Availability availability = availabilityBox.getValue();
            Bike currentBike = tableView.getSelectionModel().getSelectedItem();
            
            if(!frameId.equals(currentBike.getFrameId())) {
                for(Bike b:Bikes) {
                    if(b.getFrameId().equals(frameId)) {
                        AppContext.getInstance().showMessage("Fahrrad hinzufügen fehlgeschlagen! \n"
                                + "Rahmennummer bereits vergeben", 5, "#FFCCDD");
                        return;
                    }
                        
                }
            }
            
            Bike newBike = ctrl.saveButtonAction(currentBike, frameId, model, availability);
            if(newBike!=null)
                Bikes.set(Bikes.indexOf(currentBike),newBike);
            tableView.setItems(Bikes);
        });
        
        //Aktion beim drücken des Löschen Button
        deleteButton.setOnAction(evt ->{
            Bike bike = tableView.getSelectionModel().getSelectedItem();
            if(!(ctrl.removeBike(bike) )) {
                AppContext.getInstance().showMessage("Fahrrad löschen fehlgeschlagen!", 5, "#FFCCDD");
            }
            if(bike!=null) {
                Bikes.remove(bike);
                AppContext.getInstance().showMessage("Fahrrad erfolgreich gelöscht!", 5, "#CCFFCC");
            }
                
            tableView.setItems(Bikes);
        });
        
        //GUI Komponenten in der InnerBox hinzufügen
        innerBox.add(frameIdLabel, 0, 0);
        innerBox.add(frameIdTextField, 1, 0);
        
        innerBox.add(availabilityLabel, 0, 1);
        innerBox.add(availabilityBox, 1, 1);
        
        innerBox.add(modelLabel, 0, 2);
        innerBox.add(modelBox, 1, 2);
        
        innerBox.add(backButton, 0, 3);
        innerBox.add(saveButton, 1, 3);
        innerBox.add(deleteButton, 2, 3);
        innerBox.add(createButton, 3, 3);
        
        //Eingabefelder auf ausgewähltes Fahrrad setzen
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
			    frameIdTextField.setText(newSelection.getFrameId());
			    availabilityBox.setValue(newSelection.getAvailability());
			    modelBox.setValue(newSelection.getType().getModel());
			    
			    tableView.setItems(Bikes);
			}
		});
		
		//VBox zum anzeigen der Tabelle und des EingabeFelds erstellen und konfigurieren
		VBox vbox = new VBox(innerBox, tableView);
		vbox.setFillWidth(true);
		this.getChildren().addAll(vbox);
		this.setAlignment(Pos.CENTER);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		
		
	}
}

