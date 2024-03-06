package de.wwu.sopra.map;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.Collection;

/**
 * Service, welche im Hintergrund die Position der MapMarker aktuell hält.
 */
class MapService extends ScheduledService<Void> {
    /**
     * Zu prüfenden MapMarker
     */
    private final Collection<MapMarker<?>> markers;

    /**
     * Konstruktor: Setzt die zu prüfenden Marker.
     *
     * @param markers Die Marker
     */
    MapService(Collection<MapMarker<?>> markers) {
        this.markers = markers;
    }

    /**
     * Erstellt die Task, welche im Hintergrund laufen soll.
     *
     * @return Task, welche die Positionen überprüft
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                markers.forEach(marker -> {
                    var location = marker.Object.getLocation();
                    marker.move(location, true);
                });
                return null;
            }
        };
    }
}
