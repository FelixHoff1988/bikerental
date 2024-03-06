package de.wwu.sopra.map;

import com.sothawo.mapjfx.Coordinate;

/**
 * Klassen, welche sich als Marker auf der Map darstellen lassen implementieren dieses Interface
 */
public interface MapMarkerCandidate {
    /**
     * Gibt die Position des Objekts zurück.
     *
     * @return Position des Objektes
     */
    public Coordinate getLocation();
}
