package de.wwu.sopra.geofencingareaadministration;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.entity.GeofencingArea;
import de.wwu.sopra.map.MapGUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.List;

/**
 * Klasse zur Darstellung des Editierens von Geofencing Areas
 */
public class EditGeofencingAreaGUI extends VBox {
    /**
     * Steuerungsklasse für das GUI
     */
    private final EditGeofencingAreaCTRL ctrl = new EditGeofencingAreaCTRL();

    /**
     * Aktuell angeklickte GeofencingArea
     */
    private GeofencingArea selectedArea;

    /**
     * Knopf zum Löschen von GeofencingAreas
     */
    private Button deleteButton;

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
        var innerBox = new HBox();
        innerBox.setSpacing(20);
        innerBox.setPadding(new Insets(25, 0, 25, 0));
        innerBox.setAlignment(Pos.CENTER);

        var spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setPadding(new Insets(25));

        // Buttons zum Navigieren
        var startDesign = new Button("Erstellen starten");
        var endAndSaveButton = new Button("Area hinzufügen");
        var endAndDiscardButton = new Button("Area verwerfen");
        deleteButton = new Button("Löschen");
        
        endAndSaveButton.setDisable(true);
        endAndDiscardButton.setDisable(true);
        deleteButton.setDisable(true);
       
        innerBox.getChildren().addAll(startDesign, deleteButton, spacer, endAndSaveButton, endAndDiscardButton);
        
        MapGUI map = new MapGUI();
        HBox.setHgrow(map, Priority.ALWAYS);
        VBox.setVgrow(map, Priority.ALWAYS);
        ctrl.initializeAreas(map);
        enableAreaSelection(map);
        
        startDesign.setOnAction(event -> {
            map.drawArea();
            startDesign.setDisable(true);
            endAndSaveButton.setDisable(false);
            endAndDiscardButton.setDisable(false);
            deleteButton.setDisable(true);
            disableAreaSelection(map);
            if (this.selectedArea != null) {
                map.deselectCoordinateLine(
                        this.selectedArea,
                        "limegreen",
                        "dodgerblue");
                this.selectedArea = null;
            }
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
                startDesign.setDisable(false);
                deleteButton.setDisable(false);
                enableAreaSelection(map);
            }
        });
        
        endAndDiscardButton.setOnAction(event -> {
            map.finalizeArea();
            endAndSaveButton.setDisable(true);
            endAndDiscardButton.setDisable(true);
            startDesign.setDisable(false);
            deleteButton.setDisable(false);
            enableAreaSelection(map);
        });

        deleteButton.setOnAction(event -> {
            if (selectedArea == null)
                return;

            ctrl.removeGeofencingArea(selectedArea);
            map.removeCoordinateLine(selectedArea);
            selectedArea = null;
        });

        this.getChildren().addAll(map, innerBox);
    }

    /**
     * Aktiviert die Auswahl von GeoAreas auf der Map.
     *
     * @param map MapGUI Komponente
     */
    private void enableAreaSelection(MapGUI map) {
        map.onClickCoordinateLine(
                GeofencingArea.class,
                this::onClickArea,
                "orange",
                "red");
    }

    /**
     * Deaktiviert die Auswahl von GeoAreas auf der Map.
     *
     * @param map MapGUI Komponente
     */
    private void disableAreaSelection(MapGUI map) {
        map.removeCoordinateLineOnClickAction(GeofencingArea.class);
    }

    /**
     * Wird beim Klick auf eine GeoArea ausgeführt.
     *
     * @param area Ausgewählte GeoArea
     */
    private void onClickArea(GeofencingArea area) {
        if (area == this.selectedArea) {
            this.selectedArea = null;
            deleteButton.setDisable(true);
        } else {
            this.selectedArea = area;
            deleteButton.setDisable(false);
        }
    }
}
