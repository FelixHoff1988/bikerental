package de.wwu.sopra.entity;

import java.time.LocalDateTime;

public abstract class Service {
	/**
	 * Startzeitpunkt eines Service
	 */
	private LocalDateTime startTime;
	/**
	 * Endzeitpunkt eines Service (zu Anfang null, bis der Service endet)
	 */
	private LocalDateTime endTime;
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
	 * @param startTime Startzeitpunkt des Services
	 * @param comment   Kommentar (z.B. des Nutzers der den Mangel meldet)
	 * @param bike      Das zugehörige Fahrrad
	 */
	public Service(LocalDateTime startTime, Bike bike) {
		this.startTime = startTime;
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
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Setze den Startzeitpunkt des Services
	 *
	 * @param startTime Neuer Startzeitpunkt des Services
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Ruft das Enddatum eines Service ab
	 * 
	 * @return das Enddatum des Service
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * Setzt das Enddatum eines Service
	 * 
	 * @param endTime Enddatum
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
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
