package de.wwu.sopra.entity;

import java.time.LocalDateTime;

/**
 * Klasse Maintenance für Wartung eines Fahrrads
 */

public class Maintenance extends Service{

	/**
	 * Konstruktor für Maintenance, was von Service erbt
	 * @param startDate Startdatum 
	 * @param endDate Enddatum
	 * @param comment Kommentar
	 */
	public Maintenance(LocalDateTime startDate, LocalDateTime endDate, String comment){
		super(startDate, endDate, comment);
	}
	
}
