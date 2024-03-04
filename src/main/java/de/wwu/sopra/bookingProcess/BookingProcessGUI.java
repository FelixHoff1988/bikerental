package de.wwu.sopra.bookingProcess;

import com.sothawo.mapjfx.Marker.Provided;

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
     * Standartkonstruktor
     */
    public BookingProcessGUI() {
        var currentGUI = ctrl.currentGUI();
        this.map = new MapGUI();

        switch (currentGUI) {
            case 1 -> {
                var reserveBikeGUI = new ReserveBikeGUI();
                this.map.<Bike>onClickMarker(bike -> {
                    selectBike(bike);
                    reserveBikeGUI.update(this.selectedBike);
                }, Provided.GREEN);
                build(reserveBikeGUI);
            }
            case 2 -> {
                build(new BookBikeGUI(ctrl.getReservation()));
            }
            case 3 -> {
                build(new EndBookingGUI(ctrl.getReservation()));
            }
        }
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

        HBox.setHgrow(map, Priority.ALWAYS);
        VBox.setVgrow(map, Priority.ALWAYS);

        this.getChildren().addAll(map, gui);

        var availableBikes = data.getBikes(bike -> (bike.getAvailability() == Availability.AVAILABLE));

        this.map.displayMarkers(availableBikes, Bike::getLocation, Provided.ORANGE);
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