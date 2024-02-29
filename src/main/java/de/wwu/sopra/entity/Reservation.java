package de.wwu.sopra.entity;

import java.time.LocalDateTime;

/**
 * Klasse für die Entität der Reservierungen
 */
public class Reservation {
	/**
	 * Startzeit
	 */
	private LocalDateTime startTime;
	/**
	 * Buchungszeit (zu Anfang null bis gebucht wird)
	 */
	private LocalDateTime bookingTime;
	/**
	 * Endzeit (zu Anfang null bis die Reservation endet)
	 */
	private LocalDateTime endTime;
	/**
	 * Preis der Reservierung
	 */
	private int price;
	/**
	 * Benutzer der Reservierung
	 */
	private User user;
	/**
	 * Reserviertes Rad
	 */
	private Bike bike;

	/**
	 * Konstruktor für alle Parameter der Reservation
	 * 
	 * @param startTime   Startzeit der Reservierung
	 * @param bike        Reserviertes Rad
	 * @param user        Kunde der reserviert
	 */
	public Reservation(LocalDateTime startTime, User user, Bike bike) {
		this.startTime = startTime;
		this.price = bike.getType().getPrice();
		this.bike = bike;
		this.user = user;
	}

	/**
	 * Rufe den Kunden ab
	 * 
	 * @return Mit der Reservierung verbundener Kunde
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setze den Kunden
	 * 
	 * @param user Neuer Kunde der mit der Reservierung verbunden ist
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Rufe das reservierte Rad ab
	 * 
	 * @return Rad, welches zu der Reservierung gehört
	 */
	public Bike getBike() {
		return bike;
	}

	/**
	 * Setze Fahrrad der Reservierung
	 * 
	 * @param bike Neues mit der Reservierung zu verbindende Rad
	 */
	public void setBike(Bike bike) {
		this.bike = bike;
	}

	/**
	 * Rufe die Startzeit der Reservierung ab
	 *
	 * @return Startzeit der Reservierung
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Setze die Startzeit der Reservierung
	 * 
	 * @param startTime Neue Startzeit der Reservierung
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Rufe die Buchungszeit der Reservierung ab
	 *
	 * @return Buchungszeit der Reservierung
	 */
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	/**
	 * Setze die Buchungszeit
	 * 
	 * @param bookingTime Neue Buchungszeit der Reservierung
	 */
	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	/**
	 * Rufe die Endzeit der Reservierung ab
	 *
	 * @return Endzeit der Reservierung
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * Setze die Endzeit der Reservierung
	 * 
	 * @param endTime Neue Endzeit der Reservierung
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * Rufe den Preis der Reservierung ab
	 *
	 * @return Preis der Reservierung
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Setze den Preis der Reservierung
	 * 
	 * @param price Neuer Preis der Reservierung
	 */
	public void setPrice(int price) {
		this.price = price;
	}

}
