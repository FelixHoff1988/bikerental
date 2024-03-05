package de.wwu.sopra.map;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import de.wwu.sopra.entity.GeofencingArea;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Allgemeine Implementierung der Map zur Einbindung in die weiteren GUIs
 */
public class MapGUI extends BorderPane {
    /**
     * Der MapView, welche die Karte enthält
     */
    private final MapView mapView = new MapView();
    /**
     * Zeigt, ob die Map bereits initialisiert wurde
     */
    private boolean isInitialized;
    /**
     * Liste aller angezeigten Marker mit ihren zugeordneten Objekten
     */
    private final HashMap<Marker, Object> markers = new HashMap<>();
    /**
     * Liste aller angezeigten CoordinateLines mit ihren zugeordneten Objekten
     */
    private final HashMap<CoordinateLine, Object> coordinateLines = new HashMap<>();

    /**
     * Liste der Ecken einer aktuell zu erstellenden GeofencingArea
     */
    private CoordinateLine drawArea;

    /**
     * Bewegbarer Marker für das Platzieren neuer Marker
     */
    private Marker dynamicMarker;

    /**
     * Aktuell angeklickte Marker
     */
    private final HashMap<Class<?>, Marker> clickedMarkers = new HashMap<>();

    /**
     * Aktuell angeklickte CoordinateLines
     */
    private final HashMap<Class<?>, CoordinateLine> clickedLines = new HashMap<>();

    /**
     * Farben den Marker-Typen zugeordnet
     */
    private final HashMap<Class<?>, Marker.Provided> markerColors = new HashMap<>();

    /**
     * Kantenfarben den CoordinateLine-Typen zugeordnet
     */
    private final HashMap<Class<?>, String> coordinateLineColors = new HashMap<>();

    /**
     * Füllfarben den CoordinateLine-Typen zugeordnet
     */
    private final HashMap<Class<?>, String> coordinateLineFillColors = new HashMap<>();

    /**
     * OnClick-Funktionen den Marker-Typen zugeordnet
     */
    private final HashMap<Class<?>, EventHandler<MarkerEvent>> onClickActions = new HashMap<>();

    /**
     * OnClick-Funktionen den Marker-Typen zugeordnet
     */
    private final HashMap<Class<?>, EventHandler<MapViewEvent>> onAreaClickActions = new HashMap<>();

    /**
     * Event handler für das Zeichnen einer neuen GeofencingArea
     */
    private EventHandler<MapViewEvent> onAreaDrawClick;

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
                this.isInitialized = true;
                drawMarkers(this.markers.keySet());
                drawCoordinateLines(this.coordinateLines.keySet());
                if (dynamicMarker != null)
                    mapView.addMarker(dynamicMarker);
            }
        });

        mapView.initialize();

        this.setCenter(mapView);
        this.setMinHeight(500);
    }

    /**
     * Zeigt eine Liste von Markern auf der Map an.
     *
     * @param objects Mit den Markern zu assoziierende Objekte
     * @param coordinateSelector Funktion, welche aus objects die Koordinaten auswählt
     * @param color Farbe der Marker
     * @param <T> Typ der Objects
     */
    public <T> void displayMarkers(List<T> objects, Function<T, Coordinate> coordinateSelector, Marker.Provided color) {
        if (objects.isEmpty())
            return;

        var markerList = new ArrayList<Marker>();
        var type = objects.getFirst().getClass();
        markerColors.put(type, color);
        objects.forEach(obj -> {
            var location = coordinateSelector.apply(obj);
            var marker = Marker
                    .createProvided(color)
                    .setPosition(location)
                    .setVisible(true);
            markerList.add(marker);
            this.markers.put(marker, obj);
        });
        if (this.isInitialized)
            drawMarkers(markerList);
    }

    /**
     * Fügt der Map eine Liste an Markern hinzu.
     *
     * @param bikeMarkers Hinzuzufügende Marker
     */
    private void drawMarkers(Collection<Marker> bikeMarkers) {
        for (var marker : bikeMarkers) {
            mapView.addMarker(marker);
        }
    }

    /**
     * Zeigt eine Liste an CoordinateLines auf der Map an.
     *
     * @param objects Den CoordinateLines zugrundelegende Objekte
     * @param lineSelector Funktion, welche aus den objects eine Liste an Koordinaten auswählt
     * @param fillColor Innere Farbe des gezeichneten Bereichs
     * @param lineColor Äußere Farbe des gezeichneten Bereichs
     * @param <T> Typ der Objekte
     */
    public <T> void displayCoordinateLines(List<T> objects, Function<T, List<Coordinate>> lineSelector, String fillColor, String lineColor) {
        if (objects.isEmpty())
            return;

        var areaList = new ArrayList<CoordinateLine>();
        var type = objects.getFirst().getClass();
        coordinateLineColors.put(type, lineColor);
        coordinateLineFillColors.put(type, fillColor);
        objects.forEach(area -> {
            var line = new CoordinateLine(lineSelector.apply(area))
                    .setColor(Color.web(lineColor, 1))
                    .setFillColor(Color.web(fillColor, 0.4))
                    .setClosed(true)
                    .setVisible(true);
            areaList.add(line);
            this.coordinateLines.put(line, area);
        });
        if (this.isInitialized)
            drawCoordinateLines(areaList);
    }

    /**
     * Fügt der Map eine Liste an CoordinateLines hinzu.
     *
     * @param areas Hinzuzufügende CoordinateLines
     */
    private void drawCoordinateLines(Collection<CoordinateLine> areas) {
        for (var area : areas) {
            mapView.addCoordinateLine(area);
        }
    }

    /**
     * Entfernt eine CoordinateLine von der Map.
     *
     * @param object Mit der CoordinateLine assoziiertes Objekt
     * @param <T> Typ des Objekts
     */
    public <T> void removeCoordinateLine(T object) {
        var filter = coordinateLines.entrySet().stream().filter(entry -> entry.getValue() == object).toList();

        if (filter.isEmpty())
            return;

        clickedLines.remove(filter.getFirst().getValue().getClass());
        coordinateLines.remove(filter.getFirst().getKey());
        mapView.removeCoordinateLine(filter.getFirst().getKey());
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
    public <T> void onClickCoordinateLine(
            Class<T> objClass,
            Consumer<T> consumer,
            String changeFillColor,
            String changeLineColor) {
        EventHandler<MapViewEvent> eventHandler = event -> {
            var lines = coordinateLines
                    .entrySet()
                    .stream()
                    .filter(entry -> MapFunctions.isCoordinateInArea(event.getCoordinate(), entry.getKey().getCoordinateStream().toList()))
                    .toList();

            if (lines.isEmpty())
                return;

            var object = lines.getFirst().getValue();
            var type = object.getClass();

            if (objClass != type)
                return;

            var isCurrentLine = clickedLines
                    .entrySet()
                    .stream()
                    .anyMatch(entry -> entry.getValue() == lines.getFirst().getKey());
            deselectCoordinateLine(type, null, null);
            if (!isCurrentLine)
                selectCoordinateLine(lines.getFirst().getKey(), type, changeFillColor, changeLineColor);

            consumer.accept(objClass.cast(object));
        };

        onAreaClickActions.put(objClass, eventHandler);
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, eventHandler);
    }

    /**
     * Entfernt die onClickCoordinateLine Aktion.
     *
     * @param objClass Klasse der zu verwaltenden Objekte
     * @param <T> Typ des mit der CoordinateLine assoziierten Objektes
     */
    public <T> void removeCoordinateLineOnClickAction(Class<T> objClass) {
        mapView.removeEventHandler(MapViewEvent.MAP_CLICKED, onAreaClickActions.get(objClass));
        onAreaClickActions.remove(objClass);
    }

    /**
     * Hebt die Hervorhebung eines Markers auf.
     *
     * @param object Der CoordinateLine zugeordnetes Objekt
     * @param changeFillColor Neue Füllfarbe (kann null sein)
     * @param changeLineColor Neue Kantenfarbe (kann null sein)
     * @param <T> Typ des Objekts
     */
    public <T> void deselectCoordinateLine(T object, String changeFillColor, String changeLineColor) {
        deselectCoordinateLine(object.getClass(), changeFillColor, changeLineColor);
    }

    /**
     * Stellt die originale Farbe für eine CoordinateLines eines bestimmten Types wieder her.
     *
     * @param type Repräsentierter Datentyp
     * @param changeFillColor Neue Füllfarbe (kann null sein)
     * @param changeLineColor Neue Kantenfarbe (kann null sein)
     */
    private void deselectCoordinateLine(Class<?> type, String changeFillColor, String changeLineColor) {
        var line = clickedLines.get(type);

        if (line == null)
            return;

        line.setColor(changeLineColor != null
                ? Color.web(changeLineColor, 1)
                : Color.web(coordinateLineColors.get(type), 1));
        line.setFillColor(changeFillColor != null
                ? Color.web(changeFillColor, 0.4)
                : Color.web(coordinateLineFillColors.get(type), 0.4));

        mapView.removeCoordinateLine(line);
        mapView.addCoordinateLine(line);
        clickedLines.remove(type);
    }

    /**
     * Wähle einen bestehenden Bereich aus.
     *
     * @param coordinateLine Auszuwählender Bereich
     * @param type Type des Bereichs
     * @param changeFillColor Neue Füllfarbe des Bereichs (kann null sein)
     * @param changeLineColor Neue Kantenfarbe des Bereichs (kann null sein)
     */
    private void selectCoordinateLine(CoordinateLine coordinateLine, Class<?> type, String changeFillColor, String changeLineColor) {
        if (changeLineColor != null)
            coordinateLine.setColor(Color.web(changeLineColor, 1));
        if (changeFillColor != null)
            coordinateLine.setFillColor(Color.web(changeFillColor, 0.4));

        mapView.removeCoordinateLine(coordinateLine);
        mapView.addCoordinateLine(coordinateLine);
        clickedLines.put(type, coordinateLine);
    }

    /**
     * Definiert eine Aktion, welche ausgeführt wird, wenn ein Marker vom Typ T angeklickt wird.
     *
     * @param objClass Klasse der zu verwaltenden Objekte
     * @param consumer Funktion, welche das Objekt entgegennimmt, auf welches geklickt wurde.
     * @param changeColor Neue Farbe des Markers (kann null sein)
     * @param <T> Type des Markers
     */
    public <T> void onClickMarker(Class<T> objClass, Consumer<T> consumer, Marker.Provided changeColor) {
        EventHandler<MarkerEvent> eventHandler = event -> {
            var object = this.markers.get(event.getMarker());
            var type = object.getClass();

            if (objClass != type)
                return;

            var isCurrentMarker = clickedMarkers
                    .entrySet()
                    .stream()
                    .anyMatch(entry -> entry.getValue() == event.getMarker());
            deselectMarker(type, null);
            if (!isCurrentMarker)
                selectMarker(event.getMarker(), type, changeColor);

            consumer.accept(objClass.cast(object));
        };

        onClickActions.put(objClass, eventHandler);
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, eventHandler);
    }

    /**
     * Entfernt die onClickMarker Aktion.
     *
     * @param objClass Klasse der zu verwaltenden Objekte
     * @param <T> Typ des mit dem Marker assoziierten Objektes
     */
    public <T> void removeOnClickAction(Class<T> objClass) {
        mapView.removeEventHandler(MarkerEvent.MARKER_CLICKED, onClickActions.get(objClass));
        onClickActions.remove(objClass);
    }

    /**
     * Hebt die Hervorhebung eines Markers auf.
     *
     * @param object Zum Marker zugeordnetes Objekt
     * @param changeColor Neue Farbe des Markers
     * @param <T> Typ des Objekts
     */
    public <T> void deselectMarker(T object, Marker.Provided changeColor) {
        deselectMarker(object.getClass(), changeColor);
    }

    /**
     * Stellt die originale Farbe für alle Marker eines bestimmten Types wieder her.
     *
     * @param type Repräsentierter Datentyp
     * @param changeColor Neue Anzeigefarbe (kann null sein)
     */
    private void deselectMarker(Class<?> type, Marker.Provided changeColor) {
        var marker = clickedMarkers.get(type);

        if (marker == null)
            return;

        var object = markers.get(marker);

        mapView.removeMarker(marker);
        markers.remove(marker);
        clickedMarkers.remove(type);

        var prevMarker = Marker
                .createProvided(changeColor == null ? markerColors.get(type) : changeColor)
                .setPosition(marker.getPosition())
                .setVisible(true);

        mapView.addMarker(prevMarker);
        markers.put(prevMarker, object);
    }

    /**
     * Wählt den zum Objekt gehörigen Marker aus.
     *
     * @param object Zum marker gehöriges Objekt
     * @param changeColor Neue Farbe des Markers
     * @param <T> Typ des Objekts
     */
    public <T> void selectMarker(T object, Marker.Provided changeColor) {
        var type = object.getClass();
        var filtered = markers.entrySet().stream().filter(entry -> entry.getValue() == object).toList();

        if (filtered.isEmpty())
            return;

        selectMarker(filtered.getFirst().getKey(), type, changeColor);
    }

    /**
     * Wähle einen bestehenden Marker aus.
     *
     * @param marker Auszuwählender Marker
     * @param type Type des Markers
     * @param changeColor Neue Farbe des Markers (kann null sein)
     */
    private void selectMarker(Marker marker, Class<?> type, Marker.Provided changeColor) {
        if (changeColor == null) {
            clickedMarkers.put(type, marker);
            return;
        }

        var object = markers.get(marker);

        mapView.removeMarker(marker);
        markers.remove(marker);

        var clickedMarker = Marker
                    .createProvided(changeColor)
                    .setPosition(marker.getPosition())
                    .setVisible(true);

        mapView.addMarker(clickedMarker);
        markers.put(clickedMarker, object);
        clickedMarkers.put(type, clickedMarker);
    }

    /**
     * Startet das Zeichnen einer neuen GeofencingArea
     */
    public void drawArea() {
        this.onAreaDrawClick = event -> handleDrawEvent(event);
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, onAreaDrawClick);
        drawArea = new CoordinateLine();
    }

    /**
     * Beendet das Zeichnen der aktuellen GeofencingArea.
     *
     * @return Gezeichnete GeofencingArea
     */
    public GeofencingArea finalizeArea() {
        var coordinates = drawArea.getCoordinateStream().toList();

        mapView.removeEventHandler(MapViewEvent.MAP_CLICKED, onAreaDrawClick);
        mapView.removeCoordinateLine(drawArea);

        drawArea = null;
        onAreaDrawClick = null;

        if (coordinates.size() < 3)
            return null;

        return new GeofencingArea(new ArrayList<>(coordinates));
    }

    /**
     * Definiert, was bei einem Klick auf die Map passieren soll, während eine GeofencingArea gezeichnet wird.
     *
     * @param event Aktuelles Klick-Event
     */
    private void handleDrawEvent(MapViewEvent event) {
        event.consume();

        var clickCoordinate = event.getCoordinate();
        var coordinates = new ArrayList<>(drawArea.getCoordinateStream().toList());

        if (!MapFunctions.isValidCoordinateLine(coordinates, clickCoordinate, coordinates.size()))
            return;

        coordinates.add(clickCoordinate);

        if (drawArea != null)
            mapView.removeCoordinateLine(drawArea);

        drawArea = new CoordinateLine(coordinates)
                .setColor(Color.DODGERBLUE)
                .setFillColor(Color.web("lawngreen", 0.4))
                .setClosed(true)
                .setVisible(true);

        mapView.addCoordinateLine(drawArea);
    }

    /**
     * Start die Platzierung eines Markers.
     *
     * @param startLocation Startort des Markers (kann null sein)
     */
    public void startMarkerPlacement(Coordinate startLocation) {
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, this::handleMarkerPlacement);
        dynamicMarker = Marker
                .createProvided(Marker.Provided.RED)
                .setPosition(startLocation == null ? mapView.getCenter() : startLocation)
                .setVisible(true);

        if (this.isInitialized)
            mapView.addMarker(dynamicMarker);
    }

    /**
     * Schließt die Erstellung eines Markers ab.
     *
     * @return Position des Markers
     */
    public Coordinate finalizeMarkerPlacement() {
        var coordinates = dynamicMarker.getPosition();

        mapView.removeEventHandler(MapViewEvent.MAP_CLICKED, this::handleMarkerPlacement);
        dynamicMarker = null;

        return coordinates;
    }

    /**
     * Handelt das Klick-Event für die Platzierung eines neuen Markers.
     *
     * @param event Mausklick auf die Map
     */
    private void handleMarkerPlacement(MapViewEvent event) {
        event.consume();
        animateMarkerMovement(dynamicMarker, event.getCoordinate());
    }

    /**
     * Animiert die Bewegung eines Markers an eine neue Position.
     *
     * @param marker Zu bewegender Marker
     * @param newPosition Neue Position des Markers
     */
    private void animateMarkerMovement(Marker marker, Coordinate newPosition) {
        var oldPosition = marker.getPosition();

        final Transition transition = new Transition() {
            private final Double oldPositionLongitude = oldPosition.getLongitude();
            private final Double oldPositionLatitude = oldPosition.getLatitude();
            private final double deltaLatitude = newPosition.getLatitude() - oldPositionLatitude;
            private final double deltaLongitude = newPosition.getLongitude() - oldPositionLongitude;

            {
                setCycleDuration(Duration.seconds(1.0));
                setOnFinished(evt -> marker.setPosition(newPosition));
            }

            @Override
            protected void interpolate(double v) {
                final double latitude = oldPosition.getLatitude() + v * deltaLatitude;
                final double longitude = oldPosition.getLongitude() + v * deltaLongitude;
                marker.setPosition(new Coordinate(latitude, longitude));
            }
        };

        transition.play();
    }
}
