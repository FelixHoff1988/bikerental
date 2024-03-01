package de.wwu.sopra.bookingProcess.bookBike;

import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.Reservation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * GUI um eine Reservierung zu beenden oder zu buchen
 */
public class BookBikeGUI extends FlowPane {
    
    /**
     * Standartkonstruktor
     * @param reservation Die aktuelle Reservierung
     */
    public BookBikeGUI(Reservation reservation) {
        build(reservation);
    }
    
    /**
     * Zusammenbau des GUI mit den korrekten Werten
     * @param reservation Die aktuelle Reservierung
     */
    public void build(Reservation reservation) {
        
        var bookBox = new HBox();
        var bikeInfo = new GridPane();
        var buttons = new GridPane();
        var bikeType = new Label("Fahrradtyp: ");
        var bikePrice = new Label("Preis: ");
        var bikeFeature = new Label();
        var timer = new Label("Verbleibende Zeit: ");
        var endReserveButton = new Button("Stornieren");
        var bookButton = new Button("Buchen");
        var spacer = new Pane();

        bookBox.setMaxSize(510, 70);
        bookBox.setPadding(new Insets(10, 10, 10, 10));
        bookBox.setStyle("-fx-background-color: #e6e6e6;" + "-fx-border-radius: 10;"
                + "-fx-border-style: solid;" + "-fx-border-color: #bfbfbf;" + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
        bookBox.setAlignment(Pos.CENTER);

        endReserveButton.setStyle("-fx-font-size:22");
        bookButton.setStyle("-fx-font-size:22");
        bikeType.setStyle("-fx-font-size:22");
        bikePrice.setStyle("-fx-font-size:22");
        bikeFeature.setStyle("-fx-font-size:22");
        timer.setStyle("-fx-font-size:22");

        bikeInfo.setMinWidth(300);

        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setPadding(new Insets(8, 8, 8, 8));
        this.setMaxSize(510, 70);
        this.setAlignment(Pos.BOTTOM_RIGHT);

        bikeInfo.addRow(0, bikeType);
        bikeInfo.addRow(1, bikePrice);
        bikeInfo.addRow(2, bikeFeature);
        bikeInfo.addRow(2, timer);
        buttons.addRow(0, bookButton);
        buttons.addRow(1, endReserveButton);
        bookBox.getChildren().addAll(bikeInfo, spacer, buttons);
        this.getChildren().add(bookBox);
        
    }

}
