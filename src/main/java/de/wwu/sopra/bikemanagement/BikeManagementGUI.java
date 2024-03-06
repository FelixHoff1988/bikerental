/**
 * @author David
 * @author Nisa
 * @author Eike Elias
 */
package de.wwu.sopra.bikemanagement;

import java.util.function.Consumer;

import com.sothawo.mapjfx.Marker;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.Design;
import de.wwu.sopra.bookingProcess.reserveBike.ReserveBikeGUI;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.BikeStation;
import de.wwu.sopra.entity.BikeType;
import de.wwu.sopra.entity.CargoBike;
import de.wwu.sopra.entity.EBike;
import de.wwu.sopra.map.MapGUI;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BikeManagementGUI extends HBox{
    
    private BikeManagementCTRL ctrl = new BikeManagementCTRL();
    private TableView<Bike> tableView = new TableView<Bike>();
    private MapGUI map = new MapGUI();
    
    public BikeManagementGUI() {
        init();
    }

    public void init() {
        
        //Tabelle mit den Spalten erstellen
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
        tableView.getColumns().add(modelColumn);
        tableView.getColumns().add(typeColumn);
        tableView.getColumns().add(availabilityColumn);        
        tableView.getColumns().add(coordinateColumn);
        
        //Liste zum Verwalten der Fahrräder, verknüpft mit der Tabelle
        ObservableList<Bike> Bikes = FXCollections.observableArrayList(ctrl.loadBikes());
        tableView.setItems(Bikes);
        
        
        //Bikes zum anzeigen auf der Karte
        var availableBikes = ctrl.loadBikes(Availability.AVAILABLE);
        var blockedBikes = ctrl.loadBikes(Availability.BLOCKED);
        
        //Farbe der Bikes auf der Map setzten
        map.displayMarkers(
                availableBikes,
                Bike::getLocation,
                Design.COLOR_MAP_BIKE_DEFAULT
                );
        map.displayMarkers(
                blockedBikes,
                Bike::getLocation,
                Marker.Provided.RED
                );
        
        //Map onClick Action
        Consumer<Bike> onBikeClick = this::update;
        map.onClickMarker(Bike.class, onBikeClick, Design.COLOR_MAP_BIKE_SELECTED);
        
        //Box zum hinzufügen
        var innerBox = new GridPane();
        innerBox.setHgap(30);
        innerBox.setPadding(new Insets(25, 25, 25, 25));
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(5);
        
        var flow = new HBox();
        Button openButton = new Button("Fahrrad öffnen");
        Button closeButton = new Button("Fahrrad schließen");
        ComboBox<String> stationBox = new ComboBox<>();
        stationBox.getItems().addAll(ctrl.loadStations());
        var spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        flow.getChildren().addAll(openButton, closeButton, spacer, stationBox);
        flow.setPadding(new Insets(10));
        flow.setSpacing(5);
        
        innerBox.add(tableView, 0, 0);
        innerBox.add(flow, 0, 1);
        
        //Eingabefelder auf ausgewähltes Fahrrad setzen
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        });
        
        
        GridPane grid = new GridPane();
        grid.add(innerBox, 1,0);
        grid.add(map, 0, 0);
        
        //GUI Format´s
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25));
        this.setAlignment(Pos.CENTER);
        
        map.setMinHeight(500);
        map.setMaxHeight(500);
        map.setMinWidth(600);
        
        //Breite der Tabelle festlegen
        tableView.setMaxWidth(550);
        tableView.setMinWidth(550);
        tableView.setMinHeight(500);
        tableView.setMinHeight(500);
        
        //Breite der Spalten festlegen
        frameIdColumn.setPrefWidth(125);
        availabilityColumn.setPrefWidth(125);
        coordinateColumn.setPrefWidth(200);
        modelColumn.setPrefWidth(125);
        typeColumn.setPrefWidth(125);
        
        
        this.getChildren().add(grid);
    }

    private void update(Bike bike) {
        
    }
}
