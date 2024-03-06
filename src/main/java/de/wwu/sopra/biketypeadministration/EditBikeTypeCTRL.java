/**
 * @author David
 * @author Nisa
 */
package de.wwu.sopra.biketypeadministration;

import java.util.List;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.BikeType;
import de.wwu.sopra.entity.CargoBike;
import de.wwu.sopra.entity.EBike;
import de.wwu.sopra.entity.StandardType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Kontrollklasse zum Verwalten der BikeTypes
 */
public class EditBikeTypeCTRL {
    
    /**
     * Konstruktor
     */
    public EditBikeTypeCTRL() {
        
    }
    
    /**
     * BikeTypes als Liste laden
     * @return Liste aller BikeTypes im System
     */
    public List<BikeType> loadBikeTypes(){
        DataProvider prov = DataProvider.getInstance();
        return prov.getBikeTypes();
    }
    
    /**
     * Methode für die Aktion des Buttons zum erstellen eines Fahrrad-Typ
     * @param model Modell den neuen Bikes
     * @param size Größe des neuen Bikes
     * @param price Preis des neuen Bikes
     * @param type1 Typ des neuen Bikes
     * @param capacity Kapazität des neuen Bikes
     * @param charge AkkuKapazität des neuen Bikes
     * @return erstellten FahrradTyp
     */
    public BikeType newButtonAction(String model, int size, int price, String type1, int capacity, int charge) {
        DataProvider prov = DataProvider.getInstance();
        BikeType type = null;
        if(!(model.isBlank()||type1.isBlank())) {
            switch(type1) {
            case "Standard":
                type = new StandardType(model, size, price);
                prov.addBikeType(type);
                break;
            case "EBike":
                type = new EBike(model, size, price, charge);
                prov.addBikeType(type);
                break;
            case "CargoBike":
                type = new CargoBike(model, size, price, capacity);
                prov.addBikeType(type);
                break;
            }
        }
        return type;
        }
    
    /**
     * Methode für den Button zum löschen eines Fahrrad-Typ
     * @param type zu löschender Typ
     * @return true, falls erfolgrecih, sonst false
     */
    public boolean deleteButtonAction(BikeType type) {
        DataProvider prov = DataProvider.getInstance();
        return prov.removeBikeType(type);
    }
    
    /**
     * Methode für den Button zum ändern eines Fahrrad-Typ
     * @param type zu ändernder Typ
     * @param model Model des zu ändernden Fahrrad-Typ
     * @param size Größe des zu ändernden Fahrrad-Typ
     * @param price Preis des zu ändernden Fahrrad-Typ
     * @param capacity Kapazität des zu ändernden Fahrrad-Typ
     * @param distance AkkuKapazität des zu ändernden Fahrrad-Typ
     * @return geänderter Fahrrad-Typ
     */
    public BikeType submitButtonAction(String type,String model, int size, int price, int capacity,  int distance) {
        switch(type) {
        case "EBike":
            EBike eb = new EBike(model, size, price, distance);
            return eb;
        case "CargoBike":
            CargoBike cb = new CargoBike(model, size, price, capacity);
            return cb;
        case "Standart":
            StandardType sb = new StandardType(model, size, price);
            return sb;
        }
        return null;
    }
    


    /**
     * Mehtode für die Aktion der Box für die 3 Typen
     * @param chargeLabel Label der AkkuKapazität
     * @param chargeTextField TextFeld der AkkuKapazität
     * @param capacityLabel Label der Kapazität
     * @param capacityTextField TextFeld der Kapazität
     * @param selectedItem ausgewählter Typ
     */
    public void typeBoxAction(Label chargeLabel, TextField chargeTextField, Label capacityLabel,
            TextField capacityTextField, String selectedItem) {
        switch(selectedItem) {
        case "Standard":
            chargeLabel.setVisible(false);
            chargeTextField.setVisible(false);
            capacityLabel.setVisible(false);
            capacityTextField.setVisible(false);
            break;
            
        case "EBike":
            chargeLabel.setVisible(true);
            chargeTextField.setVisible(true);
            capacityLabel.setVisible(false);
            capacityTextField.setVisible(false);
            break;
            
        case "CargoBike":
            chargeLabel.setVisible(false);
            chargeTextField.setVisible(false);
            capacityLabel.setVisible(true);
            capacityTextField.setVisible(true);
            break;
        }
    }}
