package geofencingareaadministration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.sothawo.mapjfx.Coordinate;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.BikeStation;
import de.wwu.sopra.entity.GeofencingArea;
import de.wwu.sopra.map.MapGUI;
import de.wwu.sopra.stationadministration.EditStationCTRL;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Klasse zur Darstellung der Editierung von Geofencing Areas
 */
public class EditGeofencingAreaGUI extends HBox{
    /**
     * Constructor
     */
    private EditGeofencingAreaCTRL ctrl = new EditGeofencingAreaCTRL();

    /**
     * Konstruktor.
     */
    public EditGeofencingAreaGUI() {
        init();
    }

    /**
     * Initialisiert das GUI-Layout für die Benutzer-Editierung.
     */
    public void init() {
        
        ObservableList<GeofencingArea> geofencingAreas = FXCollections.observableArrayList(ctrl.loadGeofencingAreas());

        TableView<GeofencingArea> tableView = new TableView<GeofencingArea>();

        TableColumn<GeofencingArea, String> column1 = new TableColumn<>("Index");
        column1.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(String.valueOf(geofencingAreas.indexOf(data)));
        });


        tableView.getColumns().add(column1);

        tableView.setItems(geofencingAreas);

        var innerBox = new GridPane();
        innerBox.setHgap(30);
        innerBox.setPadding(new Insets(25, 25, 25, 25));
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(5);
        

        // Buttons zum Navigieren
        var submitButton = new Button("User Speichern");
        var newButton = new Button("Neu");
        var deleteButton = new Button("Löschen");
        
        innerBox.add(submitButton, 0, 0);
        innerBox.add(deleteButton, 1, 0);
        innerBox.add(newButton, 2, 0);
        
        
        VBox vbox = new VBox(innerBox, tableView);
        vbox.setFillWidth(true);
        
        MapGUI map = new MapGUI();
        
        
        HBox hbox = new HBox(map, vbox);
        this.getChildren().add(hbox);
        this.setAlignment(Pos.CENTER);

        newButton.setOnAction(event -> {
            
        });

        deleteButton.setOnAction(event -> {
            GeofencingArea selectedArea = tableView.getSelectionModel().getSelectedItem();
            geofencingAreas.remove(selectedArea);
            ctrl.removeGeofencingArea(selectedArea);
            tableView.setItems(geofencingAreas);
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
           
        });
    }
}
