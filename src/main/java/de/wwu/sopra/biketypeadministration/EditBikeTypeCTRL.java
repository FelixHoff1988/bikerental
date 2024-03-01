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
    
    public List<BikeType> loadBikeTypes(){
        DataProvider prov = DataProvider.getInstance();
        return prov.getBikeTypes();
    }
    
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
    
    public boolean deleteButtonAction(BikeType type) {
        DataProvider prov = DataProvider.getInstance();
        return prov.removeBikeType(type);
    }
    
    public BikeType submitButtonAction(BikeType type,String model, int size, int price, int capacity,  int charge) {
        if(type==null)
            return type;
        switch(type.getTypeString()) {
        case "EBike":
            EBike eb = (EBike) type;
            eb.setCharge(charge);
            type = eb;
            break;
        case "CargoBike":
            CargoBike cb = (CargoBike) type;
            cb.setCapacity(capacity);
            type = cb;
            break;
        }
        
        type.setModel(model);
        type.setSize(size);
        type.setPrice(price);
        return type;
    }
    


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
