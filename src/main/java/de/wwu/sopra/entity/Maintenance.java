package de.wwu.sopra.entity;

import java.time.LocalDateTime;

/**
 * Klasse Maintenance für Wartung eines Fahrrads
 */

public class Maintenance extends Service {

	/**
	 * Konstruktor für Maintenance, was von Service erbt
	 * 
	 * @param startDate Startdatum
	 * @param bike	    Betroffenes Rad
	 */
	public Maintenance(LocalDateTime startDate, Bike bike) {
		super(startDate, bike);
	}

}
