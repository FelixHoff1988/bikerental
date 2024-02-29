package de.wwu.sopra.bikeadministration.editbike;

import java.util.List;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.UserManagementGUI;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.BikeType;
import de.wwu.sopra.entity.CargoBike;
import de.wwu.sopra.entity.EBike;
import de.wwu.sopra.entity.StandardType;
import de.wwu.sopra.login.LoginGUI;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class EditBikeCTRL {
	
	public Bike addBike(String frameId, String bikeType, String model,
			Availability availability) {
		Bike newBike = null;
		
		int price = 0;
		BikeType type;
		switch(bikeType) {
//		case "Standard":
//			type = new StandardType(model, size, price);
//			break;
//			
//		case "Cargo":
//			type = new CargoBike(model, size, price, capacity);
//			break;
//			
//		case "EBike":
//			type = new EBike(model, size, price, charge);
//			break;
//			
		default:
			type = null;
			break;
		}
		
		if(type != null) {
			newBike = new Bike(type, model, null, null);
			DataProvider prov = DataProvider.getInstance();
			
			if(prov.addBike(newBike)) {
				UserManagementGUI.getInstance().changeViewNode(new LoginGUI());
			}
				
		}
		return newBike;
		
	}
	
	public boolean removeBike(Bike bike) {
		DataProvider prov = DataProvider.getInstance();
		return prov.removeBike(bike);
	}
	
	public Bike createButtonAction(String frameId, String bikeType, String model, Availability availability) {
	    Bike newBike = null;
		//Mindestens eine Eingabe ist leer
		if(frameId.isBlank() || model.isBlank() || availability==null
				 || bikeType==null){
			
			var alert = new Alert(
		            Alert.AlertType.NONE,
		            "Leere Eingaben sind nicht erlaubt!",
		            ButtonType.OK);
		    alert.setHeaderText("Fehlerhafte Eingaben");
		    alert.show();
		}
		//Alles korrekt, Fahhrad hinzufügen
		else{
			newBike = addBike(frameId, bikeType, model, availability);
		}
		return newBike;
	}
	public Bike saveButtonAction(Bike bike, String frameId, String bikeType, String model,
             Availability availability) {
	    
	    
	    //Mindestens eine Eingabe ist leer
        if(frameId.isBlank() || model.isBlank() || availability==null
                 || bikeType==null){
            
            var alert = new Alert(
                    Alert.AlertType.NONE,
                    "Leere Eingaben sind nicht erlaubt!",
                    ButtonType.OK);
            alert.setHeaderText("Fehlerhafte Eingaben");
            alert.show();
        }
        //Alles korrekt, Fahrraddaten ändern
        else {
            int price = 0;
            int size = 0;
            bike.setFrameId(frameId);
            BikeType type;
            switch(bikeType) {
            default:
                type = new StandardType(model, size, price);
            }
            bike.setType(type);
            bike.setAvailability(availability);
        }
        return bike;
	    
	}
	
	public void backButtonAction() {
		UserManagementGUI.getInstance().changeViewNode(new LoginGUI());
	}
	
	public List<Bike> loadBikes() {
		return DataProvider.getInstance().getBikes();
	}

	
	
}
