package de.wwu.sopra.map;

import com.sothawo.mapjfx.Coordinate;
import de.wwu.sopra.entity.GeofencingArea;

import java.awt.geom.Line2D;
import java.util.List;

/**
 * Funktionssammlung von Koordinaten-Berechnungen
 */
public abstract class MapFunctions {
    /**
     * Standardkonstruktor
     */
    public MapFunctions() {}

    /**
     * Testet, ob eine Koordinate sich in einer GeofencingArea befindet.
     *
     * @param coordinate Zu testende Koordinate
     * @param area Zu testende GeofencingArea
     * @return true: Koordinate in der GeofencingArea, false: sonst
     */
    public static boolean isCoordinateInArea(Coordinate coordinate, GeofencingArea area) {
        var numVertices = area.getEdges().size();
        var yCoordinate = coordinate.getLatitude();
        var xCoordinate = coordinate.getLongitude();

        boolean isInArea = false;

        for (int i = 0, j = numVertices - 1; i < numVertices; j = i++) {
            var yi = area.getEdges().get(i).getLatitude();
            var yj = area.getEdges().get(j).getLatitude();
            var xi = area.getEdges().get(i).getLongitude();
            var xj = area.getEdges().get(j).getLongitude();

            if (((yi > yCoordinate) != (yj > yCoordinate))
                    && (xCoordinate < (xj - xi) * (yCoordinate - yi) / (yj - yi) + xi))
                isInArea = !isInArea;
        }
        return isInArea;
    }

    /**
     * Versucht eine neue Koordinate in einen bestehenden Bereich an der bestmöglichen Seite einzufügen.
     * Sollte der Bereich dadurch überschneidende Seiten bilden, so wird die Koordinate nicht eingefügt.
     *
     * @param area Bestehender Bereich
     * @param coordinate Neue Koordinate
     * @return true: Koordinate wurde eingefügt, false: sonst
     */
    public static boolean insertAtNearestSide(List<Coordinate> area, Coordinate coordinate) {
        if (area.size() < 2)
            return area.add(coordinate);

        var insertPosition  = 0;
        var closestDistance = Double.MAX_VALUE;

        for (var i = 0; i < area.size(); i++) {
            var iPlusPos = getSaveListPosition(area.size(), i, 1);
            var distance = Line2D.ptSegDist(
                    area.get(i).getLongitude(),
                    area.get(i).getLatitude(),
                    area.get(iPlusPos).getLongitude(),
                    area.get(iPlusPos).getLatitude(),
                    coordinate.getLongitude(),
                    coordinate.getLatitude());

            if (distance < closestDistance) {
                closestDistance = distance;
                insertPosition = i + 1;
            }
        }

        if (!isValidCoordinateLine(area, coordinate, insertPosition))
            return false;

        area.add(insertPosition, coordinate);
        return true;
    }

    /**
     * Testet, ob eine Koordinate zusammen mit einem abgesteckten Bereich
     * immer noch ein Polygon ohne überschneidende Seiten bildet.
     *
     * @param area Zu testender Bereich
     * @param coordinate Zu testende Koordinate
     * @param insertAt Stelle in der Liste, an welcher die Koordinate eingefügt werden soll
     * @return true: Valider bereich, false: sonst
     */
    public static boolean isValidCoordinateLine(List<Coordinate> area, Coordinate coordinate, int insertAt) {
        if (area.size() < 2)
            return true;

        var insertAfter  = getSaveListPosition(area.size(), insertAt, -1);
        var insertBefore = getSaveListPosition(area.size(), insertAt, 0);

        for (var i = 1; i < area.size() - 2; i++) {
            var iPos = getSaveListPosition(area.size(), insertAt, i);
            var iPlusPos = getSaveListPosition(area.size(), insertAt, i + 1);
            if (areIntersecting(coordinate, area.get(insertAfter), area.get(iPos), area.get(iPlusPos))
                || areIntersecting(coordinate, area.get(insertBefore), area.get(iPos), area.get(iPlusPos)))
                return false;
        }

        if (area.size() > 2) {
            if (areIntersecting(
                    coordinate,
                    area.get(insertBefore),
                    area.get(insertAfter),
                    area.get(getSaveListPosition(area.size(), insertAfter, -1))))
                return false;
            if (areIntersecting(
                    coordinate,
                    area.get(insertAfter),
                    area.get(insertBefore),
                    area.get(getSaveListPosition(area.size(), insertBefore, 1))))
                return false;
        }

        return true;
    }

    /**
     * Berechnet eine Position in einer Liste.
     * Beim Über-/Unterlauf wird respektive wieder vorne/hinten angefangen zu zählen.
     *
     * @param size Größe der Liste
     * @param position Aktuelle Position in der Liste
     * @param step Schrittweite (kann negativ sein)
     * @return Neue Position in der Liste
     */
    private static int getSaveListPosition(int size, int position, int step) {
        if (position >= size)
            position = 0;
        if (position < 0)
            position = size;

        step %= size;

        if (position + step >= size)
            return step - (size - position);
        if (position + step < 0)
            return size + (position + step);

        return position + step;
    }

    /**
     * Testet, ob die Strecken "ab" und "cd" sich schneiden.
     *
     * @param a Startpunkt Strecke "ab"
     * @param b Endpunkt Strecke "ab"
     * @param c Startpunkt Strecke "cd"
     * @param d Endpunkt Strecke "cd"
     * @return true: Strecken schneiden sich, false: sonst
     */
    private static boolean areIntersecting(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
        var oABC = orientation(a, b, c);
        var oABD = orientation(a, b, d);
        var oCDA = orientation(c, d, a);
        var oCDB = orientation(c, d, b);

        if (oABC != oABD && oCDA != oCDB)
            return true;

        if (oABC == 0 && onSegment(a, b, c))
            return true;
        if (oABD == 0 && onSegment(a, b, d))
            return true;
        if (oCDA == 0 && onSegment(c, d, a))
            return true;
        if (oCDB == 0 && onSegment(c, d, b))
            return true;

        return false;
    }

    /**
     * Berechnet die Orientierung des Triplets (a, b, c)
     *
     * @param a Punkt 1
     * @param b Punkt 2
     * @param c Punkt 3
     * @return  0: a, b und c sind kollinear
     *          1: mit dem Uhrzeigersinn
     *          2: gegen den Uhrzeigersinn
     */
    private static int orientation(Coordinate a, Coordinate b, Coordinate c) {
        var value = (b.getLatitude() - a.getLatitude()) * (c.getLongitude() - b.getLongitude())
                  - (b.getLongitude() - a.getLongitude()) * (c.getLatitude() - b.getLatitude());

        if (value == 0)
            return 0;

        return value > 0 ? 1 : 2;
    }

    /**
     * Die Funktion überprüft, ob der Punkt c auf der Strecke "ab" liegt.
     *
     * @param a Anfang der Strecke
     * @param b Ende der Strecke
     * @param c Zu prüfender Punkt
     * @return true: Punkt liegt auf der Strecke, false: sonst
     */
    private static boolean onSegment(Coordinate a, Coordinate b, Coordinate c) {
        return c.getLongitude() <= Math.max(a.getLongitude(), b.getLongitude())
                && c.getLongitude() >= Math.min(a.getLongitude(), b.getLongitude())
                && c.getLatitude() <= Math.max(a.getLatitude(), b.getLatitude())
                && c.getLatitude() >= Math.min(a.getLatitude(), b.getLatitude());
    }
}
