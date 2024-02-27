package de.wwu.sopra.map;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.BikeStation;
import de.wwu.sopra.entity.GeofencingArea;
import javafx.animation.Transition;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
     * Zeigt, ob die Map bereits initialisiert wurde
     */
    private boolean isInitialized;
    /**
     * Liste aller angezeigten Marker mit ihren zugeordneten Bikes
     */
    private final HashMap<Marker, Bike> bikeMarkers = new HashMap<>();
    /**
     * Liste aller angezeigten Marker mit ihren zugeordneten BikeStations
     */
    private final HashMap<Marker, BikeStation> stationMarkers = new HashMap<>();
    /**
     * Liste aller angezeigten CoordinateLines mit ihren zugeordneten GeoAreas
     */
    private final HashMap<CoordinateLine, GeofencingArea> geoAreas = new HashMap<>();

    /**
     * Liste der Ecken einer aktuell zu erstellenden GeofencingArea
     */
    private CoordinateLine drawArea;

    /**
     * Bewegbarer Marker für das Platzieren neuer Marker
     */
    private Marker dynamicMarker;

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
                drawMarkers(this.bikeMarkers.keySet());
                drawMarkers(this.stationMarkers.keySet());
                drawCoordinateLines(this.geoAreas.keySet());
                if (dynamicMarker != null)
                    mapView.addMarker(dynamicMarker);
            }
        });

        mapView.initialize();

        this.setCenter(mapView);
        this.setMinHeight(500);
    }

    /**
     * Zeigt eine Liste an Fahrrädern auf der Karte an.
     *
     * @param bikes Liste an anzuzeigenden Fahrrädern
     */
    public void displayBikes(List<Bike> bikes) {
        var markerList = new ArrayList<Marker>();
        bikes.forEach(bike -> {
            var marker = Marker
                    .createProvided(Marker.Provided.ORANGE)
                    .setPosition(bike.getLocation())
                    .setVisible(true);
            markerList.add(marker);
            this.bikeMarkers.put(marker, bike);
        });
        if (this.isInitialized)
            drawMarkers(markerList);
    }

    /**
     * Zeigt eine Liste an BikeStations auf der Map an.
     *
     * @param stations Anzuzeigende BikeStations
     */
    public void displayStations(List<BikeStation> stations) {
        var markerList = new ArrayList<Marker>();
        stations.forEach(station -> {
            var marker = Marker
                    .createProvided(Marker.Provided.BLUE)
                    .setPosition(station.getLocation())
                    .setVisible(true);
            markerList.add(marker);
            this.stationMarkers.put(marker, station);
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
     * Zeigt eine Liste an GeofencingAreas auf der Map an.
     *
     * @param areas Anzuzeigende GeofencingAreas
     */
    public void displayAreas(List<GeofencingArea> areas) {
        var areaList = new ArrayList<CoordinateLine>();
        areas.forEach(area -> {
            var line = new CoordinateLine(area.getEdges())
                    .setColor(Color.DODGERBLUE)
                    .setFillColor(Color.web("lawngreen", 0.4))
                    .setClosed(true)
                    .setVisible(true);
            areaList.add(line);
            this.geoAreas.put(line, area);
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
     * Definiert eine Aktion, welche ausgeführt wird, wenn ein Bike-Marker angeklickt wird.
     *
     * @param consumer Funktion, welche das Bike entgegennimmt, auf welches geklickt wurde.
     */
    public void onClickBike(Consumer<Bike> consumer) {
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            var bike = this.bikeMarkers.get(event.getMarker());
            if (bike != null)
                consumer.accept(bike);
        });
    }

    /**
     * Definiert eine Aktion, welche ausgeführt wird, wenn ein BikeStation-Marker angeklickt wird.
     *
     * @param consumer Funktion, welche das BikeStation entgegennimmt, auf welches geklickt wurde.
     */
    public void onClickStation(Consumer<BikeStation> consumer) {
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            var station = this.stationMarkers.get(event.getMarker());
            if (station != null)
                consumer.accept(station);
        });
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

        if (!MapFunctions.insertAtNearestSide(coordinates, clickCoordinate))
            return;

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
     * Start die Platzierung eines neuen Markers.
     */
    public void placeNewMarker() {
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, this::handleMarkerPlacement);
        dynamicMarker = Marker
                .createProvided(Marker.Provided.RED)
                .setPosition(mapView.getCenter())
                .setVisible(true);

        if (this.isInitialized)
            mapView.addMarker(dynamicMarker);
    }

    /**
     * Schließt die Erstellung eines neuen Markers ab.
     *
     * @return Position des neuen Markers
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
