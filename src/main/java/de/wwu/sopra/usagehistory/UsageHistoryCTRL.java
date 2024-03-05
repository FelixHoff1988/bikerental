/*
 * @author Nisa
 * @author David
 */
package de.wwu.sopra.usagehistory;

import java.util.ArrayList;
import java.util.List;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.Reservation;
import de.wwu.sopra.entity.User;

/*
 * Kontrollklasse für Nutzungshistorie
 */
public class UsageHistoryCTRL {
    
    
    //Konstruktor
    public UsageHistoryCTRL() {
    }
    
    
    public List<Reservation> loadReservations() {
        return AppContext.getInstance().getLoggedInUser().getReservationList();
    }
    

}
