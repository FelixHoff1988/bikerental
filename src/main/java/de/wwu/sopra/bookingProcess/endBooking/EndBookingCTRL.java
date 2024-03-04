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
     * @return Wurde die Buchung erfolgreich beendet
     */
    public boolean endBooking(Reservation reservation) {
        var bike = reservation.getBike();

        if (bike != null && bike.getCurrentArea() == null) {
            AppContext.getInstance().showMessage(
                    "Bitte stelle dein Fahrrad in einem der markierten Bereiche ab!",
                    5,
                    "#FFCCDD");
            return false;
        }

        reservation.setEndTime(LocalDateTime.now());
        if (bike != null)
            bike.setAvailability(Availability.AVAILABLE);

        AppContext.getInstance().showMessage(
                "Vielen Dank für ihre Fahrt mit BikeRental.de!",
                5,
                "#CCFFCC");
        return true;
    }
}
