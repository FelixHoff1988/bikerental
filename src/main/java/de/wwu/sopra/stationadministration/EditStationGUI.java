package de.wwu.sopra.stationadministration;

import java.util.ArrayList;

import com.sothawo.mapjfx.Coordinate;

import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.entity.BikeStation;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.entity.UserRole;
import de.wwu.sopra.map.MapGUI;
import de.wwu.sopra.register.RegisterCTRL;
import de.wwu.sopra.useradministration.EditUserCTRL;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

/**
 * Darstellung von Bearbeitung von Stationen durch Admin
 */
public class EditStationGUI extends VBox{
    private EditStationCTRL ctrl = new EditStationCTRL();
    Coordinate c = new Coordinate((double) 0, (double) 0);
    String s = new String();

    /**
     * Konstruktor.
     */
    public EditStationGUI() {
        init();
    }

    /**
     * Initialisiert das GUI-Layout für die Benutzer-Editierung.
     */
    public void init() {

        TableView<BikeStation> tableView = new TableView<BikeStation>();

        TableColumn<BikeStation, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(data.getValue().getName());
        });

        TableColumn<BikeStation, String> column2 = new TableColumn<>("Location");
        column2.setCellValueFactory(data -> {
            String s = "";
            if(data.getValue().getLocation()!= null) {
                s = String.valueOf(data.getValue().getLocation().getLatitude())+" | "+ 
                        String.valueOf(data.getValue().getLocation().getLongitude()); 
            }
            return new ReadOnlyStringWrapper(s);
        });

        TableColumn<BikeStation, String> column3 = new TableColumn<>("Kapazität");
        column3.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(String.valueOf(data.getValue().getCapacity()));
        });


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        
        //Breite der Tabelle festlegen
        tableView.setMaxWidth(800);
        tableView.setMinWidth(800);
        
        //Breite der Spalten festlegen
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        

        ObservableList<BikeStation> bikeStations = FXCollections.observableArrayList(ctrl.loadStations());
        tableView.setItems(bikeStations);

        var innerBox = new GridPane();
        innerBox.setHgap(30);
        innerBox.setVgap(5);

        var nameLabel = new Label("Name: ");
        var nameTextField = new TextField("");
        innerBox.add(nameLabel, 0, 0);
        innerBox.add(nameTextField, 1, 0);

        var capacityLabel = new Label("Kapazität: ");
        var capacityTextField = new TextField("");
        innerBox.add(capacityLabel, 0, 1);
        innerBox.add(capacityTextField, 1, 1);
        
        var standortLabel = new Label("Setze Standort: ");
        var openMap = new Button("Öffne Karte");
        openMap.setMinWidth(100);
        innerBox.add(standortLabel, 0, 2);
        innerBox.add(openMap, 1, 2);
        

        // Buttons zum Navigieren
        var submitButton = new Button("Speichern");
        submitButton.setMinWidth(100);
        var newButton = new Button("Neu");
        newButton.setMinWidth(100);
        var deleteButton = new Button("Löschen");
        deleteButton.setMinWidth(100);
        
        FlowPane buttons = new FlowPane();
        buttons.setHgap(10);
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setPadding(new Insets(10));
        buttons.getChildren().addAll(newButton, submitButton, deleteButton);
        
        s = String.valueOf(c.getLatitude())+" | "+ 
                String.valueOf(c.getLongitude());
        var mapLabel = new Label("Koordinaten " + s);
        mapLabel.setMinWidth(250);
        innerBox.add(mapLabel, 2, 2);
        
        VBox vbox = new VBox(innerBox, buttons, tableView);
        vbox.setAlignment(Pos.CENTER);
        innerBox.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        StackPane stack = new StackPane();
        this.getChildren().add(stack);
        stack.getChildren().addAll(vbox);
        stack.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
        VBox.setVgrow(this, Priority.ALWAYS);
        
        openMap.setOnAction(event -> {
            MapGUI map = new MapGUI();
            map.setMinHeight(600);
            Button closeButton = new Button("Eingabe beenden");
            closeButton.setMinWidth(100);
            VBox box = new VBox();
            map.startMarkerPlacement(c);
            box.getChildren().addAll(map, closeButton);
            stack.getChildren().add(box);
            
            closeButton.setOnAction(event2 -> {
                stack.getChildren().remove(1);
                c = map.finalizeMarkerPlacement();
                s = String.valueOf(c.getLatitude())+" | "+ 
                        String.valueOf(c.getLongitude());
                mapLabel.setText(s);
            });
            
        });

        newButton.setOnAction(event -> {
            ArrayList<Boolean> list = new ArrayList<Boolean>();

            list.add(ctrl.testTextField("^[a-zA-Z0-9]*$", nameTextField));
            list.add(ctrl.testTextField("^[0-9]+$", capacityTextField));

            if (areAllTrue(list)) {
                TextField[] textFieldsBikeStation = innerBox.getChildren().stream()
                        .filter(node -> node.getClass() == TextField.class)
                        .toArray(TextField[]::new);
                BikeStation addedBikeStation = ctrl.addBikeStation(textFieldsBikeStation);
                addedBikeStation.setLocation(c);
                bikeStations.add(addedBikeStation);
                tableView.setItems(bikeStations);
            }
        });

        deleteButton.setOnAction(event -> {
            BikeStation selectedBikeStation = tableView.getSelectionModel().getSelectedItem();
            bikeStations.remove(selectedBikeStation);
            ctrl.removeBikeStation(selectedBikeStation);
            tableView.setItems(bikeStations);
        });

        submitButton.setOnAction(value -> {
            ArrayList<Boolean> list = new ArrayList<Boolean>();

            BikeStation selectedBikeStation = tableView.getSelectionModel().getSelectedItem();

            list.add(ctrl.testTextField("^[a-zA-Z0-9]*$", nameTextField));
            list.add(ctrl.testTextField("^[0-9]+$", capacityTextField));

            if (areAllTrue(list)) {
                int index = bikeStations.indexOf(selectedBikeStation);
                selectedBikeStation.setCapacity(Integer.valueOf(capacityTextField.getText()));
                selectedBikeStation.setName(nameTextField.getText());
                selectedBikeStation.setLocation(c);
                bikeStations.set(index, selectedBikeStation);
                tableView.setItems(bikeStations);
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameTextField.setText(newSelection.getName());
                capacityTextField.setText(String.valueOf(newSelection.getCapacity()));
                c = newSelection.getLocation();
                s = String.valueOf(c.getLatitude())+" | "+ 
                        String.valueOf(c.getLongitude());
                mapLabel.setText(s);
            }
        });
    }

    /**
     * Prüft, ob alle Elemente einer ArrayList von Typ Boolean wahr sind
     * 
     * @param array
     * @return
     */
    private static boolean areAllTrue(ArrayList<Boolean> array) {
        for (boolean b : array)
            if (!b)
                return false;
        return true;
    }
}
