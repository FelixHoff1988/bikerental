package de.wwu.sopra.map;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Collection;

/**
 * Service, welche im Hintergrund die Position der MapMarker aktuell hält.
 */
class MapService extends Service<Void> {
    /**
     * Frequenz, in welcher die Positionen überprüft werden sollen (in Millisekunden)
     */
    private final int frequency;

    /**
     * Zu prüfenden MapMarker
     */
    private final Collection<MapMarker<?>> markers;

    /**
     * Konstruktor: Setzt die gewünschte Frequenz.
     *
     * @param frequency Frequenz, in welcher die Positionen überprüft werden sollen (in Millisekunden)
     */
    MapService(int frequency, Collection<MapMarker<?>> markers) {
        this.frequency = frequency;
        this.markers = markers;
    }

    /**
     * Erstellt die Task, welche im Hintergrund laufen soll.
     *
     * @return Task, welche die Positionen überprüft
     */
    @Override
    protected Task<Void> createTask() {
        this.setOnSucceeded(event -> this.restart());
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                markers.forEach(marker -> {
                    var location = marker.Object.getLocation();
                    marker.move(location, true);
                });
                Thread.sleep(frequency);
                return null;
            }
        };
    }
}
