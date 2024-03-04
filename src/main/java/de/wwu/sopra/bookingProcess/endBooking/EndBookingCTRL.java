package de.wwu.sopra.bookingProcess.endBooking;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Reservation;

import java.time.LocalDateTime;

/**
 * Kontrollklasse um eine Reservierung zu beenden
 */
public class EndBookingCTRL {

    /**
     * Standartkonstruktor
     */
    public EndBookingCTRL() {}

    /**
     * Beendet eine Reservierung.
     *
     * @param reservation Zu beendende Reservierung
     */
    public void endBooking(Reservation reservation) {
        reservation.setEndTime(LocalDateTime.now());

        var bike = reservation.getBike();
        if (bike != null)
            bike.setAvailability(Availability.AVAILABLE);

        AppContext.getInstance().showMessage("Vielen Dank für ihre Fahrt mit BikeRental.de!", 5, "#CCFFCC");
    }
}
