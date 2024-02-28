package de.wwu.sopra.bikeadministration.addbike;

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

public class AddBikeCTRL {
	
	public void addBike(String frameId, String bikeType, String model, int size, 
			Availability availability, int charge, int capacity) {
		
		int price = 0;
		BikeType type;
		switch(bikeType) {
		case "Standard":
			type = new StandardType(model, size, price);
			break;
			
		case "Cargo":
			type = new CargoBike(model, size, price, capacity);
			break;
			
		case "EBike":
			type = new EBike(model, size, price, charge);
			break;
			
		default:
			type = null;
			break;
		}
		
		if(type != null) {
			System.out.println("erstellen: ");
			Bike newBike = new Bike(type, model, null, null);
			DataProvider prov = DataProvider.getInstance();
			
			prov.addBikeType(type);
			
			if(prov.addBike(newBike)) {
				System.out.println("erstellen2: ");
				UserManagementGUI.getInstance().changeViewNode(new LoginGUI());
			}
				
		}
		
	}
	
	public void createButtonaction(String frameId, String bikeType, String model,
			int size, Availability availability, int charge, int capacity,
			TextField chargeTextField, TextField capacityTextField) {
		//Mindestens eine Eingabe ist leer
		if(frameId.isBlank() || model.isBlank() || !(12<=size && size<=25) || availability==null
				 || bikeType==null){
			
			System.out.println("frameid: "+frameId.isBlank());
			System.out.println("model: "+model.isBlank());
			System.out.println("size: "+!(12>=size && size<=25));
			System.out.println("availability: "+availability==null);
			System.out.println("bikeType: "+bikeType==null);
			
			
			var alert = new Alert(
		            Alert.AlertType.NONE,
		            "Leere Eingaben sind nicht erlaubt!",
		            ButtonType.OK);
		    alert.setHeaderText("Fehlerhafte Eingaben");
		    alert.show();
		}
		//Fehlerhafte Eingabe bei der Akkukapazität eines EBikes
		else if(charge<=0 && bikeType.equals("EBike")) {
			chargeTextField.setStyle("-fx-background-color: #FFA59D;");
		}
		//Fehlerhafte Eingabe für die Kapazität eines CargoBikes
		else if(capacity<=0 && bikeType.equals("Cargo")) {
			capacityTextField.setStyle("-fx-background-color: #FFA59D;");
		}
		//Alles korrekt, Fahhrad hinzufügen
		else{
			addBike(frameId, bikeType, model, size, availability, charge, capacity);
		}
	}
	
}
