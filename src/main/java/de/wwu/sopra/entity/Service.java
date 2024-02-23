package de.wwu.sopra.entity;

import java.time.LocalDateTime;

public class Service {

	/**
	 * startDate Startdatum eines Service endDate Enddatum eines Service comment
	 * Kommentar als String bike Das betroffene Rad user Der zugeordnete
	 * Wartungsarbeiter
	 */

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String comment;
	private Bike bike;
	private User user;

	/**
	 * Konstruktor für Service
	 * 
	 * @param startDate gibt das Startdatum eines Service
	 * @param endDate   gibt das Enddatum eines Service
	 * @param comment   Kommentar
	 * @param user      Der Wartungsarbeiter
	 * @param bike      Das Fahrrad
	 */
	public Service(LocalDateTime startDate, LocalDateTime endDate, String comment, Bike bike, User user) {

		this.startDate = startDate;
		this.endDate = endDate;
		this.comment = comment;
		this.bike = bike;
		this.user = user;

		/**
		 * Ruft das Startdatum eines Service auf
		 * 
		 * @return das Startdatum eines Service
		 */
	}

	/**
	 * Rufe das betroffene Rad ab
	 * 
	 * @return bike
	 */
	public Bike getBike() {
		return bike;
	}

	/**
	 * Setze das betroffene Rad
	 * 
	 * @param bike
	 */
	public void setBike(Bike bike) {
		this.bike = bike;
	}

	/**
	 * Rufe den Wartungsarbeiter ab
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setze den Wartungsarbeiter
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getStartDate() {
		return startDate;

		/**
		 * Setzt das Startdatum eines Service
		 * 
		 * @param startDate Startdatum eines Service
		 */
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * Ruft das Enddatum eines Service auf
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
	 * Ruft den Kommentar eines Service auf
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
