package de.wwu.sopra.map;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import de.wwu.sopra.Design;
import de.wwu.sopra.entity.GeofencingArea;
import javafx.scene.layout.BorderPane;

import java.util.*;
import java.util.function.Consumer;

/**
 * Allgemeine Implementierung der Map zur Einbindung in die weiteren GUIs
 */
public class MapGUI extends BorderPane {
    /**
     * Der MapView, welche die Karte enthält
     */
    private final MapView mapView = new MapView();

    /**
     * Aktuell zu erstellenden CoordinateLine
     */
    private MapCoordinateLine<?> drawArea;

    /**
     * Bewegbarer Marker für das Platzieren neuer Marker
     */
    private MapMarker<?> dynamicMarker;

    /**
     * Alle auf der Map angezeigten Marker
     */
    private final HashSet<MapMarker<?>> markers = new HashSet<>();

    /**
     * Liste aller angezeigten CoordinateLines
     */
    private final HashSet<MapCoordinateLine<?>> coordinateLines = new HashSet<>();

    /**
     * Standardkonstruktor: Initialisiert den MapView
     */
    public MapGUI() {
        init();
    }

    /**
     * Initiale Einrichtung des MapViews
     */
    public void init() {
        // Münster Hbf
        var muenster = new Coordinate(51.956681, 7.635691);

        //Set the initial properties of the map.
        mapView.setCenter(muenster);
        mapView.setZoom(14);
        mapView.setXYZParam(
                new XYZParam()
                        .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
                        .withAttributions(
                                "'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'"));

        mapView.setMapType(MapType.XYZ);

        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                drawMarkers(this.markers);
                drawCoordinateLines(this.coordinateLines);
                if (dynamicMarker != null)
                    dynamicMarker.add();
            }
        });
        mapView.addEventHandler(
                MarkerEvent.MARKER_CLICKED,
                event -> {
                    var matches = this.markers.stream().filter(m -> m.getMarker() == event.getMarker()).toList();
                    if (matches.isEmpty())
                        return;
                    var matchClass = matches.getFirst().Object.getClass();
                    this.markers.stream()
                            .filter(m -> m.Object.getClass() == matchClass)
                            .forEach(m -> m.click(event));

                });
        mapView.addEventHandler(
                MapViewEvent.MAP_CLICKED,
                event -> {
                    if (this.dynamicMarker != null) {
                        this.dynamicMarker.move(event.getCoordinate(), true);
                    } if (this.drawArea != null) {
                        this.drawArea.addVertex(event.getCoordinate());
                    } else {
                        this.coordinateLines.forEach(line -> line.click(event));
                    }
                });

        mapView.initialize();

        this.setCenter(mapView);
        this.setMinHeight(500);
    }

    public void keepUpToDate() {

    }

    /**
     * Zeigt eine Liste von Markern auf der Map an.
     *
     * @param objects Mit den Markern zu assoziierende Objekte
     * @param color Farbe der Marker
     * @param <T> Typ der Objects
     */
    public <T extends MapMarkerCandidate> void displayMarkers(
            List<T> objects,
            Marker.Provided color) {
        objects.forEach(obj -> {
            var marker = new MapMarker<>(
                    mapView,
                    obj,
                    color);
            markers.add(marker);
        });
        if (this.mapView.getInitialized())
            drawMarkers(this.markers);
    }

    /**
     * Fügt der Map eine Liste an Markern hinzu.
     *
     * @param markers Hinzuzufügende Marker
     */
    private void drawMarkers(Collection<MapMarker<?>> markers) {
        markers.forEach(MapMarker::add);
    }

    /**
     * Definiert eine Aktion, welche ausgeführt wird, wenn ein Marker vom Typ T angeklickt wird.
     *
     * @param objClass Klasse der zu verwaltenden Objekte
     * @param consumer Funktion, welche das Objekt entgegennimmt, auf welches geklickt wurde.
     * @param changeColor Neue Farbe des Markers (kann null sein)
     * @param <T> Type des Markers
     */
    public <T extends MapMarkerCandidate> void onClickMarker(
            Class<T> objClass,
            Consumer<T> consumer,
            Marker.Provided changeColor) {
        this.markers
                .stream()
                .filter(marker -> marker.Object.getClass() == objClass)
                .forEach(marker -> ((MapMarker<T>)marker).setOnAction(consumer, changeColor));
    }

    /**
     * Entfernt die onClickMarker Aktion.
     *
     * @param objClass Klasse der zu verwaltenden Objekte
     * @param <T> Typ des mit dem Marker assoziierten Objektes
     */
    public <T> void removeOnClickAction(Class<T> objClass) {
        this.markers
                .stream()
                .filter(marker -> marker.Object.getClass() == objClass)
                .forEach(MapMarker::removeOnAction);
    }

    /**
     * Wählt den zum Objekt gehörigen Marker aus.
     *
     * @param object Zum marker gehöriges Objekt
     * @param changeColor Neue Farbe des Markers
     * @param <T> Typ des Objekts
     */
    public <T> void selectMarker(T object, Marker.Provided changeColor) {
        this.markers
                .stream()
                .filter(marker -> marker.Object == object)
                .forEach(marker -> marker.select(changeColor));
    }

    /**
     * Hebt die Hervorhebung eines Markers auf.
     *
     * @param object Zum Marker zugeordnetes Objekt
     * @param <T> Typ des Objekts
     */
    public <T> void deselectMarker(T object) {
        this.markers
                .stream()
                .filter(marker -> marker.Object == object)
                .forEach(MapMarker::deselect);
    }

    /**
     * Start die Platzierung eines Markers.
     *
     * @param startLocation Startort des Markers (kann null sein)
     */
    public void startMarkerPlacement(Coordinate startLocation) {
        var location = startLocation == null ? mapView.getCenter() : startLocation;
        if (dynamicMarker == null)
            dynamicMarker = new MapMarker<>(mapView, location, Design.COLOR_MAP_PLACEMENT);
        else
            dynamicMarker.move(location, false);

        dynamicMarker.add();
    }

    /**
     * Schließt die Erstellung eines Markers ab.
     *
     * @return Position des Markers
     */
    public Coordinate finalizeMarkerPlacement() {
        var coordinates = dynamicMarker.getMarker().getPosition();

        dynamicMarker.remove();
        dynamicMarker = null;

        return coordinates;
    }

    /**
     * Zeigt eine Liste an CoordinateLines auf der Map an.
     *
     * @param objects Den CoordinateLines zugrundelegende Objekte
     * @param fillColor Innere Farbe des gezeichneten Bereichs
     * @param lineColor Äußere Farbe des gezeichneten Bereichs
     * @param <T> Typ der Objekte
     */
    public <T extends MapCoordinateLineCandidate> void displayCoordinateLines(
            List<T> objects,
            String fillColor,
            String lineColor) {
        objects.forEach(obj -> {
            var line = new MapCoordinateLine<>(
                    mapView,
                    obj,
                    lineColor,
                    fillColor);
            coordinateLines.add(line);
        });
        if (this.mapView.getInitialized())
            drawCoordinateLines(this.coordinateLines);
    }

    /**
     * Fügt der Map eine Liste an CoordinateLines hinzu.
     *
     * @param areas Hinzuzufügende CoordinateLines
     */
    private void drawCoordinateLines(Collection<MapCoordinateLine<?>> areas) {
        areas.forEach(MapCoordinateLine::add);
    }

    /**
     * Hebt die Hervorhebung einer CoordinateLine auf.
     *
     * @param object Zur CoordinateLine zugeordnetes Objekt
     * @param <T> Typ des Objekts
     */
    public <T extends MapCoordinateLineCandidate> void deselectCoordinateLine(T object) {
        this.coordinateLines
                .stream()
                .filter(line -> line.Object == object)
                .forEach(MapCoordinateLine::deselect);
    }

    /**
     * Entfernt eine CoordinateLine von der Map.
     *
     * @param object Mit der CoordinateLine assoziiertes Objekt
     * @param <T> Typ des Objekts
     */
    public <T extends MapCoordinateLineCandidate> void removeCoordinateLine(T object) {
        var toRemove = this.coordinateLines
                .stream()
                .filter(line -> line.Object == object)
                .toList();
        toRemove.forEach(MapCoordinateLine::remove);
        toRemove.forEach(this.coordinateLines::remove);
    }

    /**
     * Definiert eine Aktion, welche ausgeführt wird, wenn ein markierter Bereich vom Typ T angeklickt wird.
     *
     * @param objClass Klasse der zu verwaltenden Objekte
     * @param consumer Funktion, welche das Objekt entgegennimmt, auf welches geklickt wurde.
     * @param changeFillColor Neue Füllfarbe des Bereichs (kann null sein)
     * @param changeLineColor Neue Kantenfarbe des Bereichs (kann null sein)
     * @param <T> Type des Bereichs
     */
    public <T extends MapCoordinateLineCandidate> void onClickCoordinateLine(
            Class<T> objClass,
            Consumer<T> consumer,
            String changeFillColor,
            String changeLineColor) {
        this.coordinateLines
                .stream()
                .filter(line -> line.Object.getClass() == objClass)
                .forEach(line -> ((MapCoordinateLine<T>)line).setOnAction(consumer, changeLineColor, changeFillColor));
    }

    /**
     * Entfernt die onClickCoordinateLine Aktion.
     *
     * @param objClass Klasse der zu verwaltenden Objekte
     * @param <T> Typ des mit der CoordinateLine assoziierten Objektes
     */
    public <T extends MapCoordinateLineCandidate> void removeCoordinateLineOnClickAction(Class<T> objClass) {
        this.coordinateLines
                .stream()
                .filter(line -> line.Object.getClass() == objClass)
                .forEach(MapCoordinateLine::removeOnAction);
    }

    /**
     * Startet das Zeichnen einer neuen GeofencingArea
     */
    public void drawArea() {
        if (drawArea != null)
            return;

        drawArea = new MapCoordinateLine<>(
                this.mapView,
                new ArrayList<>(),
                Design.COLOR_MAP_AREA_LINE_DRAW,
                Design.COLOR_MAP_AREA_FILL_DRAW);
        drawArea.add();
    }

    /**
     * Beendet das Zeichnen der aktuellen GeofencingArea.
     *
     * @return Gezeichnete GeofencingArea
     */
    public GeofencingArea finalizeArea() {
        var coordinates = drawArea.getCoordinateLine().getCoordinateStream().toList();

        drawArea.remove();
        drawArea = null;

        if (coordinates.size() < 3)
            return null;

        return new GeofencingArea(new ArrayList<>(coordinates));
    }
}
