/**
 * @author David
 * @author Nisa
 */
package de.wwu.sopra.biketypeadministration;

import java.util.ArrayList;

import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.entity.BikeType;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.entity.UserRole;
import de.wwu.sopra.register.RegisterCTRL;
import de.wwu.sopra.useradministration.EditUserCTRL;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Grenzklasse für das Verwalten der BikeTypes
 */
public class EditBikeTypeGUI extends HBox{
    

    private EditBikeTypeCTRL ctrl = new EditBikeTypeCTRL();

    /**
     * Konstruktor.
     */
    public EditBikeTypeGUI() {
        init();
    }

    /**
     * Initialisiert das GUI-Layout für die Fahrradtyp-Editierung.
     */
    public void init() {

        TableView<BikeType> tableView = new TableView<BikeType>();

        TableColumn<BikeType, String> column1 = new TableColumn<>("Modell");
        column1.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(data.getValue().getModel());
        });
        
        TableColumn<BikeType, String> column2 = new TableColumn<>("Größe");
        column2.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(String.valueOf(data.getValue().getSize()));
        });
        
        TableColumn<BikeType, String> column3 = new TableColumn<>("Preis");
        column3.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(String.valueOf(data.getValue().getPrice()));
        });

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        ObservableList<BikeType> biketypes = FXCollections.observableArrayList(ctrl.loadBikeTypes());
        tableView.setItems(biketypes);

        var innerBox = new GridPane();
        innerBox.setHgap(30);
        innerBox.setPadding(new Insets(25, 25, 25, 25));
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(5);

        var modelLabel = new Label("Modell: ");
        var modelTextField = new TextField("");
        innerBox.add(modelLabel, 0, 0);
        innerBox.add(modelTextField, 1, 0);
        
        var sizeLabel = new Label("Größe: ");
        var sizeTextField = new TextField("");
        innerBox.add(sizeLabel, 0, 1);
        innerBox.add(sizeTextField, 1, 1);
        
        var priceLabel = new Label("Preis: ");
        var priceTextField = new TextField("");
        innerBox.add(priceLabel, 0, 2);
        innerBox.add(priceTextField, 1, 2);
        
        var typeLabel = new Label("Fahhrad-Art: ");
        var typeBox = new ComboBox<String>();
        typeBox.getItems().addAll("Standard","EBike","CargoBike");
        innerBox.add(typeLabel, 0, 3);
        innerBox.add(typeBox, 1, 3);
        
        var chargeLabel = new Label("Akku-Kapazität: ");
        var chargeTextField = new TextField("");
        chargeLabel.setVisible(false);
        chargeTextField.setVisible(false);
        innerBox.add(chargeLabel, 0, 4);
        innerBox.add(chargeTextField, 1, 4);
        
        var capacityLabel = new Label("Lade-Kapazität");
        var capacityTextField = new TextField("");
        capacityLabel.setVisible(false);
        chargeTextField.setVisible(false);
        innerBox.add(capacityLabel, 0, 4);
        innerBox.add(capacityTextField, 1, 4);
        
        
                
        
        // Buttons zum Navigieren
        var submitButton = new Button("BikeType Speichern");
        var newButton = new Button("Neu");
        var deleteButton = new Button("Löschen");

        innerBox.add(new Label(), 0, 10);
        innerBox.add(submitButton, 5, 12);
        innerBox.add(deleteButton, 4, 12);
        innerBox.add(newButton, 0, 12);

        Label successionLabel = new Label("Änderung erfolgreich");
        successionLabel.setVisible(false);
        innerBox.add(successionLabel, 0, 12);

        
        typeBox.setOnAction(event -> {
            ctrl.typeBoxAction(chargeLabel, chargeTextField, capacityLabel, capacityTextField, typeBox.getValue());
        });
        newButton.setOnAction(event -> {
            String model = modelTextField.getText();
            int size = (int) Integer.valueOf(sizeTextField.getText());
            int price = (int) Integer.valueOf(priceTextField.getText());
            String type1 = typeBox.getValue();
            
            BikeType type = ctrl.newButtonAction(model, size, price, type1);
            if(type!=null) {
                biketypes.add(type);
                tableView.setItems(biketypes);
            }
            
        });

        deleteButton.setOnAction(event -> {
            BikeType selectedType = tableView.getSelectionModel().getSelectedItem();
            if(ctrl.deleteButtonAction(selectedType)) {
                biketypes.remove(selectedType);
                tableView.setItems(biketypes);
            }
        });

        submitButton.setOnAction(value -> {
            String model = modelTextField.getText();
            int size = (int) Integer.valueOf(sizeTextField.getText());
            int price = (int) Integer.valueOf(priceTextField.getText());
            int capacity = -1;
            int charge = -1;
            String type1 = typeBox.getValue();
            BikeType selectedType = tableView.getSelectionModel().getSelectedItem();
            int index = biketypes.indexOf(selectedType);
            
            ctrl.submitButtonAction(selectedType, model, size, price, capacity, charge);
            if(selectedType!=null) {
                biketypes.set(index, selectedType);
                tableView.setItems(biketypes);
            }
            
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            modelTextField.setText(newSelection.getModel());
            sizeTextField.setText(String.valueOf(newSelection.getSize()));
            priceTextField.setText(String.valueOf(newSelection.getPrice()));
            typeBox.setValue(newSelection.getTypeString());
        });

        VBox vbox = new VBox(innerBox, tableView);
        vbox.setFillWidth(true);

        this.getChildren().addAll(vbox);
        this.setAlignment(Pos.CENTER);
        VBox.setVgrow(this, Priority.ALWAYS);

    }
    
}
