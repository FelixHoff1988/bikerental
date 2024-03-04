package de.wwu.sopra.bikemaintenance;

import java.time.LocalDateTime;
import java.util.List;

import com.sothawo.mapjfx.Coordinate;

import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.Maintenance;
import de.wwu.sopra.entity.Service;
import de.wwu.sopra.map.MapGUI;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BikeMaintenanceGUI extends HBox{

    
    private BikeMaintenanceCTRL ctrl = new BikeMaintenanceCTRL();
    private String model = new String();
    private Coordinate c = new Coordinate((double) 0, (double) 0);
    private String s = new String();
    private String frameId = "";
    private Availability availability = Availability.MAINTENANCE;
     /**
     * Konstruktor.
     */
    public BikeMaintenanceGUI(){
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
        TableColumn<Bike,String> lastMaintenanceColumn = new TableColumn<>("letzte Wartung");
        
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
        lastMaintenanceColumn.setCellValueFactory(data -> {
            String s = "";
            List<Service> services = data.getValue().getServiceList();
            if(services!=null) {
                LocalDateTime endTime = services.getLast().getEndTime();
                if(endTime!=null) {
                    s = endTime.getDayOfMonth() + "." + endTime.getMonthValue() + "." + endTime.getYear();
                }
            } 
            return new ReadOnlyStringWrapper(s);
        });
        
        
        //Spalten zur Tabelle hinzufügen
        tableView.getColumns().add(frameIdColumn);
        tableView.getColumns().add(modelColumn);
        tableView.getColumns().add(typeColumn);
        tableView.getColumns().add(availabilityColumn);        
        tableView.getColumns().add(coordinateColumn);
        tableView.getColumns().add(lastMaintenanceColumn);
        
        //Breite der Tabelle festlegen
        tableView.setMaxWidth(800);
        tableView.setMinWidth(800);
        
        //Breite der Spalten festlegen
        frameIdColumn.setPrefWidth(125);
        availabilityColumn.setPrefWidth(125);
        coordinateColumn.setPrefWidth(175);
        modelColumn.setPrefWidth(125);
        typeColumn.setPrefWidth(125);
        lastMaintenanceColumn.setPrefWidth(125);
        
        
        //Liste zum Verwalten der Fahrräder, verknüpft mit der Tabelle
        ObservableList<Bike> Bikes = FXCollections.observableArrayList(ctrl.loadBikes(Availability.MAINTENANCE));
        tableView.setItems(Bikes);
        
        //Box zum hinzufügen
        var innerBox = new GridPane();
        innerBox.setHgap(30);
        innerBox.setPadding(new Insets(25, 25, 25, 25));
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(5);
        
        //Eingabe der Rahmennummer
        var frameIdLabel = new Label("Rahmennummer: " + frameId);
        
        //Eingabe des Zustand´s
        var availabilityLabel = new Label("Zustand: " + availability.toString());
        
        //Eingabe des Modell´s
        var modelLabel = new Label("Modell: " + model);  
        
        //Buttons zum verwalten der Fahrräder, ein zurück Button zum AdminGUI
        var saveButton = new Button("Standort setzen");
        
        //Buttons zum in die Wartung geben und beenden
        var finishButton = new Button("Wartung Fertig");
        var maintainButton = new Button("Fahrrad warten");
        
        //Buttons um zwischen den Bikes zu wechseln
        var maintainBikesButton = new Button("Fahrräder in Wartung");
        var availableBikesButton = new Button("Verfügbare Fahrräder");
        var faultyBikesButton = new Button("Defekte Fahrräder");
        
        FlowPane flow = new FlowPane();
        flow.setHgap(10);
        //flow.setVgap(10);
        flow.setPadding(new Insets(10));
        flow.getChildren().addAll(maintainBikesButton, availableBikesButton, faultyBikesButton);
        
        
        //Eingabe des Standorts
        var openMap = new Button("Öffne Karte");
        
        s = String.valueOf(c.getLatitude())+" | "+ 
                String.valueOf(c.getLongitude());
        var mapLabel = new Label("Standort: " + s);
        mapLabel.setMinWidth(400);
        
        
        //GUI Komponenten in der InnerBox hinzufügen
        innerBox.add(frameIdLabel, 0, 0);
        
        innerBox.add(availabilityLabel, 0, 1);
        
        innerBox.add(modelLabel, 0, 2);
        
        innerBox.add(mapLabel, 0, 3);
        innerBox.add(openMap, 1, 3);
        
        innerBox.add(saveButton, 2, 3);
        
        innerBox.add(finishButton, 0, 4);
        innerBox.add(maintainButton, 1, 4);
        finishButton.setDisable(true);
        maintainButton.setDisable(false);
        
        //Eingabefelder auf ausgewähltes Fahrrad setzen
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                frameId = newSelection.getFrameId();
                frameIdLabel.setText("Rahmennummer: "+ frameId);
                availability = newSelection.getAvailability();
                availabilityLabel.setText("Zustand: " + availability.toString());
                model = newSelection.getType().getModel();
                modelLabel.setText("Modell: " + model);
                c = newSelection.getLocation();
                s = "Standort: " + String.valueOf(c.getLatitude())+" | "+ 
                        String.valueOf(c.getLongitude());
                mapLabel.setText(s);
                tableView.setItems(Bikes);
            }
        });
        
        //Aktion beim drücken des Speichern Button
        saveButton.setOnAction(evt ->{
            Bike currentBike = tableView.getSelectionModel().getSelectedItem();
            Bike newBike = currentBike;
            newBike.setLocation(c);
            Bikes.set(Bikes.indexOf(currentBike),newBike);
            tableView.setItems(Bikes);
        });
        
        maintainBikesButton.setOnAction(event ->{
            Bikes.setAll(ctrl.loadBikes(Availability.MAINTENANCE));
            tableView.setItems(Bikes);
            maintainBikesButton.setDisable(true);
            availableBikesButton.setDisable(false);
            faultyBikesButton.setDisable(false);
            
            finishButton.setDisable(false);
            maintainButton.setDisable(true);
            
            lastMaintenanceColumn.setCellValueFactory(data -> {
                String s = "";
                
                List<Service> services = data.getValue().getServiceList();
                if(services.size()>=2) {
                    LocalDateTime endTime = services.get(services.size()-2).getEndTime();
                    if(endTime!=null)
                        s = endTime.getDayOfMonth() + "." + endTime.getMonthValue() + "." + endTime.getYear();
                }
                    
                return new ReadOnlyStringWrapper(s);
            });
        });
        
        faultyBikesButton.setOnAction(event ->{
            Bikes.setAll(ctrl.loadBikes(Availability.FAULTY));
            tableView.setItems(Bikes);
            maintainBikesButton.setDisable(false);
            availableBikesButton.setDisable(false);
            faultyBikesButton.setDisable(true);
            
            finishButton.setDisable(true);
            maintainButton.setDisable(false);
            
            lastMaintenanceColumn.setCellValueFactory(data -> {
                String s = "";
                List<Service> services = data.getValue().getServiceList();
                if(services.size()!=0) {
                    LocalDateTime endTime = services.getLast().getEndTime();
                    if(endTime!=null) {
                        s = endTime.getDayOfMonth() + "." + endTime.getMonthValue() + "." + endTime.getYear();
                    }
                } 
                return new ReadOnlyStringWrapper(s);
            });
        });
        
        availableBikesButton.setOnAction(event ->{
            Bikes.setAll(ctrl.loadBikes(Availability.AVAILABLE));
            tableView.setItems(Bikes);
            maintainBikesButton.setDisable(false);
            availableBikesButton.setDisable(true);
            faultyBikesButton.setDisable(false);
            
            finishButton.setDisable(true);
            maintainButton.setDisable(false);
            
            lastMaintenanceColumn.setCellValueFactory(data -> {
                String s = "";
                List<Service> services = data.getValue().getServiceList();
                if(services.size()!=0) {
                    LocalDateTime endTime = services.getLast().getEndTime();
                    if(endTime!=null) {
                        s = endTime.getDayOfMonth() + "." + endTime.getMonthValue() + "." + endTime.getYear();
                    }
                } 
                return new ReadOnlyStringWrapper(s);
            });
        });
        
        finishButton.setOnAction(event ->{
            Bike currentBike = tableView.getSelectionModel().getSelectedItem();
            Bike newBike = currentBike;
            newBike.setAvailability(Availability.AVAILABLE);
            newBike.getServiceList().getLast().setEndTime(LocalDateTime.now());
            
            Bikes.set(Bikes.indexOf(currentBike), newBike);
            tableView.getItems().remove(currentBike);
        });
        
        maintainButton.setOnAction(event ->{
            Bike currentBike = tableView.getSelectionModel().getSelectedItem();
            Bike newBike = currentBike;
            newBike.setAvailability(Availability.MAINTENANCE);
            newBike.serviceListAdd(new Maintenance(LocalDateTime.now(), currentBike));
            
            Bikes.set(Bikes.indexOf(currentBike), newBike);
            tableView.getItems().remove(currentBike);
        });
        
        
        //VBox zum anzeigen der Tabelle und des EingabeFelds erstellen und konfigurieren
        VBox vbox = new VBox(innerBox, flow,  tableView);
        vbox.setPadding(new Insets(30));
        vbox.setFillWidth(true);
        StackPane stack = new StackPane();
        this.getChildren().add(stack);
        stack.getChildren().addAll(vbox);
        this.setAlignment(Pos.CENTER);
        VBox.setVgrow(this, Priority.ALWAYS);
        
        //Aktion beim drücken des Standort hinzufügen Button
        openMap.setOnAction(event -> {
            MapGUI map = new MapGUI();
            map.setMinHeight(600);
            Button closeButton = new Button("Eingabe beenden");
            VBox box = new VBox();
            map.startMarkerPlacement(c);
            box.getChildren().addAll(map, closeButton);
            stack.getChildren().add(box);
            
            closeButton.setOnAction(event2 -> {
                stack.getChildren().remove(1);
                c = map.finalizeMarkerPlacement();
                s = "Standort: "+String.valueOf(c.getLatitude())+" | "+ 
                        String.valueOf(c.getLongitude());
                mapLabel.setText(s);
            });
            
        });
        
    }

}
