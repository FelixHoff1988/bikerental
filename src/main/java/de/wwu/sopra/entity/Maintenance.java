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
	 * @param endDate   Enddatum
	 * @param comment   Kommentar
	 * @param bike	    Betroffenes Rad
	 * @param user		Wartungsarbeiter
	 */
	public Maintenance(LocalDateTime startDate, LocalDateTime endDate, String comment, Bike bike, User user) {
		super(startDate, endDate, comment, bike, user);
	}

}
