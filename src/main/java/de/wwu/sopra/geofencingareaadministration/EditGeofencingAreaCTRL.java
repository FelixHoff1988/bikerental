package de.wwu.sopra.geofencingareaadministration;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.Design;
import de.wwu.sopra.entity.GeofencingArea;
import de.wwu.sopra.map.MapGUI;

/**
 * Steuerungsklasse zur GeofencingArea GUI Klasse
 */
public class EditGeofencingAreaCTRL {
    /**
     * Constructor
     */
    public EditGeofencingAreaCTRL() {

    }
    
    /**
     * löscht eine GeofencingArea
     * @param toRemove GeofencingArea, welche gelöscht werden soll
     */
    public void removeGeofencingArea(GeofencingArea toRemove) {
        DataProvider prov = DataProvider.getInstance();
        var success = prov.removeGeoArea(toRemove);
        if (success)
            AppContext.getInstance().showMessage(
                    "Die Geofencing-Area wurde erfolgreich entfernt!",
                    Design.DIALOG_TIME_STANDARD,
                    Design.COLOR_DIALOG_SUCCESS);
    }

    /**
     * Fügt eine GeofencingArea hinzu
     * @param toAdd GeofencingArea, welche hinzugefügt werden soll
     */
    public void addGeofencingArea(GeofencingArea toAdd) {
        DataProvider prov = DataProvider.getInstance();
        var success = prov.addGeoArea(toAdd);
        if (success)
            AppContext.getInstance().showMessage(
                    "Die Geofencing-Area wurde erfolgreich hinzugefügt!",
                    Design.DIALOG_TIME_STANDARD,
                    Design.COLOR_DIALOG_SUCCESS);
    }
    
    /**
     * Bei Start der Ansicht der Seite werden alle Areas initialisiert
     * @param map zu bezeichnende Karte
     */
    public void initializeAreas(MapGUI map)
    {
        DataProvider prov = DataProvider.getInstance();
        map.displayCoordinateLines(prov.getGeoAreas(), GeofencingArea::getEdges, "limegreen", "dodgerblue");
    }
}
