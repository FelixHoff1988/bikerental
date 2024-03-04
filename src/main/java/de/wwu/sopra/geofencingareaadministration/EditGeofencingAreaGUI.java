package de.wwu.sopra.geofencingareaadministration;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.entity.GeofencingArea;
import de.wwu.sopra.map.MapGUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Klasse zur Darstellung des Editierens von Geofencing Areas
 */
public class EditGeofencingAreaGUI extends HBox{
    /**
     * Steuerungsklasse für das GUI
     */
    private final EditGeofencingAreaCTRL ctrl = new EditGeofencingAreaCTRL();

    /**
     * Aktuell angeklickte GeofencingArea
     */
    private GeofencingArea selectedArea;

    /**
     * Konstruktor.
     */
    public EditGeofencingAreaGUI() {
        init();
    }

    /**
     * Initialisiert das GUI-Layout für das Benutzer-Editieren.
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
        var endAndSaveButton = new Button("Area hinzufügen");
        var endAndDiscardButton = new Button("Area verwerfen");
        var deleteButton = new Button("Löschen");
        
        endAndSaveButton.setDisable(true);
        endAndDiscardButton.setDisable(true);
       
        innerBox.add(deleteButton, 0, 0);
        innerBox.add(startDesign, 1, 0);
        innerBox.add(endAndSaveButton, 2, 0);
        innerBox.add(endAndDiscardButton, 3, 0);
        
        MapGUI map = new MapGUI();
        map.setMinHeight(600);
        ctrl.initializeAreas(map);
        
        startDesign.setOnAction(event -> {
            map.drawArea();
            endAndSaveButton.setDisable(false);
            endAndDiscardButton.setDisable(false);
        });
        
        endAndSaveButton.setOnAction(event -> {
            var geoArea = map.finalizeArea();
            if (geoArea == null) {
                AppContext.getInstance().showMessage(
                        "Die Geofencing-Area muss mindestens drei Eckpunkte besitzen!",
                        5,
                        "#FFCCDD");
            } else {
                ctrl.addGeofencingArea(geoArea);
                map.displayCoordinateLines(
                        List.of(geoArea), GeofencingArea::getEdges,
                        "limegreen",
                        "dodgerblue");
                endAndSaveButton.setDisable(true);
                endAndDiscardButton.setDisable(true);
            }
        });
        
        endAndDiscardButton.setOnAction(event -> {
            map.finalizeArea();
            endAndSaveButton.setDisable(true);
            endAndDiscardButton.setDisable(true);
        });

        deleteButton.setOnAction(event -> {
            if (selectedArea == null)
                return;

            ctrl.removeGeofencingArea(selectedArea);
            map.removeCoordinateLine(selectedArea);
            selectedArea = null;
        });

        map.<GeofencingArea>onClickCoordinateLine(area -> {
            if (area == this.selectedArea)
                this.selectedArea = null;
            else
                this.selectedArea = area;
        }, "orange", "red");
        
        VBox vbox = new VBox(map, innerBox);
        this.getChildren().add(vbox);
        this.setAlignment(Pos.CENTER);
    }
}
