package de.wwu.sopra.entity;

import java.time.LocalDateTime;

/**
 * Klasse Repair für Reparatur eines Fahrrads
 */

public class Repair extends Service  {

	/**
	 * statement Statement einer Reparatur
	 */
	private String statement;

	/**
	 * Konstruktor für Repair, was von Service erbt
	 * @param startDate Startdatum
	 * @param endDate Enddatum
	 * @param commment Kommentar
	 * @param statement Statement einer Reparatur
	 */
	public Repair(LocalDateTime startDate, LocalDateTime endDate, String comment, String statement) {
		super(startDate, endDate, comment);
		this.statement=statement;
	}
	
}
