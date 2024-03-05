package de.wwu.sopra.bookingProcess.reserveBike;

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

import java.util.function.Consumer;

/**
 * Grenzklasse der Reservierung eines Fahrrads
 */
public class ReserveBikeGUI extends FlowPane {

    /**
     * Speichert die Kontrollklasse ab
     */
    private final ReserveBikeCTRL ctrl;
    /**
     * Die äußerste Box
     */
    private HBox reserveBox;
    /**
     * Der Fahrradname
     */
    private Label bikeType;
    /**
     * Preis
     */
    private Label bikePrice;
    /**
     * Zusatzinformationen
     */
    private Label bikeFeature;
    /**
     * Zeigt die Informationen an
     */
    private GridPane bikeInfo;
    /**
     * Wenn kein Rad angewählt ist
     */
    private Label disclaimer;
    /**
     * Knopf zum Reservieren
     */
    private Button reserveButton;
    /**
     * Das Momentan ausgewählte Rad
     */
    private Bike currentBike;

    /**
     * Standartkonstruktor initialisiert das Overlay
     */
    public ReserveBikeGUI() {
        this.ctrl = new ReserveBikeCTRL();
        build();
    }

    /**
     * Baut das Anzeigefeld zusammen
     */
    public void build() {
        this.reserveBox = new HBox();
        this.bikeInfo = new GridPane();
        this.bikeType = new Label("Fahrradtyp: ");
        this.disclaimer = new Label("Bitte wählen Sie ein Fahrrad aus.");
        this.bikePrice = new Label("Preis: ");
        this.bikeFeature = new Label();
        this.reserveButton = new Button("Reservieren");
        var spacer = new Pane();

        this.reserveBox.setMaxSize(510, 70);
        this.reserveBox.setPadding(new Insets(10, 10, 10, 10));
        this.reserveBox.setStyle("-fx-background-color: #e6e6e6;" + "-fx-border-radius: 10;"
                + "-fx-border-style: solid;" + "-fx-border-color: #bfbfbf;" + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");
        this.reserveBox.setAlignment(Pos.CENTER);

        this.reserveButton.setStyle("-fx-font-size: 18;");
        this.bikeType.setStyle("-fx-font-size: 18;");
        this.bikePrice.setStyle("-fx-font-size: 18;");
        this.disclaimer.setStyle("-fx-font-size: 18;");
        this.bikeFeature.setStyle("-fx-font-size: 18;");

        this.bikeInfo.setMinWidth(300);
        this.disclaimer.setMinWidth(300);

        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.reserveButton.setDisable(true);

        this.setPadding(new Insets(8, 8, 8, 8));
        this.setMaxSize(510, 70);
        this.setAlignment(Pos.BOTTOM_RIGHT);

        this.bikeInfo.addRow(0, bikeType);
        this.bikeInfo.addRow(1, bikePrice);
        this.bikeInfo.addRow(2, bikeFeature);
        this.reserveBox.getChildren().addAll(disclaimer, spacer, reserveButton);
        this.getChildren().add(reserveBox);
    }

    /**
     * Updated die übersicht auf ein neues Rad
     * @param newBike neues Fahrrad welches Angezeigt werden soll
     */
    public void update(Bike newBike) {
        if (this.currentBike == newBike)
            newBike = null;

        this.currentBike = newBike;
        
        if (null == newBike) {
            this.reserveBox.getChildren().removeFirst();
            this.reserveBox.getChildren().addFirst(this.disclaimer);

            this.reserveButton.setDisable(true);
        } else {
            String type = newBike.getType().getModel();

            int cent = newBike.getType().getPrice();
            int euro = cent / 100;
            cent %= 100;
            String centString = String.valueOf(cent);
            centString = "0" + centString;
            centString = centString.substring(centString.length() - 2);

            String price = euro + "," + centString + "€";

            String feature = newBike.getType().getFeatureDescription();

            this.bikeType.setText("Fahrradtyp: " + type);
            this.bikePrice.setText("Preis: " + price + "/h");
            this.bikeFeature.setText(feature);

            this.bikeFeature.setVisible(feature != null);

            this.reserveBox.getChildren().removeFirst();
            this.reserveBox.getChildren().addFirst(this.bikeInfo);

            this.reserveButton.setDisable(false);
        }
    }

    /**
     * Definiert, was nach dem Erstellen einer Reservierung passiert.
     *
     * @param consumer Funktion, welche die erstellte Reservierung entgegennimmt
     */
    public void onStepFinish(Consumer<Reservation> consumer) {
        this.reserveButton.setOnAction(event -> {
            var reservation = ctrl.reserveBike(this.currentBike);
            consumer.accept(reservation);
        });
    }
}