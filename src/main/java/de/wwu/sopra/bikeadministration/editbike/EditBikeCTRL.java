package de.wwu.sopra.bikeadministration.editbike;

import java.util.ArrayList;
import java.util.List;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
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
	
	public Bike addBike(String frameId, String model,
			Availability availability) {
		Bike newBike = null;
		List<BikeType> types = DataProvider.getInstance().getBikeTypes();
		BikeType type = null;
		for(BikeType t:types) {
		    if(t.getModel().equals(model))
		        type = t;
		}
		
		if(type != null) {
			newBike = new Bike(type, frameId, null, null);
			newBike.setAvailability(availability);
			DataProvider.getInstance().addBike(newBike);	
		}
		return newBike;
		
	}
	
	public boolean removeBike(Bike bike) {
		DataProvider prov = DataProvider.getInstance();
		return prov.removeBike(bike);
	}
	
	public Bike createButtonAction(String frameId, String model, Availability availability) {
	    Bike newBike = null;
		//Mindestens eine Eingabe ist leer
		if(frameId.isBlank() || availability==null
				 || model==null){
			
		    AppContext.getInstance().showMessage("Fahrrad hinzufügen fehlgeschlagen! \n"
		            + "Leere Eingabe", 5, "#FFCCDD");
		}
		//Alles korrekt, Fahhrad hinzufügen
		else{
		    
			newBike = addBike(frameId, model, availability);
			AppContext.getInstance().showMessage("Fahrrad erfolgreich hinzugefügt", 5, "#CCFFCC");
		}
		return newBike;
	}
	public Bike saveButtonAction(Bike bike, String frameId, String model, Availability availability) {
	    
	    
	    //Mindestens eine Eingabe ist leer
        if(frameId.isBlank() || availability==null
                 || model==null){
            
            AppContext.getInstance().showMessage("Fahrrad-Daten ändern fehlgeschlagen! \n"
                    + "Leere Eingabe", 5, "#FFCCDD");
        }
        //Alles korrekt, Fahrraddaten ändern
        else {
            bike.setFrameId(frameId);
            BikeType type = null;
            List<BikeType> types = DataProvider.getInstance().getBikeTypes();
            for(BikeType t:types) {
                if(t.getModel().equals(model))
                    type = t;
            }
            bike.setType(type);
            bike.setAvailability(availability);
            AppContext.getInstance().showMessage("Fahrrad-Daten erfolgreich geändert", 5, "#CCFFCC");
        }
        return bike;
	    
	}
	
	public void backButtonAction() {
		AppContext.getInstance().changeViewNode(new LoginGUI());
	}
	
	public List<String> loadBikeTypesString(){
	    var bikeTypesString = new ArrayList<String>();
	    var bikeTypes = DataProvider.getInstance().getBikeTypes();
	    for(int i=0;i<bikeTypes.size();i++) {
	        bikeTypesString.add(bikeTypes.get(i).getTypeString());
	    }
	    
	    return bikeTypesString;
	}
	
	public List<String> loadModels(){
	    List<String> models = new ArrayList<String>();
	    List<BikeType> types = DataProvider.getInstance().getBikeTypes();
	    
	    for(BikeType t:types) {
	        String s = t.getModel();
	        if(!models.contains(s))
	            models.add(s);
	    }
	    return models;
	}
	
	public List<Bike> loadBikes() {
		return DataProvider.getInstance().getBikes();
	}

	
	
}
