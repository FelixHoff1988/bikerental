package de.wwu.sopra;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.GeofencingArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.function.Consumer;

public class MapGUI extends BorderPane {
    /**
     * Der MapView, welche die Karte enthält
     */
    private MapView mapView;
    /**
     * Zeigt, ob die Map bereits initialisiert wurde
     */
    private boolean isInitialized;
    /**
     * Liste aller angezeigten Marker mit ihren zugeordneten Bikes
     */
    private HashMap<Marker, Bike> bikeMarkers = new HashMap<>();
    /**
     * Liste aller angezeigten CoordinateLines mit ihren zugeordneten GeoAreas
     */
    private HashMap<CoordinateLine, GeofencingArea> geoAreas = new HashMap<>();

    /**
     * Liste der Ecken einer aktuell zu erstellenden GeofencingArea
     */
    private CoordinateLine drawArea;

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
        mapView = new MapView();

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
                drawBikes(this.bikeMarkers.keySet());
                drawAreas(this.geoAreas.keySet());
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
            drawBikes(markerList);
    }

    /**
     * Fügt der Map eine Liste an Markern hinzu.
     *
     * @param bikeMarkers Hinzuzufügende Marker
     */
    private void drawBikes(Collection<Marker> bikeMarkers) {
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
            drawAreas(areaList);
    }

    /**
     * Fügt der Map eine Liste an CoordinateLines hinzu.
     *
     * @param areas Hinzuzufügende CoordinateLines
     */
    private void drawAreas(Collection<CoordinateLine> areas) {
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
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> consumer.accept(this.bikeMarkers.get(event.getMarker())));
    }

    public void drawArea() {
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, this::handleDrawEvent);
    }

    private GeofencingArea finalizeArea() {
        mapView.removeEventHandler(MapViewEvent.MAP_CLICKED, this::handleDrawEvent);
        mapView.removeCoordinateLine(drawArea);

        var coordinates = drawArea.getCoordinateStream().toList();
        if (coordinates.size() < 3)
            return null;

        var geoArea = new GeofencingArea(new ArrayList<>(coordinates));

        drawArea = null;
        return geoArea;
    }

    private void handleDrawEvent(MapViewEvent event) {
        var clickCoordinate = event.getCoordinate();
        var coordinates = drawArea == null
                ? new ArrayList<Coordinate>()
                : new ArrayList<>(drawArea.getCoordinateStream().toList());



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
}
