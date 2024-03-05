package de.wwu.sopra.map;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.CoordinateLine;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.event.MapViewEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Wrapper für eine CoordinateLine mit zusätzlichen Informationen
 *
 * @param <T> Verknüpfter Datentyp
 */
class MapCoordinateLine<T extends MapCoordinateLineCandidate> {
    /**
     * Der MapView, auf welchem die CoordinateLine angezeigt wird.
     */
    private final MapView parent;
    /**
     * Die CoordinateLine, welcher auf dem MapView angezeigt wird.
     */
    private CoordinateLine coordinateLine;
    /**
     * Das mit der CoordinateLine assoziierte Objekt
     */
    final T Object;
    /**
     * Diese Aktion wird beim Klick auf die CoordinateLine ausgeführt.
     */
    private Consumer<T> onClickAction;
    /**
     * Dies ist die Standard-Linienfarbe der CoordinateLine.
     */
    private final String baseColorLine;
    /**
     * Dies ist die Standard-Füllfarbe der CoordinateLine.
     */
    private final String baseColorFill;
    /**
     * Diese Linienfarbe nimmt die CoordinateLine beim Klick auf sich an.
     */
    private String clickLineColor;
    /**
     * Diese Füllfarbe nimmt die CoordinateLine beim Klick auf sich an.
     */
    private String clickFillColor;
    /**
     * Ist die CoordinateLine ausgewählt
     */
    private boolean selected;
    /**
     * Ist die Farbe der CoordinateLine verändert
     */
    private boolean colorChanged;
    /**
     * Wird die CoordinateLine aktuell auf dem MapView angezeigt
     */
    private boolean isShown;

    /**
     * Instanziiert eine neue CoordinateLine
     *
     * @param parent       Der MapView auf welchem die CoordinateLine angezeigt werden soll
     * @param object       Das mit der CoordinateLine assoziierte Objekt
     * @param lineColor    Die Linienfarbe der CoordinateLine
     * @param fillColor    Die Füllfarbe der CoordinateLine
     */
    MapCoordinateLine(
            MapView parent,
            T object,
            String lineColor,
            String fillColor) {
        this.parent = parent;
        this.Object = object;
        this.baseColorLine = lineColor;
        this.baseColorFill = fillColor;
        this.coordinateLine = new CoordinateLine(object.getVertices())
                .setColor(Color.web(lineColor, 1))
                .setFillColor(Color.web(fillColor, 0.4))
                .setClosed(true)
                .setVisible(true);
    }

    /**
     * Instanziiert eine neue CoordinateLine
     *
     * @param parent   Der MapView auf welchem die CoordinateLine angezeigt werden soll
     * @param vertices Eckpunkte der CoordinateLine
     * @param lineColor    Die Linienfarbe der CoordinateLine
     * @param fillColor    Die Füllfarbe der CoordinateLine
     */
    MapCoordinateLine(
            MapView parent,
            List<Coordinate> vertices,
            String lineColor,
            String fillColor) {
        this.parent = parent;
        this.Object = null;
        this.baseColorLine = lineColor;
        this.baseColorFill = fillColor;
        this.coordinateLine = new CoordinateLine(vertices)
                .setColor(Color.web(lineColor, 1))
                .setFillColor(Color.web(fillColor, 0.4))
                .setClosed(true)
                .setVisible(true);
    }

    /**
     * Ruft die aktuell angezeigte CoordinateLine ab.
     * 
     * @return CoordinateLine dieser Instanz
     */
    CoordinateLine getCoordinateLine() {
        return this.coordinateLine;
    }

    /**
     * Setzt eine Aktion, welche beim Klick auf die CoordinateLine ausgeführt werden soll.
     *
     * @param action      Die Aktion
     * @param changeLineColor Eine Linienfarbe, die die CoordinateLine beim Klick annehmen soll (kann null sein)
     * @param changeFillColor Eine Füllfarbe, die die CoordinateLine beim Klick annehmen soll (kann null sein)
     */
    void setOnAction(Consumer<T> action, String changeLineColor, String changeFillColor) {
        this.onClickAction = action;
        this.clickLineColor = changeLineColor;
        this.clickFillColor = changeFillColor;
    }

    /**
     * Entfernt die in setOnAction spezifizierte Aktion.
     */
    void removeOnAction() {
        this.onClickAction = null;
        this.clickLineColor = null;
        this.clickFillColor = null;
    }

    /**
     * Führt die durch setOnAction spezifizierte Funktion aus
     * und ruft select() bzw. deselect() auf die CoordinateLine auf.
     *
     * @param event Klick-Event des MapView (kann null sein)
     */
    void click(MapViewEvent event) {
        if (this.onClickAction == null)
            return;

        if (event != null) {
            var coordinates = new ArrayList<>(coordinateLine.getCoordinateStream().toList());
            if (!MapFunctions.isCoordinateInArea(event.getCoordinate(), coordinates)) {
                deselect();
                return;
            }
        }

        if (this.selected)
            deselect();
        else
            select();

        onClickAction.accept(Object);
    }

    /**
     * Wählt die CoordinateLine aus.
     * Falls setOnAction gesetzt wurde, nimmt sie die dort spezifizierten Farben an.
     */
    void select() {
        select(null, null);
    }

    /**
     * Wählt die CoordinateLine aus.
     * Falls lineColor oder fillColor nicht null ist, nimmt sie die angegebene Farbe an.
     * Ansonsten nimmt sie, falls setOnAction gesetzt wurde, die dort spezifizierten Farben an.
     *
     * @param lineColor Anzunehmende Linienfarbe (kann null sein)
     * @param fillColor Anzunehmende Füllfarbe (kann null sein)
     */
    void select(String lineColor, String fillColor) {
        this.selected = true;
        if (lineColor != null && fillColor != null)
            changeColor(lineColor, fillColor);
        else if (this.clickLineColor != null && this.clickFillColor != null)
            changeColor(this.clickLineColor, this.clickFillColor);
    }

    /**
     * Wählt den Marker ab.
     * Falls setOnAction gesetzt wurde,
     */
    void deselect() {
        this.selected = false;
        if (this.colorChanged) {
            changeColor(this.baseColorLine, this.baseColorFill);
        }
    }

    /**
     * Zeigt den Marker auf der Map an.
     */
    void add() {
        if (!this.isShown && this.parent.getInitialized()) {
            this.parent.addCoordinateLine(this.coordinateLine);
            this.isShown = true;
        }
    }

    /**
     * Entfernt den Marker von der Map.
     */
    void remove() {
        if (this.isShown) {
            this.parent.removeCoordinateLine(this.coordinateLine);
            this.isShown = false;
        }
    }

    /**
     * Wechselt die Farbe des Markers.
     *
     * @param lineColor Anzunehmende Linienfarbe
     * @param fillColor Anzunehmende Füllfarbe
     */
    void changeColor(String lineColor, String fillColor) {
        this.colorChanged =
                !this.baseColorLine.equals(lineColor)
                && !this.baseColorFill.equals(fillColor);

        this.coordinateLine
                .setColor(Color.web(lineColor, 1))
                .setFillColor(Color.web(fillColor, 0.4));

        if (isShown) {
            this.parent.removeCoordinateLine(this.coordinateLine);
            this.parent.addCoordinateLine(this.coordinateLine);
        }
    }

    /**
     * Füge einen neuen Eckpunkt zur CoordinateLine hinzu.
     * Die Aktion funktioniert nur, wenn sich dadurch keine Seiten überschneiden.
     *
     * @param vertex Hinzuzufügender Eckpunkt
     */
    void addVertex(Coordinate vertex) {
        var coordinates = new ArrayList<>(this.coordinateLine.getCoordinateStream().toList());

        if (!MapFunctions.isValidCoordinateLine(coordinates, vertex, coordinates.size()))
            return;

        coordinates.add(vertex);

        var newLine = new CoordinateLine(coordinates)
                .setColor(Color.web(this.baseColorLine, 1))
                .setFillColor(Color.web(this.baseColorFill, 0.4))
                .setClosed(true)
                .setVisible(true);

        if (isShown) {
            this.parent.removeCoordinateLine(this.coordinateLine);
            this.parent.addCoordinateLine(newLine);
        }

        this.coordinateLine = newLine;
    }
}
