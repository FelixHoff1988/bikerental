package de.wwu.sopra.entity;

import java.time.LocalDateTime;

/**
 * Klasse Repair für Reparatur eines Fahrrads
 */

public class Repair extends Service {

	/**
	 * statement Statement einer Reparatur
	 */
	private String statement;

	/**
	 * Konstruktor für Repair, was von Service erbt
	 * 
	 * @param startDate Startdatum
	 * @param comment   Möglichkeit einen Kommentar hinzufügen
	 * @param statement Statement einer 
	 * @param bike	    Betroffenes Rad
	 */
	public Repair(LocalDateTime startDate, String statement, Bike bike) {
		super(startDate, bike);
		this.statement = statement;
	}


	/**
	 * Rufe das Statement des Nutzers ab
	 *
	 * @return Statement des Nutzers zum Mangel
	 */
	public String getStatement() {
		return statement;
	}
}
