/**
 * @author David
 * @author Nisa
 */
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Grafische Oberfläche für die Wartung der Fahrräder
 */
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
     * Initialisiert das GUI-Layout für die Fahrrad-Wartung.
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
        
        
        //Buttons zum verwalten der Fahrräder, ein zurück Button zum AdminGUI
        var saveButton = new Button("Standort setzen");
        //Eingabe des Standorts
        var openMap = new Button("Öffne Karte");
        
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
        
        /*FlowPane flowMapFunctions = new FlowPane();
        flowMapFunctions.setHgap(10);
        //flow.setVgap(10);
        flowMapFunctions.setPadding(new Insets(10));
        flowMapFunctions.getChildren().addAll(saveButton, openMap);
        
        FlowPane flowManipulateBikes = new FlowPane();
        flowManipulateBikes.setHgap(10);
        //flow.setVgap(10);
        flowManipulateBikes.setPadding(new Insets(10));
        flowManipulateBikes.getChildren().addAll(finishButton, maintainButton);*/
        
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        
        Region region2 = new Region();
        region2.setMinWidth(10);
        
        Region region3 = new Region();
        region3.setMinWidth(10);
        
        HBox buttonsBelow = new HBox(openMap, region2, saveButton, region1 , finishButton, region3, maintainButton);
        buttonsBelow.setPadding(new Insets(10));
        
        buttonsBelow.setMaxWidth(800);
        buttonsBelow.setMinWidth(800);
        
        s = String.valueOf(c.getLatitude())+" | "+ 
                String.valueOf(c.getLongitude());
        
        finishButton.setDisable(true);
        maintainButton.setDisable(false);
        
        //Eingabefelder auf ausgewähltes Fahrrad setzen
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                frameId = newSelection.getFrameId();
                availability = newSelection.getAvailability();
                model = newSelection.getType().getModel();
                c = newSelection.getLocation();
                s = "Standort: " + String.valueOf(c.getLatitude())+" | "+ 
                        String.valueOf(c.getLongitude());
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
        
        //Aktion beim drücken des Buttons zum Anzeigen der Fährrader in Wartung
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
        
        //Aktion beim drücken des Button zum Anzeigen der defekten Fahrräden
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
        
        //Aktion beim drücken des Buttons zum Anzeigen der verfügbaren Fahrräder
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
        
        //Aktion des Buttons zum beenden einer Wartung
        finishButton.setOnAction(event ->{
            Bike currentBike = tableView.getSelectionModel().getSelectedItem();
            Bike newBike = currentBike;
            newBike.setAvailability(Availability.AVAILABLE);
            newBike.getServiceList().getLast().setEndTime(LocalDateTime.now());
            
            Bikes.set(Bikes.indexOf(currentBike), newBike);
            tableView.getItems().remove(currentBike);
        });
        
        //Aktion des Buttons zum warten eines Fahrrads
        maintainButton.setOnAction(event ->{
            Bike currentBike = tableView.getSelectionModel().getSelectedItem();
            Bike newBike = currentBike;
            newBike.setAvailability(Availability.MAINTENANCE);
            newBike.serviceListAdd(new Maintenance(LocalDateTime.now(), currentBike));
            
            Bikes.set(Bikes.indexOf(currentBike), newBike);
            tableView.getItems().remove(currentBike);
        });
        
        
        //VBox zum anzeigen der Tabelle und des EingabeFelds erstellen und konfigurieren
        Region region4 = new Region();
        region3.setMinHeight(10);
        region3.setMaxHeight(10);
        
        VBox vbox = new VBox(flow,  tableView, buttonsBelow);
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
            });
            
        });
        
    }

}
