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
	 * Buchungszeit
	 */
	private LocalDateTime bookingTime;
	/**
	 * Endzeit
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
	 * @param startTime   Startzeit
	 * @param bookingTime BuchungsZeit
	 * @param endTime     Endzeit
	 * @param price       Preis der Reservierung
	 * @param bike        reserviertes Rad
	 * @param user        Kunde
	 */
	public Reservation(LocalDateTime startTime, LocalDateTime bookingTime, LocalDateTime endTime, int price, User user,
			Bike bike) {
		this.startTime = startTime;
		this.bookingTime = bookingTime;
		this.endTime = endTime;
		this.price = price;
		this.bike = bike;
		this.user = user;
	}

	/**
	 * Rufe den Kunden ab
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setze den Kunden
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Rufe das reservierte Rad ab
	 * 
	 * @return
	 */
	public Bike getBike() {
		return bike;
	}

	/**
	 * Setze Fahrrad der Reservierung
	 * 
	 * @param bike
	 */
	public void setBike(Bike bike) {
		this.bike = bike;
	}

	/**
	 * @return Startzeit
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Setze die Startzeit
	 * 
	 * @param startTime neue Startzeit
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return Buchungszeit
	 */
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	/**
	 * Setze die Buchungszeit
	 * 
	 * @param bookingTime neue Buchungszeit
	 */
	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	/**
	 * @return Endzeit
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * Setze die Endzeit der Reservierung
	 * 
	 * @param endTime neue Endzeit
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return Preis der Reservierung
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Setze den Preis der Reservierung
	 * 
	 * @param price neuer Preis
	 */
	public void setPrice(int price) {
		this.price = price;
	}

}
