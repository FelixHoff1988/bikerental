package de.wwu.sopra.bookingProcess;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.entity.Reservation;

/**
 * Kontrollklasse für Funktionen des Buchungsprozesses
 */
public class BookingProcessCTRL {

    /**
     * Standartkonstruktor
     */
    public BookingProcessCTRL() {
        
    }
    
    /**
     * Methode prüft, in welchem Reservierungsschritt der aktuelle Nutzer ist
     * @return 1: Der Nutzer hat keine offene Reservierung, 
     *         2: Der Nutzer hat eine Reservierung, 
     *         3: Der Nutzer hat eine aktuelle Fahrt
     */
    public int currentGUI() {
        var latestReservation = getReservation();
        
        if (latestReservation == null) {
            return 1;
        }
        if (latestReservation.getEndTime() != null) {
            return 1;
        } 
        if (latestReservation.getBookingTime() == null) {
            return 2;
        } 
        return 3;
    }
    
    /**
     * Gebe die letzte Reservierung zurück
     * @return letzte Reservierung
     *         null, wenn es keine letzte Reservierung gibt
     */
    public Reservation getReservation() {
        var context = AppContext.getInstance();
        var currentUser = context.getLoggedInUser();
        
        var reservations = currentUser.getReservationList();
        if (reservations.isEmpty()) {
            return null;
        }
        
        return reservations.getLast();
    }
    
}
