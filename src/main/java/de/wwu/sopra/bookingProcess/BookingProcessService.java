package de.wwu.sopra.bookingProcess;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Availability;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Service, welche im Hintergrund jede Minute auf überzogene Reservierungen prüft und diese abbricht.
 */
public class BookingProcessService extends Service<Void> {
    /**
     * Frequenz, in welcher die Reservierungen überprüft werden sollen (in Sekunden)
     */
    private int frequency;

    /**
     * Konstruktor: Setzt die gewünschte Frequenz.
     *
     * @param frequency Frequenz, in welcher die Reservierungen überprüft werden sollen (in Sekunden)
     */
    public BookingProcessService(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Erstellt die Task, welche im Hintergrund laufen soll.
     *
     * @return Task, welche die Reservierungen überprüft
     */
    @Override
    protected Task<Void> createTask() {
        this.setOnSucceeded(event -> this.restart());
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                DataProvider
                        .getInstance()
                        .getReservations(
                                res -> res.getEndTime() == null
                                    && res.getBookingTime() == null
                                    && res.getStartTime().until(LocalDateTime.now(), ChronoUnit.SECONDS) >= 3600)
                        .forEach(res -> {
                            res.setEndTime(LocalDateTime.now());
                            var bike = res.getBike();
                            if (bike != null && bike.getAvailability() == Availability.RESERVED)
                                bike.setAvailability(Availability.AVAILABLE);
                        });
                Thread.sleep(frequency);
                return null;
            }
        };
    }
}
