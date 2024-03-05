package de.wwu.sopra.bookingProcess;

import com.sothawo.mapjfx.Marker.Provided;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.bookingProcess.bookBike.BookBikeGUI;
import de.wwu.sopra.bookingProcess.endBooking.EndBookingGUI;
import de.wwu.sopra.bookingProcess.reserveBike.ReserveBikeGUI;
import de.wwu.sopra.entity.*;
import de.wwu.sopra.map.MapGUI;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

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
     * Standartkonstruktor
     */
    public BookingProcessGUI() {
        var currentGUI = ctrl.currentGUI();
        this.map = new MapGUI();
        build();

        var reservation = ctrl.getReservation();
        switch (currentGUI) {
            case 1 -> initReservationGUI();
            case 2 -> initBookingGUI(reservation);
            case 3 -> initEndBookingGUI(reservation);
        }

        var availableBikes = data.getBikes(bike ->
                bike.getAvailability() == Availability.AVAILABLE
                || (!bike.getReservationList().isEmpty() && bike.getReservationList().getLast() == reservation));
        this.map.displayMarkers(availableBikes, Bike::getLocation, Provided.ORANGE);
        this.map.displayMarkers(data.getStations(), BikeStation::getLocation, Provided.BLUE);

        if (reservation != null && reservation.getEndTime() == null)
            this.map.selectMarker(reservation.getBike(), Provided.GREEN);
    }

    /**
     * Baut das Overlay zusammen
     */
    public void build() {
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setAlignment(Pos.BOTTOM_RIGHT);

        HBox.setHgrow(map, Priority.ALWAYS);
        VBox.setVgrow(map, Priority.ALWAYS);

        this.getChildren().add(map);
    }

    /**
     * Initialisiert die ReservationGUI
     */
    private void initReservationGUI() {
        var reserveBikeGUI = new ReserveBikeGUI();
        Consumer<Bike> onBikeClick = reserveBikeGUI::update;

        this.map.onClickMarker(Bike.class, onBikeClick, Provided.GREEN);
        reserveBikeGUI.onStepFinish(reservation -> {
            initBookingGUI(reservation);
            map.removeOnClickAction(Bike.class);
        });

        if (this.getChildren().size() > 1)
            this.getChildren().removeLast();

        this.getChildren().add(reserveBikeGUI);
    }

    /**
     * Initialisiert die Buchungsansicht
     *
     * @param reservation Aktuelle Reservierung
     */
    private void initBookingGUI(Reservation reservation) {
        var bookingGUI = new BookBikeGUI(reservation);
        bookingGUI.onStepCancel(res -> {
            initReservationGUI();
            map.deselectMarker(reservation.getBike(), Provided.ORANGE);
        });
        bookingGUI.onStepFinish(this::initEndBookingGUI);

        if (this.getChildren().size() > 1)
            this.getChildren().removeLast();

        this.getChildren().add(bookingGUI);
    }

    /**
     * Initialisiert die Ansicht zum Beenden der Buchung
     *
     * @param reservation Aktuelle Reservierung
     */
    private void initEndBookingGUI(Reservation reservation) {
        var endBooking = new EndBookingGUI(reservation);
        this.map.displayCoordinateLines(
                data.getGeoAreas(),
                GeofencingArea::getEdges,
                "limegreen",
                "dodgerblue");

        endBooking.onStepFinish(res -> {
            initReservationGUI();
            map.deselectMarker(reservation.getBike(), Provided.ORANGE);
            data.getGeoAreas().forEach(map::removeCoordinateLine);
        });

        if (this.getChildren().size() > 1)
            this.getChildren().removeLast();

        this.getChildren().add(endBooking);
    }
}