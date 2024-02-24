package de.wwu.sopra.entity;

import java.time.LocalDateTime;

public abstract class Service {
	/**
	 * Startzeitpunkt eines Service
	 */
	private LocalDateTime startDate;
	/**
	 * Endzeitpunkt eines Service (zu Anfang null, bis der Service endet)
	 */
	private LocalDateTime endDate;
	/**
	 * Kommentar (z.B. des Nutzers, der den Mangel meldet)
	 */
	private String comment;
	/**
	 * Zugeordnetes Fahrrad
	 */
	private Bike bike;
	/**
	 * Zugeordneter Maintainer (zu Anfang null, bis jemand den Service bearbeitet)
	 */
	private User user;

	/**
	 * Konstruktor für Service
	 * 
	 * @param startDate Startzeitpunkt des Services
	 * @param comment   Kommentar (z.B. des Nutzers der den Mangel meldet)
	 * @param bike      Das zugehörige Fahrrad
	 */
	public Service(LocalDateTime startDate, String comment, Bike bike) {
		this.startDate = startDate;
		this.comment = comment;
		this.bike = bike;
	}

	/**
	 * Rufe das betroffene Rad ab
	 * 
	 * @return Zugehöriges Fahrrad
	 */
	public Bike getBike() {
		return bike;
	}

	/**
	 * Setze das betroffene Rad
	 * 
	 * @param bike Neues zugehöriges Fahrrad
	 */
	public void setBike(Bike bike) {
		this.bike = bike;
	}

	/**
	 * Rufe den Wartungsarbeiter ab
	 * 
	 * @return Zugehöriger Wartungsarbeiter (kann null sein)
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setze den Wartungsarbeiter
	 * 
	 * @param user Neuer Wartungsarbeiter
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Rufe den Startzeitpunkt des Services ab
	 *
	 * @return Startzeitpunkt des Services
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
	 * Setze den Startzeitpunkt des Services
	 *
	 * @param startDate Neuer Startzeitpunkt des Services
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * Ruft das Enddatum eines Service ab
	 * 
	 * @return das Enddatum des Service
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * Setzt das Enddatum eines Service
	 * 
	 * @param endDate Enddatum
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	/**
	 * Ruft den Kommentar eines Service ab
	 * 
	 * @return der Kommentar eines Service
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setzt den Kommentar eines Service
	 * 
	 * @param comment Kommentar
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
