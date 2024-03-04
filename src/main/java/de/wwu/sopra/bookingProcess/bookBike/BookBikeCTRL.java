package de.wwu.sopra.bookingProcess.bookBike;

import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Reservation;

import java.time.LocalDateTime;

/**
 * Kontrollklasse um reservierungen zu beenden oder zu buchen
 */
public class BookBikeCTRL {
    /**
     * Standartkonstruktor
     */
    public BookBikeCTRL() { }

    /**
     * Bricht eine Reservierung ab.
     *
     * @param reservation Abzubrechende Reservierung
     */
    public void cancelReservation(Reservation reservation) {
        reservation.setEndTime(LocalDateTime.now());

        var bike = reservation.getBike();
        if (bike != null)
            bike.setAvailability(Availability.AVAILABLE);
    }

    /**
     * Setzt die Buchungszeit einer Reservierung.
     *
     * @param reservation Zu buchende Reservierung
     */
    public void bookBike(Reservation reservation) {
        reservation.setBookingTime(LocalDateTime.now());

        var bike = reservation.getBike();
        if (bike != null)
            bike.setAvailability(Availability.BOOKED);
    }
}
