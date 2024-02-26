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
	 * @param comment   Kommentar
	 * @param bike	    Betroffenes Rad
	 */
	public Maintenance(LocalDateTime startDate, String comment, Bike bike) {
		super(startDate, comment, bike);
	}

}
