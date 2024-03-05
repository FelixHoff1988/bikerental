package de.wwu.sopra.map;

import com.sothawo.mapjfx.Coordinate;

import java.util.List;

/**
 * Klassen, welche sich als CoordinateLine auf der Map darstellen lassen implementieren dieses Interface
 */
public interface MapCoordinateLineCandidate {
    /**
     * Gibt die Eckpunkte des Objektes zurück.
     *
     * @return Eckpunkte des Objektes
     */
    public List<Coordinate> getVertices();
}
