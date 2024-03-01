package geofencingareaadministration;

import java.util.List;
import java.util.regex.Pattern;

import com.sothawo.mapjfx.Coordinate;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.BikeStation;
import de.wwu.sopra.entity.GeofencingArea;
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
}
