package de.wwu.sopra;

import com.sothawo.mapjfx.Coordinate;
import de.wwu.sopra.entity.Availability;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.Random;

/**
 * Simuliert die Bewegung von Fahrrädern auf der Karte.
 */
public class SimulationService extends ScheduledService<Void> {
    /**
     * Konstruktor
     */
    public SimulationService() { }

    /**
     * Erstellt die Task, welche im Hintergrund laufen soll.
     *
     * @return Task, welche die Fahrräder bewegt
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                var random = new Random();
                DataProvider
                        .getInstance()
                        .getBikes()
                        .forEach(bike -> {
                            if (bike.getAvailability() != Availability.BOOKED)
                                return;

                            var basePosition = bike.getLocation();

                            var metersToMove = random.nextInt(50);
                            var metersLat = random.nextInt(50);
                            var latNegative = random.nextInt(2) == 1;
                            var lngNegative = random.nextInt(2) == 1;

                            var lng = basePosition.getLongitude()
                                    + ((180 / Math.PI) * ((metersToMove-metersLat) / 6378137D) * (lngNegative ? -1 : 1)) / Math.cos(basePosition.getLatitude());
                            var lat = basePosition.getLatitude()
                                    + (180 / Math.PI) * ((metersToMove-metersLat) / 6378137D) * (latNegative ? -1 : 1);

                            bike.setLocation(new Coordinate(lat, lng));
                        });
                return null;
            }
        };
    }
}
