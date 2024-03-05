package de.wwu.sopra;

import com.sothawo.mapjfx.Marker;

/**
 * Design Konstanten und Funktionen
 */
public abstract class Design {
    /**
     * Standardkonstruktor: ungenutzt
     */
    public Design() {}

    /**
     * Standard-Anzeigedauer für Dialoge
     */
    public final static int DIALOG_TIME_STANDARD = 5;
    /**
     * Standardfarbe (Erfolg) für Dialoge
     */
    public final static String COLOR_DIALOG_FAILURE = "#FFCCDD";
    /**
     * Standardfarbe (Fehler) für Dialoge
     */
    public final static String COLOR_DIALOG_SUCCESS = "#CCFFCC";
    /**
     * Standardfarbe für Bike Marker auf der Map
     */
    public final static Marker.Provided COLOR_MAP_BIKE_DEFAULT = Marker.Provided.ORANGE;
    /**
     * Standardfarbe für ausgewählte Bike Marker auf der Map
     */
    public final static Marker.Provided COLOR_MAP_BIKE_SELECTED = Marker.Provided.GREEN;
    /**
     * Standardfarbe für neue Bike Marker auf der Map
     */
    public final static Marker.Provided COLOR_MAP_PLACEMENT = Marker.Provided.GREEN;
    /**
     * Standardfarbe für BikeStation Marker auf der Map
     */
    public final static Marker.Provided COLOR_MAP_STATION_DEFAULT = Marker.Provided.BLUE;
    /**
     * Standard-Füllfarbe für GeoAreas auf der Map
     */
    public final static String COLOR_MAP_AREA_FILL_DEFAULT = "limegreen";
    /**
     * Standard-Linienfarbe für GeoAreas auf der Map
     */
    public final static String COLOR_MAP_AREA_LINE_DEFAULT = "dodgerblue";
    /**
     * Standard-Füllfarbe für ausgewählte GeoAreas auf der Map
     */
    public final static String COLOR_MAP_AREA_FILL_SELECTED = "orange";
    /**
     * Standard-Linienfarbe für ausgewählte GeoAreas auf der Map
     */
    public final static String COLOR_MAP_AREA_LINE_SELECTED = "red";
    /**
     * Standard-Füllfarbe für ausgewählte GeoAreas auf der Map
     */
    public final static String COLOR_MAP_AREA_FILL_DRAW = "lawngreen";
    /**
     * Standard-Linienfarbe für ausgewählte GeoAreas auf der Map
     */
    public final static String COLOR_MAP_AREA_LINE_DRAW = "dodgerblue";
}
