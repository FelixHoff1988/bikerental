package de.wwu.sopra.bookingProcess;

import de.wwu.sopra.DataProvider;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BookingProcessService extends Service<Void> {
    @Override
    protected Task<Void> createTask() {
        var service = this;
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                DataProvider
                        .getInstance()
                        .getReservations()
                        .stream()
                        .filter(res -> res.getEndTime() == null
                                && res.getBookingTime() == null
                                && res.getStartTime().until(LocalDateTime.now(), ChronoUnit.MILLIS) >= 60000)
                        .forEach(res -> res.setEndTime(LocalDateTime.now()));

                wait(60000);

                service.reset();
                service.start();
                return null;
            }
        };
    }
}
