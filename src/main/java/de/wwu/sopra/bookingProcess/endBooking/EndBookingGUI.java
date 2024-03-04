package de.wwu.sopra.bookingProcess.endBooking;

import de.wwu.sopra.entity.Reservation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

/**
 * GUI zum beenden einer Fahrt
 */
public class EndBookingGUI extends FlowPane {
    /**
     * Reservierung
     */
    private final Reservation reservation;

    /**
     * Button zum Beenden der Buchung
     */
    private Button endBookButton;

    /**
     * Steuerungsklasse des GUI
     */
    private final EndBookingCTRL ctrl = new EndBookingCTRL();

    /**
     * Standartkonstruktor
     * @param reservation Die aktuelle Buchung
     */
    public EndBookingGUI(Reservation reservation) {
        this.reservation = reservation;
        build(reservation);
    }

    /**
     * Aufbau des GUI
     * @param reservation Die aktuelle Buchung
     */
    public void build(Reservation reservation) {
        var endBookBox = new HBox();
        var bikeInfo = new GridPane();
        var timer = new Label("Fahrzeit: ");
        var bikePrice = new Label("Aktueller Preis: ");
        var spacer = new Pane();
        endBookButton = new Button("Fahrt beenden");

        endBookBox.setMaxSize(510, 70);
        endBookBox.setPadding(new Insets(10, 10, 10, 10));
        endBookBox.setStyle("-fx-background-color: #e6e6e6;" + "-fx-border-radius: 10;"
                + "-fx-border-style: solid;" + "-fx-border-color: #bfbfbf;" + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
        endBookBox.setAlignment(Pos.CENTER);

        endBookButton.setStyle("-fx-font-size: 18;");
        bikePrice.setStyle("-fx-font-size: 18;");
        timer.setStyle("-fx-font-size: 18;");

        bikeInfo.setMinWidth(300);

        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setPadding(new Insets(8, 8, 8, 8));
        this.setMaxSize(510, 70);
        this.setAlignment(Pos.BOTTOM_RIGHT);

        var animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                var bookingTime = reservation.getBookingTime();
                var now = LocalDateTime.now();
                var minutes = bookingTime.until(now, ChronoUnit.MINUTES);
                var seconds = bookingTime.until(now, ChronoUnit.SECONDS) % 60;

                if (minutes <= 0)
                    minutes = 0;
                if (seconds < 0)
                    seconds = 0;

                var price = reservation.getPrice() * (minutes / 60F);
                var cent = ("0" + (int)(price % 100));
                cent = cent.substring(cent.length() - 2);
                var euro = (int)(price / 100);

                var secondString = "0" + seconds;
                secondString = secondString.substring(secondString.length() - 2);

                timer.setText("Fahrzeit: " + minutes + ":" + secondString);
                bikePrice.setText("Aktueller Preis: " + euro + "," + cent + " €");
            }
        };
        animationTimer.start();

        bikeInfo.addRow(0, bikePrice);
        bikeInfo.addRow(1, timer);
        endBookBox.getChildren().addAll(bikeInfo, spacer, endBookButton);
        this.getChildren().add(endBookBox);
    }

    /**
     * Definiert, was nach dem Erstellen einer Reservierung passiert.
     *
     * @param consumer Funktion, welche die erstellte Reservierung entgegennimmt
     */
    public void onStepFinish(Consumer<Reservation> consumer) {
        this.endBookButton.setOnAction(event -> {
            ctrl.endBooking(this.reservation);
            consumer.accept(this.reservation);
        });
    }
}
