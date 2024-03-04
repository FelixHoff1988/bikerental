package de.wwu.sopra.bookingProcess.reserveBike;

import java.time.LocalDateTime;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.Reservation;
import de.wwu.sopra.entity.Availability;

/**
 * Klasse die das Reservieren von Fahrrädern managed
 */
public class ReserveBikeCTRL {
	/**
	 * Standartkonstruktor
	 */
	public ReserveBikeCTRL() {
		
	}
	
	/**
	 * Methode die ein Fahrrad reserviert und eine Reservierung erstellt
	 *
	 * @param bike Fahrrad welches reserviert werden soll
	 * @return Erstellte Reservierung
	 */
	public Reservation reserveBike(Bike bike) {
	    LocalDateTime now = LocalDateTime.now();
	    var user = AppContext.getInstance().getLoggedInUser();
	    
		var reservation = new Reservation(now, user, bike);
		
		var data = DataProvider.getInstance();
		data.addReservation(reservation);
		
		bike.setAvailability(Availability.RESERVED);

		return reservation;
	}
}
