package de.wwu.sopra.geofencingareaadministration;

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

        var innerBox = new GridPane();
        innerBox.setHgap(30);
        innerBox.setPadding(new Insets(25, 25, 25, 25));
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(5);
        
        innerBox.setMinWidth(900);

        // Buttons zum Navigieren
        var startDesign = new Button("Erstellen starten");
        var endDesign = new Button("Erstellen beenden");
        var deleteButton = new Button("Löschen");
       
        innerBox.add(deleteButton, 0, 0);
        innerBox.add(startDesign, 1, 0);
        innerBox.add(endDesign, 2, 0);
        
        MapGUI map = new MapGUI();
        map.setMinHeight(600);
        ctrl.initializeAreas(map);
        
        startDesign.setOnAction(event -> {
            map.drawArea();
        });
        
        endDesign.setOnAction(event -> {
            map.finalizeArea();
        });
        
        VBox vbox = new VBox(map, innerBox);
        this.getChildren().add(vbox);
        this.setAlignment(Pos.CENTER);
    }
}
