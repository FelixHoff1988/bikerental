package de.wwu.sopra.bookingProcess;

import com.sothawo.mapjfx.Marker.Provided;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.bookingProcess.reserveBike.ReserveBikeGUI;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.map.MapGUI;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * GUI in der der komplette Fahrradleihvorgang stattfinden soll
 */
public class BookingProcessGUI extends StackPane {

    /**
     * Die Karte
     */
    private MapGUI map;
    /**
     * Das Momentan ausgewählte Rad
     */
    private Bike selectedBike;
    /**
     * Das Fenster zum reservieren eines Rads
     */
    private ReserveBikeGUI reserveBikeGUI;

    /**
     * Standartkonstruktor
     */
    public BookingProcessGUI() {
        
        this.reserveBikeGUI = new ReserveBikeGUI();
        
        build();

        this.map.<Bike>onClickMarker(bike -> {
            selectBike(bike);
            this.reserveBikeGUI.update(this.selectedBike);
        }, Provided.GREEN);

    }

    /**
     * Baut das Overlay zusammen
     */
    public void build() {

        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setAlignment(Pos.BOTTOM_RIGHT);

        this.map = new MapGUI();
        HBox.setHgrow(map, Priority.ALWAYS);
        VBox.setVgrow(map, Priority.ALWAYS);

        this.getChildren().addAll(map, this.reserveBikeGUI);

        var reader = DataProvider.getInstance();
        var availableBikes = reader.getBikes(bike -> (bike.getAvailability() == Availability.AVAILABLE));

        this.map.displayMarkers(availableBikes, bike -> bike.getLocation(), Provided.ORANGE);

    }
    
    /**
     * Updated, welches Fahrrad momentan angewählt ist
     * @param bike neues Rad
     */
    public void selectBike(Bike bike) {
        if (this.selectedBike == bike) {
            this.selectedBike = null;
        } else {
            this.selectedBike = bike;
        }
    }
}