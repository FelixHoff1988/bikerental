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
     * Farben den Marker-Typen zugeordnet
     */
    private final HashMap<Class<?>, Marker.Provided> markerColors = new HashMap<>();

    /**
     * OnClick-Funktionen den Marker-Typen zugeordnet
     */
    private final HashMap<Class<?>, EventHandler<MarkerEvent>> onClickActions = new HashMap<>();

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
        var areaList = new ArrayList<CoordinateLine>();
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
     * Definiert eine Aktion, welche ausgeführt wird, wenn ein Marker vom Typ T angeklickt wird.
     *
     * @param consumer Funktion, welche das Objekt entgegennimmt, auf welches geklickt wurde.
     * @param changeColor Neue Farbe des Markers (kann null sein)
     * @param <T> Type des Markers
     */
    public <T> void onClickMarker(Consumer<T> consumer, Marker.Provided changeColor) {
        var consumerClass = consumer.getClass();
        EventHandler<MarkerEvent> eventHandler = event -> {
            var object = this.markers.get(event.getMarker());

            T typedObject = null;
            try { typedObject = (T) object; } catch (Exception ignored) {}

            if (typedObject == null)
                return;

            var type = typedObject.getClass();

            var isCurrentMarker = clickedMarkers
                    .entrySet()
                    .stream()
                    .anyMatch(entry -> entry.getValue() == event.getMarker());
            deselectMarker(type, null);
            if (!isCurrentMarker)
                selectMarker(event.getMarker(), type, changeColor);

            consumer.accept(typedObject);
        };

        onClickActions.put(consumerClass, eventHandler);
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, eventHandler);
    }

    /**
     * Entfernt die onClickMarker Aktion.
     *
     * @param consumer Gleiche Funktion, welche bei onClickMarker übergeben wurde.
     * @param <T> Typ des mit dem Marker assoziierten Objektes
     */
    public <T> void removeOnClickAction(Consumer<T> consumer) {
        mapView.removeEventHandler(MarkerEvent.MARKER_CLICKED, onClickActions.get(consumer.getClass()));
        onClickActions.remove(consumer.getClass());
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
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, this::handleDrawEvent);
    }

    /**
     * Beendet das Zeichnen der aktuellen GeofencingArea.
     *
     * @return Gezeichnete GeofencingArea
     */
    private GeofencingArea finalizeArea() {
        var coordinates = drawArea.getCoordinateStream().toList();

        mapView.removeEventHandler(MapViewEvent.MAP_CLICKED, this::handleDrawEvent);
        mapView.removeCoordinateLine(drawArea);

        if (coordinates.size() < 3)
            return null;

        var geoArea = new GeofencingArea(new ArrayList<>(coordinates));

        drawArea = null;
        return geoArea;
    }

    /**
     * Definiert, was bei einem Klick auf die Map passieren soll, während eine GeofencingArea gezeichnet wird.
     *
     * @param event Aktuelles Klick-Event
     */
    private void handleDrawEvent(MapViewEvent event) {
        var clickCoordinate = event.getCoordinate();
        var coordinates = drawArea == null
                ? new ArrayList<Coordinate>()
                : new ArrayList<>(drawArea.getCoordinateStream().toList());

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

        mapView.removeEventHandler(MapViewEvent.MAP_CLICKED, this::handleDrawEvent);
        dynamicMarker = null;

        return coordinates;
    }

    /**
     * Handelt das Klick-Event für die Platzierung eines neuen Markers.
     *
     * @param event Mausklick auf die Map
     */
    private void handleMarkerPlacement(MapViewEvent event) {
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
