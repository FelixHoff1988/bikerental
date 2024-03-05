/*
 * @author Nisa
 * @author David
 */
package de.wwu.sopra.usagehistory;

import java.util.List;
import de.wwu.sopra.AppContext;
import de.wwu.sopra.entity.Reservation;

/*
 * Kontrollklasse für Nutzungshistorie
 */
public class UsageHistoryCTRL {
    
    
    //Konstruktor
    public UsageHistoryCTRL() {
    }
    
    
    /** Lädt die Liste von Reservierungen
     * @return gibt die Liste von Reservierungen für den eingeloggten User zurück.
     */
    public List<Reservation> loadReservations() {
        return AppContext.getInstance().getLoggedInUser().getReservationList();
    }
    

}
