package de.wwu.sopra.entity.service;

import java.time.LocalDateTime;

public class Service {

	/**
	 * startDate Startdatum eines Service
	 * endDate Enddatum eines Service
	 * comment Kommentar als String
	 */
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String comment;


	/**
	 * Konstruktor für Service
	 * @param startDate gibt das Startdatum eines Service
	 * @param endDate gibt das Enddatum eines Service
	 * @param comment Kommentar
	 */
	public Service(LocalDateTime startDate, LocalDateTime endDate, String comment) {
		
		this.startDate=startDate;
		this.endDate=endDate;
		this.comment=comment;
		
	/**
	 * Ruft das Startdatum eines Service auf
	 * @return das Startdatum eines Service
	 */
	}
	public LocalDateTime getStartDate() {
		return startDate;
		
	/**
	 * Setzt das Startdatum eines Service
	 * @param startDate Startdatum eines Service
	 */
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Ruft das Enddatum eines Service auf
	 * @return das Enddatum des Service
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}
	
	/**
	 * Setzt das Enddatum eines Service
	 * @param endDate Enddatum
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Ruft den Kommentar eines Service auf
	 * @return der Kommentar eines Service
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Setzt den Kommentar eines Service
	 * @param comment Kommentar
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}


