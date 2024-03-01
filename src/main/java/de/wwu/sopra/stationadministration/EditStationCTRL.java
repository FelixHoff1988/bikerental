package de.wwu.sopra.stationadministration;

import java.util.List;
import java.util.regex.Pattern;

import com.sothawo.mapjfx.Coordinate;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.entity.BikeStation;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.entity.UserRole;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * Kontrollklasse zum Editieren von Stationen durch den Admin
 */
public class EditStationCTRL {
    /**
     * Constructor
     */
    public EditStationCTRL() {

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
     * Prüft, ob Eingabe in TextField von EditUserGUI korrekt ist
     * 
     * @param regex     Regex code um Korrektheit der Eingabe eines Feldes zu
     *                  überprüfen
     * @param textField Text Field aus Registrierungsform
     * @return true wenn Input aus EditUserGUI korrekt ist
     */
    public boolean testTextField(String regex, TextField textField) {
        if (!Pattern.matches(regex, textField.getText())) {
            textField.setStyle("-fx-background-color: #FFA59D;");
            return false;
        } else {
            textField.setStyle("");
            return true;
        }
    }

    /**
     * lädt die liste aller BikeStations
     * @return Liste aller BikeStations
     */
    public List<BikeStation> loadStations() {
        return DataProvider.getInstance().getStations();
    }
    
    /**
     * löscht eine BikeStation
     * @param bikeStation
     */
    public void removeBikeStation(BikeStation bikeStation) {
        DataProvider prov = DataProvider.getInstance();
        prov.removeStation(bikeStation);
    }
}
