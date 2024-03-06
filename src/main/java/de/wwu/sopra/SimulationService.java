package de.wwu.sopra;

import com.sothawo.mapjfx.Coordinate;
import de.wwu.sopra.entity.Availability;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Random;

/**
 * Simuliert die Bewegung von Fahrrädern auf der Karte.
 */
public class SimulationService extends Service<Void> {
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
        this.setOnSucceeded(event -> this.restart());
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(10000);

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

                            double  earth = 6378.137,
                                    meter = (1 / ((2 * Math.PI / 360) * earth)) / 1000;

                            var lng = basePosition.getLongitude() + (metersToMove-metersLat * meter)
                                    / Math.cos(basePosition.getLatitude() * (Math.PI / 180));
                            var lat = basePosition.getLatitude() + (metersLat * meter);

                            bike.setLocation(new Coordinate(lat, lng));
                        });
                return null;
            }
        };
    }
}
