/**
 * @author David
 * @author Nisa
 * @author Eike Elias
 */
package de.wwu.sopra.bikemanagement;

import java.util.ArrayList;
import java.util.List;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.BikeStation;

public class BikeManagementCTRL {

    /**
     * Methode zum Laden der Bikes
     * @return Liste der gespeicherten Bikes
     */
    public List<Bike> loadBikes() {
        return DataProvider.getInstance().getBikes();
    }
    
    /**
     * Methode zum Laden der BikeStations
     * @return Liste der BikeStations
     */
    public List<String> loadStations(){
        List<BikeStation> stations = DataProvider.getInstance().getStations();
        List<String> stationStrings = new ArrayList<>();
        for(BikeStation b: stations) {
            stationStrings.add(b.getName());
        }
        return stationStrings;
    }
    
    /**
     * Methode um Fahrräder eines Zustandes zu laden
     * @param status Fahrräder mit diesem Verfügbarkeits-Status laden
     * @return Liste von Fahrrädern mit dem gegebenen Verfügbarkeits-Status
     */
    public List<Bike> loadBikes(Availability status) {
        DataProvider prov = DataProvider.getInstance();
        List<Bike> bikes = new ArrayList<>();
        bikes = prov.getBikes(bike ->{
            if(bike.getAvailability() == status)
                return true;
            return false;
        });
        return bikes;
    }
    
}
