/**
 * @author David
 * @author Nisa
 * @author Eike Elias
 */
package de.wwu.sopra.bikemanagement;

import java.util.function.Consumer;

import com.sothawo.mapjfx.Marker;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.Design;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.map.MapGUI;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * GUI Klasse für das Umverteilen der Fahrräder vom StationsManager
 */
public class BikeManagementGUI extends HBox{
    
    /**
     * Kontrollinstanz der GUI
     */
    private BikeManagementCTRL ctrl = new BikeManagementCTRL();
    /**
     * Tabelle für die Fahrräder
     */
    private TableView<Bike> tableView = new TableView<Bike>();
    /**
     * Map für die Fahrräder
     */
    private MapGUI map = new MapGUI();
    
    /**
     * Konstruktor
     */
    public BikeManagementGUI() {
        init();
    }

    /**
     * Methode zur Initialisierung der GUI
     */
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
        
        //Bikes zum anzeigen
        var availableBikes = ctrl.loadBikes(Availability.AVAILABLE);
        var blockedBikes = ctrl.loadBikes(Availability.BLOCKED);
        
        //Liste zum Verwalten der Fahrräder, verknüpft mit der Tabelle
        ObservableList<Bike> Bikes = FXCollections.observableArrayList();
        Bikes.addAll(availableBikes);
        Bikes.addAll(blockedBikes);
        tableView.setItems(Bikes);
        
        //Bikes als Marker auf der Map setzten
        map.displayMarkers(
                availableBikes,
                Design.COLOR_MAP_BIKE_DEFAULT
                );
        map.displayMarkers(
                blockedBikes,
                Marker.Provided.RED
                );
        
        //Map onClick Action
        Consumer<Bike> onBikeClick = this::update;
        map.onClickMarker(Bike.class, onBikeClick, Design.COLOR_MAP_BIKE_SELECTED);
        
        //Box für die Anordnung der nicht Map Grafik
        var innerBox = new GridPane();
        innerBox.setHgap(30);
        innerBox.setPadding(new Insets(25, 25, 25, 25));
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(5);
        
        //Bereich für die Aktionen des Managers
        var flow = new HBox();
        Button blockButton = new Button("Fahrrad blockieren");
        Button deblockButton = new Button("Fahrrad ent-blockieren");
        blockButton.setDisable(true);
        deblockButton.setDisable(true);
        ComboBox<String> stationBox = new ComboBox<>();
        stationBox.getItems().addAll(ctrl.loadStations());
        var spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        //Grafische Konfiguration des Bereichs der Manager-Aktionen
        flow.getChildren().addAll(blockButton, deblockButton, spacer, stationBox);
        flow.setPadding(new Insets(10));
        flow.setSpacing(5);
        
        innerBox.add(tableView, 0, 0);
        innerBox.add(flow, 0, 1);
        
        //Eingabefelder auf ausgewähltes Fahrrad setzen
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            map.deselectMarker(oldSelection);
            map.selectMarker(newSelection, Design.COLOR_MAP_BIKE_SELECTED);
            
            if(newSelection.getAvailability()==Availability.AVAILABLE) {
                blockButton.setDisable(false);
                deblockButton.setDisable(true);
            }
            else if(newSelection.getAvailability()==Availability.BLOCKED) {
                blockButton.setDisable(true);
                deblockButton.setDisable(false);
            }
        });
        
        //Aktion des Blockier-Button
        blockButton.setOnAction(evt -> {
            Bike bike = tableView.getSelectionModel().getSelectedItem();
            ctrl.blockBike(bike);
            tableView.refresh();
            
            blockButton.setDisable(true);
            deblockButton.setDisable(false);
            AppContext.getInstance().showMessage(
                    "Fahrrad erfolgreich blockiert!",
                    Design.DIALOG_TIME_STANDARD,
                    Design.COLOR_DIALOG_SUCCESS);
            map.selectMarker(bike, Marker.Provided.RED);
        });
        
        //Aktion des ent-Blockier-Button
        deblockButton.setOnAction(evt -> {
            Bike bike = tableView.getSelectionModel().getSelectedItem();
            if(stationBox.getValue()!=null) {
                ctrl.deBlockBike(bike, stationBox.getValue());
                tableView.refresh();
                
                blockButton.setDisable(false);
                deblockButton.setDisable(true);
                AppContext.getInstance().showMessage(
                        "Fahrrad erfolgreich ent-blockiert!",
                        Design.DIALOG_TIME_STANDARD,
                        Design.COLOR_DIALOG_SUCCESS);
                map.selectMarker(bike, Design.COLOR_MAP_BIKE_DEFAULT);
            }
            else {
                AppContext.getInstance().showMessage(
                        "Fahrrad ent-Blockieren fehlgeschlagen! \n"
                        + "keine Station ausgewählt",
                        Design.DIALOG_TIME_STANDARD,
                        Design.COLOR_DIALOG_FAILURE);
            }
        });
        
        //Grid
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

    /**
     * Methode zum updaten bei Klick auf Marker
     * @param bike Bike des Markers
     */
    private void update(Bike bike) {
        tableView.getSelectionModel().select(bike);
    }
}
