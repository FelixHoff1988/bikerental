package de.wwu.sopra.bookingProcess.reserveBike;

import java.util.List;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Bike;
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
	 * @return
	 */
	public void selectBike(Bike bike) {
		
	}
	
//	/**
//	 * 
//	 */
//	public void reserveBike() {
//		
//	}
}
