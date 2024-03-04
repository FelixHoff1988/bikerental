/**
 * @author David
 * @author Nisa
 */
package de.wwu.sopra.bikemaintenance;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.Availability;

/**
 * Kontrollklassse zur BikeMaintenanceGUI
 */
public class BikeMaintenanceCTRL {

    
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
