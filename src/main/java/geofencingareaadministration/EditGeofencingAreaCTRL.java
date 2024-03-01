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
     * fügt runtime liste aller BikeStations eine neue BikeStation hinzu
     * @param textFieldsBikeStation EingabeFelder der BikeStation
     * @return BikeStation welche hinzugefügt wurde
     */
    public BikeStation addBikeStation(TextField[] textFieldsBikeStation) {
        BikeStation addedBikeStation = new BikeStation(
                textFieldsBikeStation[0].getText(),
                new Coordinate((double) 0, (double) 0),
                Integer.valueOf(textFieldsBikeStation[1].getText())
        );

        DataProvider prov = DataProvider.getInstance();
        prov.addStation(addedBikeStation);
        return addedBikeStation;
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
