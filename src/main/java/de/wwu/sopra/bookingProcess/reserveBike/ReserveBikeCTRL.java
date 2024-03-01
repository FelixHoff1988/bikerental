package de.wwu.sopra.bookingProcess.reserveBike;

import java.time.LocalDateTime;
import java.util.List;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.Reservation;
import de.wwu.sopra.entity.Availability;

/**
 * 
 */
public class ReserveBikeCTRL {
	
	/**
	 * Das aktuell betrachtete Fahrrad
	 */
	private Bike currentBike;
	
	/**
	 * Konstruktor
	 */
	public ReserveBikeCTRL() {
		
	}
	
	/**
	 * @return
	 */
	public List<Bike> availableBikes() {
		
		var reader = DataProvider.getInstance();
		
		var availableBikes = reader.getBikes(bike -> (bike.getAvailability() == Availability.AVAILABLE));
		
		return availableBikes;
	}
	
	/**
	 * 
	 */
	public void reserveBike(Bike bike) {
	    LocalDateTime now = LocalDateTime.now();
	    var user = AppContext.getInstance().getLoggedInUser();
		var reservation = new Reservation(now, user, bike);
		
		bike.setAvailability(Availability.RESERVED);
	}
}
