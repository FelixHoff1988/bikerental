package de.wwu.sopra.map;

import com.sothawo.mapjfx.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für Kartenberechnungen
 */
class MapFunctionsTests {
    /**
     * Teste isCoordinateInArea
     */
    @Test
    void isCoordinateInArea() {
        // Koordinaten rund um Münster Hbf
        var coordinates = new Coordinate[] {
                new Coordinate(51.959063,7.630286),
                new Coordinate(51.958405,7.637877),
                new Coordinate(51.956478,7.644844),
                new Coordinate(51.955753,7.636982),
                new Coordinate(51.958003,7.629771),
        };
        // Münster Hbf
        var muenster = new Coordinate(51.956681, 7.635691);
        var coesfeld = new Coordinate(51.945894, 7.169111);

        assertTrue(MapFunctions.isCoordinateInArea(muenster, List.of(coordinates)));
        assertFalse(MapFunctions.isCoordinateInArea(coesfeld, List.of(coordinates)));
    }

    /**
     * Teste insertAtNearestSide
     */
    @Test
    void insertAtNearestSide() {
        // Koordinaten rund um Münster Hbf
        var coordinates = new Coordinate[] {
                new Coordinate(51.959063,7.630286),
                new Coordinate(51.958405,7.637877),
                new Coordinate(51.956478,7.644844),
                new Coordinate(51.955753,7.636982),
                new Coordinate(51.958003,7.629771),
        };
        // Münster Hbf
        var muenster = new Coordinate(51.956681, 7.635691);
        var coesfeld = new Coordinate(51.945894, 7.169111);

        assertTrue(MapFunctions.insertAtNearestSide(new ArrayList<>(List.of(coordinates)), muenster));
        assertTrue(MapFunctions.insertAtNearestSide(new ArrayList<>(List.of(coordinates)), coesfeld));
    }

    /**
     * Teste isValidCoordinateLine
     */
    @Test
    void isValidCoordinateLine() {
        // Koordinaten rund um Münster Hbf
        var coordinates = new Coordinate[] {
                new Coordinate(51.959063,7.630286),
                new Coordinate(51.958405,7.637877),
                new Coordinate(51.956478,7.644844),
                new Coordinate(51.955753,7.636982),
                new Coordinate(51.958003,7.629771),
        };
        // Münster Hbf
        var muenster = new Coordinate(51.95694546806814, 7.649767232910157);
        var coesfeld = new Coordinate(51.945894, 7.169111);

        assertTrue(MapFunctions.isValidCoordinateLine(List.of(coordinates), coesfeld, coordinates.length));
        assertFalse(MapFunctions.isValidCoordinateLine(List.of(coordinates), muenster, coordinates.length));
    }
}