package de.wwu.sopra.bookingProcess.bookBike;

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
 * GUI um eine Reservierung zu beenden oder zu buchen
 */
public class BookBikeGUI extends FlowPane {
    /**
     * Button zur Buchung des Fahrrads
     */
    private Button bookButton;
    /**
     * Button zum Abbrechen der Reservierung
     */
    private Button endReserveButton;

    /**
     * Reservierung
     */
    private final Reservation reservation;

    /**
     * Steuerungsklasse für das GUI
     */
    private final BookBikeCTRL ctrl = new BookBikeCTRL();

    /**
     * Funktion, die beim Abbrechen der Reservierung ausgeführt wird.
     */
    private Consumer<Reservation> cancelAction;
    
    /**
     * Standartkonstruktor
     * @param reservation Die aktuelle Reservierung
     */
    public BookBikeGUI(Reservation reservation) {
        this.reservation = reservation;
        build(reservation);
    }
    
    /**
     * Zusammenbau des GUI mit den korrekten Werten
     * @param reservation Die aktuelle Reservierung
     */
    public void build(Reservation reservation) {
        var bookBox = new HBox();
        var bikeInfo = new HBox();
        var buttons = new GridPane();
        var timer = new Label("Verbleibende Zeit: ");
        var spacer = new Pane();
        endReserveButton = new Button("Stornieren");
        bookButton = new Button("Buchen");

        bookBox.setMaxSize(510, 70);
        bookBox.setPadding(new Insets(10, 10, 10, 10));
        bookBox.setStyle("-fx-background-color: #e6e6e6;" + "-fx-border-radius: 10;"
                + "-fx-border-style: solid;" + "-fx-border-color: #bfbfbf;" + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
        bookBox.setAlignment(Pos.CENTER);

        endReserveButton.setStyle("-fx-font-size: 18;");
        bookButton.setStyle("-fx-font-size: 18;");
        timer.setStyle("-fx-font-size: 18;");
        endReserveButton.setMinWidth(170);
        bookButton.setMinWidth(170);

        buttons.setVgap(5);
        bikeInfo.setMinWidth(300);
        bikeInfo.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setPadding(new Insets(8, 8, 8, 8));
        this.setMaxSize(510, 70);
        this.setAlignment(Pos.BOTTOM_RIGHT);

        var animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                var endTime = reservation.getStartTime().plusMinutes(60);
                var now = LocalDateTime.now();
                var minutes = now.until(endTime, ChronoUnit.MINUTES);
                var seconds = now.until(endTime, ChronoUnit.SECONDS) % 60;

                if (minutes <= 0) {
                    minutes = 0;
                    if (seconds <= 0) {
                        ctrl.cancelReservation(reservation);
                        cancelAction.accept(reservation);
                    }
                }
                if (seconds < 0)
                    seconds = 0;

                var secondsString = "0" + seconds;
                secondsString = secondsString.substring(secondsString.length()-2);

                timer.setText("Verbleibende Zeit: " + minutes + ":" + secondsString);
            }
        };
        animationTimer.start();

        bikeInfo.getChildren().add(timer);
        buttons.addRow(0, bookButton);
        buttons.addRow(1, endReserveButton);
        bookBox.getChildren().addAll(bikeInfo, spacer, buttons);
        this.getChildren().add(bookBox);
    }

    /**
     * Definiert, was nach dem Abbruch einer Reservierung passiert.
     *
     * @param consumer Funktion, welche die abgebrochene Reservierung entgegennimmt
     */
    public void onStepCancel(Consumer<Reservation> consumer) {
        this.cancelAction = consumer;
        this.endReserveButton.setOnAction(event -> {
            ctrl.cancelReservation(this.reservation);
            consumer.accept(this.reservation);
        });
    }

    /**
     * Definiert, was nach dem Erstellen einer Reservierung passiert.
     *
     * @param consumer Funktion, welche die erstellte Reservierung entgegennimmt
     */
    public void onStepFinish(Consumer<Reservation> consumer) {
        this.bookButton.setOnAction(event -> {
            ctrl.bookBike(this.reservation);
            consumer.accept(this.reservation);
        });
    }
}
