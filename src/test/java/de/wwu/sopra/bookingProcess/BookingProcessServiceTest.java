package de.wwu.sopra.bookingProcess;

import de.wwu.sopra.DataProviderMock;
import de.wwu.sopra.entity.Availability;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static de.wwu.sopra.DataProviderMock.mockReservation;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testet das automatische Abbrechen von Reservierungen.
 */
class BookingProcessServiceTest {
    /**
     * Testet das automatische Abbrechen von Reservierungen.
     * @exception InterruptedException Falls der Thread unterbrochen wird
     */
    @Test
    void testReservationCancellation() throws Exception {
        // JavaFX Tests laufen auf Gitlab nicht
        if (System.getenv("GITLAB_CI") != null)
            return;

        Platform.startup(() -> {});

        var provider = DataProviderMock.resetProvider(true);

        mockReservation.setStartTime(LocalDateTime.now().minusSeconds(56).minusMinutes(59));
        mockReservation.getBike().setAvailability(Availability.RESERVED);

        provider.addUser(mockReservation.getUser());
        provider.addBikeType(mockReservation.getBike().getType());
        provider.addBike(mockReservation.getBike());
        provider.addReservation(mockReservation);

        new BookingProcessService(5000).start();

        // Buchung steht vier Sekunden vor dem Abbruch
        assertEquals(Availability.RESERVED, mockReservation.getBike().getAvailability());
        assertNull(mockReservation.getEndTime());
        assertNull(mockReservation.getBookingTime());

        Thread.sleep(6000);

        // Buchung wurde abgebrochen
        assertEquals(Availability.AVAILABLE, mockReservation.getBike().getAvailability());
        assertNotNull(mockReservation.getEndTime());
        assertNull(mockReservation.getBookingTime());
    }
}