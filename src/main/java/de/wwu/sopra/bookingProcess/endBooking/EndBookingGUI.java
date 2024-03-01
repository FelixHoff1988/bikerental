package de.wwu.sopra.bookingProcess.endBooking;

import de.wwu.sopra.entity.Bike;
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
 * GUI zum beenden einer Fahrt
 */
public class EndBookingGUI extends FlowPane {

    /**
     * Standartkonstruktor
     * @param bike das Gebuchte Rad
     */
    public EndBookingGUI(Bike bike) {
        build(bike);
    }

    public void build(Bike bike) {
        
        var endBookBox = new HBox();
        var bikeInfo = new GridPane();
        var bikeType = new Label("Fahrradtyp: ");
        var bikePrice = new Label("Preis: ");
        var bikeFeature = new Label();
        var timer = new Label("Fahrtzeit: ");
        var endBookButton = new Button("Fahrt beenden");
        var spacer = new Pane();

        endBookBox.setMaxSize(510, 70);
        endBookBox.setPadding(new Insets(10, 10, 10, 10));
        endBookBox.setStyle("-fx-background-color: #e6e6e6;" + "-fx-border-radius: 10;"
                + "-fx-border-style: solid;" + "-fx-border-color: #bfbfbf;" + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
        endBookBox.setAlignment(Pos.CENTER);

        endBookButton.setStyle("-fx-font-size:22");
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
        endBookBox.getChildren().addAll(bikeInfo, spacer, endBookButton);
        this.getChildren().add(endBookBox);
        
    }
    
}
