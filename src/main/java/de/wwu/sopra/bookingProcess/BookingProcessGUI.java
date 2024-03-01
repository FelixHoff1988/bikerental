package de.wwu.sopra.bookingProcess;

import com.sothawo.mapjfx.Marker.Provided;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.bookingProcess.bookBike.BookBikeGUI;
import de.wwu.sopra.bookingProcess.endBooking.EndBookingGUI;
import de.wwu.sopra.bookingProcess.reserveBike.ReserveBikeGUI;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.map.MapGUI;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;

/**
 * GUI in der der komplette Fahrradleihvorgang stattfinden soll
 */
public class BookingProcessGUI extends StackPane {

    /**
     * Instanz des DataProviders
     */
    private DataProvider data = DataProvider.getInstance();
    /**
     * Instanz der Kontrollklasse
     */
    private BookingProcessCTRL ctrl = new BookingProcessCTRL();
    /**
     * Die Karte
     */
    private MapGUI map;
    /**
     * Das Momentan ausgewählte Rad
     */
    private Bike selectedBike;
    /**
     * Das aktuell geöffnete GUI
     */
    private FlowPane currentGUI;

    /**
     * Standartkonstruktor
     */
    public BookingProcessGUI() {

        switch (ctrl.currentGUI()) {
        case 1 -> {
            this.currentGUI = new ReserveBikeGUI();
            build(this.currentGUI);
        }
        case 2 -> {
            this.currentGUI = new BookBikeGUI(ctrl.getReservation());
            build(this.currentGUI);
        }
        case 3 -> {
            this.currentGUI = new EndBookingGUI(ctrl.getReservation());
            build(this.currentGUI);
        }
        }

        this.map.<Bike>onClickMarker(bike -> {
            selectBike(bike);
            this.reserveBikeGUI.update(this.selectedBike);
        }, Provided.GREEN);

    }

    /**
     * Baut das Overlay zusammen
     * 
     * @param gui das aktuell notwendige GUI
     */
    public void build(FlowPane gui) {

        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setAlignment(Pos.BOTTOM_RIGHT);

        this.map = new MapGUI();
        HBox.setHgrow(map, Priority.ALWAYS);
        VBox.setVgrow(map, Priority.ALWAYS);

        this.getChildren().addAll(map, this.reserveBikeGUI);

        var availableBikes = data.getBikes(bike -> (bike.getAvailability() == Availability.AVAILABLE));

        this.map.displayMarkers(availableBikes, bike -> bike.getLocation(), Provided.ORANGE);

    }

    /**
     * Updated, welches Fahrrad momentan angewählt ist
     * 
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