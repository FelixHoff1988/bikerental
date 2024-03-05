
/**
 * @author Nisa 
 */
package de.wwu.sopra.reportdefect;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.Design;
import de.wwu.sopra.entity.Bike;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Diese Klasse repräsentiert die GUI zum Melden von defekten Fahrraedern. Der
 * User gibt die Fahrradnummer und den Schaden ein und meldet somit einen
 * Schaden mit Klick auf den Absendebutton
 */
public class ReportGUI extends VBox {

    ReportCTRL ctrl = new ReportCTRL();

    /**
     * Konstruktor fuer ReportGUI und initialisierung der Kompenenten
     */

    public ReportGUI() {
        init();

    }

    /**
     * Initialisiert die Komponenten also das Eingabefeld fuer die Fahrradnummer die
     * Beschreibung des Schadens und ein Button zum Absenden um den Schaden zu
     * melden.
     */
    public void init() {

        // Layout zum Organisieren der Komponenten
        var innerBox = new GridPane();
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(30);

        // Textfeld fuer die Fahrradnummer zum Eingeben
        var bikeIdInput = new TextField();
        bikeIdInput.setPromptText("Fahrrad Nummer");
        bikeIdInput.setPrefWidth(400);

        // Textarea zum Eingeben der Schadensbeschreibung
        var reportInput = new TextArea();
        reportInput.setPromptText("Beschreiben Sie den vorgefundenen Schaden...");
        reportInput.setPrefSize(600, 200);

        // Button zum Absenden des Schadens
        var submitButton = new Button();
        submitButton.setText("Schaden melden");
        submitButton.setPrefWidth(200);

        submitButton.setOnAction(event -> {
            // teste ob Werte korrekt sind
            Bike bike = ctrl.findBike(bikeIdInput.getText());
            if (reportInput.getText() != "" & bike != null) {
                AppContext.getInstance().changeViewNode(new Pane());
            } else {
                AppContext.getInstance().showMessage(
                        "Es existiert kein Fahrrad mit der angegebenen Fahrradnummer.",
                        Design.DIALOG_TIME_STANDARD,
                        Design.COLOR_DIALOG_FAILURE);
            }
        });

        // Layout zur VBox hinzufuegen
        this.getChildren().addAll(innerBox);

        // Ausrichten und Wachstum der VBox
        this.setAlignment(Pos.CENTER);
        VBox.setVgrow(this, Priority.ALWAYS);

        // Hbox fuer den Submitbutton
        var controls = new HBox();
        controls.setPrefWidth(400);

        controls.getChildren().addAll(submitButton);

        // Anordnung
        innerBox.add(bikeIdInput, 0, 0);
        innerBox.add(reportInput, 0, 1);
        innerBox.add(controls, 0, 2);

    }

}
