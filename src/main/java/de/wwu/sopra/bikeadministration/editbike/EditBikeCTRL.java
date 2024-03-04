package de.wwu.sopra.bikeadministration.editbike;

import java.util.ArrayList;
import java.util.List;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.BikeType;

public class EditBikeCTRL {
	
    
	/**
	 * Methode um ein Fahhrad hinzuzufügen
	 * @param frameId Rahmennummer des Fahrrads
	 * @param model Modell des Fahrrads
	 * @param availability Verfügbarkeit des Fahrrads
	 * @return Das Objekt des erstellten Fahrrads, null falls es nicht hinzugefügt werden konnte
	 */
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
	
	/**
	 * Methode zum löschen eines Fahrrads
	 * @param bike zu löschendes Fahrrad
	 * @return true falls erfolgreich gelöscht, sonst false
	 */
	public boolean removeBike(Bike bike) {
		DataProvider prov = DataProvider.getInstance();
		return prov.removeBike(bike);
	}
	
	/**
	 * Methode für die Aktion des Button zum hinzufügen eines Fahhrads
	 * @param frameId Rahemnnummer des Fahrrads
	 * @param model Model des Fahrrads
	 * @param availability Verfügbarkeit des Fahrrads
	 * @return das erstellte Fahrrad, null falls es nicht erstellt werden konnnte
	 */
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
	/**
	 * Methode für die Aktion des Buttons zum ändern von Fahrraddaten
	 * @param bike zu änderndes Fahrrad
	 * @param frameId neue Rahmennummer des Fahrrads
	 * @param model neues Modell des Fahrrads
	 * @param availability neue Verfügbarkeit des Fahrrads
	 * @return das geänderte Fahrrad mit den Änderungen
	 */
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
	
	/**
	 * Methode zum Laden der Bikes
	 * @return Liste der gespeicherten Bikes
	 */
	public List<Bike> loadBikes() {
		return DataProvider.getInstance().getBikes();
	}

	
	
}
