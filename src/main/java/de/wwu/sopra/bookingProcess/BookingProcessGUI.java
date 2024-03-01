package de.wwu.sopra.bookingProcess;

import com.sothawo.mapjfx.Marker.Provided;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.bookingProcess.reserveBike.ReserveBikeCTRL;
import de.wwu.sopra.bookingProcess.reserveBike.ReserveBikeGUI;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.map.MapGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 
 */
public class BookingProcessGUI extends StackPane {

    /**
     * 
     */
    private MapGUI map;
    /**
     * 
     */
    private Bike selectedBike;
    /**
     * 
     */
    private ReserveBikeGUI reserveBikeGUI;

    /**
     * 
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
     * 
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
     * 
     */
    public void selectBike(Bike bike) {
        if (this.selectedBike == bike) {
            this.selectedBike = null;
        } else {
            this.selectedBike = bike;
        }
    }
}