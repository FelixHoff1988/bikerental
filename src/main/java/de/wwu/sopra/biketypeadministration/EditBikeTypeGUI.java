/**
 * @author David
 * @author Nisa
 */
package de.wwu.sopra.biketypeadministration;

import java.util.ArrayList;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.entity.BikeType;
import de.wwu.sopra.entity.CargoBike;
import de.wwu.sopra.entity.EBike;
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
        
        TableColumn<BikeType, String> column4 = new TableColumn<>("Akku-Kapazität");
        column4.setCellValueFactory(data -> {
            if(data.getValue().getTypeString().equals("EBike")) {
                EBike eb = (EBike) data.getValue();
                return new ReadOnlyStringWrapper(String.valueOf(eb.getCharge()));            
                }
            return new ReadOnlyStringWrapper("");
        });
        
        TableColumn<BikeType, String> column5 = new TableColumn<>("Ladungs-Kapazität");
        column5.setCellValueFactory(data -> {
            if(data.getValue().getTypeString().equals("CargoBike")) {
                CargoBike cb = (CargoBike) data.getValue();
                return new ReadOnlyStringWrapper(String.valueOf(cb.getCapacity()));            
                }
            return new ReadOnlyStringWrapper("");
        });

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);

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
        
        var sizeLabel = new Label("Größe in Zoll: ");
        var sizeTextField = new TextField("");
        innerBox.add(sizeLabel, 0, 1);
        innerBox.add(sizeTextField, 1, 1);
        
        var priceLabel = new Label("Preis in Cent: ");
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
        
        var capacityLabel = new Label("Ladungs-Kapazität: ");
        var capacityTextField = new TextField("");
        capacityLabel.setVisible(false);
        capacityTextField.setVisible(false);
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
        innerBox.add(successionLabel, 0, 13);

        
        typeBox.setOnAction(event -> {
            ctrl.typeBoxAction(chargeLabel, chargeTextField, capacityLabel, capacityTextField, typeBox.getValue());
        });
        newButton.setOnAction(event -> {
            String model = modelTextField.getText();
            for(BikeType b : biketypes) {
                if(b.getModel().equals(model)) {
                    AppContext.getInstance().showMessage("Fahrradtyp hinzufügen fehlgeschlagen!", 5);
                    return;
                }
                    
            }
            
            int size = (int) Integer.valueOf(sizeTextField.getText());
            int price = (int) Integer.valueOf(priceTextField.getText());
            String type1 = typeBox.getValue();
            
            int capacity = 0;
            int charge = 0;
            if(!capacityTextField.getText().isBlank())
                capacity = (int) Integer.valueOf(capacityTextField.getText());
            if(!chargeTextField.getText().isBlank())
                charge = (int) Integer.valueOf(chargeTextField.getText());
            
            BikeType type = ctrl.newButtonAction(model, size, price, type1, capacity, charge);
            if(type!=null) {
                biketypes.add(type);
                tableView.setItems(biketypes);
                AppContext.getInstance().showMessage("Fahrradtyp erfolgreich hinzugefügt", 5);
            } else {
                AppContext.getInstance().showMessage("Fahrradtyp hinzufügen fehlgeschlagen!", 5);            
            }
            
        });

        deleteButton.setOnAction(event -> {
            BikeType selectedType = tableView.getSelectionModel().getSelectedItem();
            if(ctrl.deleteButtonAction(selectedType)) {
                biketypes.remove(selectedType);
                tableView.setItems(biketypes);
                AppContext.getInstance().showMessage("Fahrradtyp erfolgreich gelöscht", 5);
            } else {
                AppContext.getInstance().showMessage("Fahrradtyp löschen fehlgeschlagen!", 5);
            }
        });

        submitButton.setOnAction(value -> {
            tableView.getSelectionModel().clearSelection();
            String model = modelTextField.getText();
            int size = (int) Integer.valueOf(sizeTextField.getText());
            int price = (int) Integer.valueOf(priceTextField.getText());
            int capacity = (int) Integer.valueOf(capacityTextField.getText());
            int charge = (int) Integer.valueOf(capacityTextField.getText());;
            BikeType selectedType = tableView.getSelectionModel().getSelectedItem();
            int index = biketypes.indexOf(selectedType);
            
            BikeType newType = ctrl.submitButtonAction(selectedType, model, size, price, capacity, charge);
            if(selectedType!=null) {
                biketypes.set(index, newType);
                tableView.setItems(biketypes);
                AppContext.getInstance().showMessage("Fahrradtyp erfolgreich geändert", 5);
            } else {
                AppContext.getInstance().showMessage("Fahrradtyp ändern fehlgeschlagen!", 5);
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
