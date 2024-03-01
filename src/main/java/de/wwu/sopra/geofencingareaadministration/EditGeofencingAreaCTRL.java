package de.wwu.sopra.geofencingareaadministration;

import java.util.List;
import java.util.regex.Pattern;

import com.sothawo.mapjfx.Coordinate;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.BikeStation;
import de.wwu.sopra.entity.GeofencingArea;
import de.wwu.sopra.map.MapGUI;
import javafx.scene.control.TextField;

public class EditGeofencingAreaCTRL {
    /**
     * Constructor
     */
    public EditGeofencingAreaCTRL() {

    }

    /**
     * lädt die liste aller GeofencingAreas
     * @return Liste aller GeofencingAreas
     */
    public List<GeofencingArea> loadGeofencingAreas() {
        return DataProvider.getInstance().getGeoAreas();
    }
    
    /**
     * löscht eine GeofencingArea
     * @param toRemove GeofencingArea, welche gelöscht werden soll
     */
    public void removeGeofencingArea(GeofencingArea toRemove) {
        DataProvider prov = DataProvider.getInstance();
        prov.removeGeoArea(toRemove);
    }
    
    /**
     * Bei Start der Ansicht der Seite werden alle Areas initialisiert
     * @param map zu bezeichnende Karte
     */
    public void initializeAreas(MapGUI map)
    {
        DataProvider prov = DataProvider.getInstance();
        if (!prov.getGeoAreas().isEmpty())
        {
            map.displayCoordinateLines(prov.getGeoAreas(), GeofencingArea::getEdges, "limegreen", "dodgerblue");
        }
    }
}
