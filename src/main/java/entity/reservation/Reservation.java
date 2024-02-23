package entity.reservation;

import java.time.LocalDateTime;

public class Reservation {
	
	//Startzeit erstellen
	private LocalDateTime startTime;
	//Buchungszeit erstellen
	private LocalDateTime bookingTime;
	//Endzeit erstellen
	private LocalDateTime endTime;
	//Preis der Reservierung erstellen
	private int price;
	
	/*
	 * Konstruktor für alle Parameter der Reservation
	 * @param startTime Startzeit
	 * @param bookingTime BuchungsZeit
	 * @param endTime Endzeit
	 * @param price Preis der Reservierung
	 */
	public Reservation(LocalDateTime startTime, LocalDateTime bookingTime, LocalDateTime endTime, int price) {
		super();
		this.startTime = startTime;
		this.bookingTime = bookingTime;
		this.endTime = endTime;
		this.price = price;
	}
	
	//Startzeit zurückgeben
	public LocalDateTime getStartTime() {
		return startTime;
	}
	//Startzeit setzten
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	//BuchungsZeit zurückgeben
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}
	//BuchungsZeit setzten
	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}
	//Endzeit zurückgeben
	public LocalDateTime getEndTime() {
		return endTime;
	}
	//Endzeit setzten
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	//Preis der Reservierung zurückgeben
	public int getPrice() {
		return price;
	}
	//Preis der Reservierung setzten
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
	
}
