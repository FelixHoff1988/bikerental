/**
 * @author Nisa
 */

package de.wwu.sopra.reportdefect;

import java.time.LocalDateTime;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.Repair;
import de.wwu.sopra.entity.Service;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller Klasse zum erstellen einer Schadensmeldung
 */
public class ReportCTRL {

    /**
     * Standardkonstruktor
     */

    public ReportCTRL() {

    }

    /**
     * erstelle einen report, erstelle eine repair
     * 
     * @param statement Beschreibung des Defekts den ein User gibt
     * @param bike      user gibt fahrrad an welches kaputt ist
     */
    public void submitReport(String statement, Bike bike) {
        DataProvider prov = DataProvider.getInstance();
        LocalDateTime now = LocalDateTime.now();
        Repair rep = new Repair(now, statement, bike);
        prov.addService(rep);
    }

    /**
     * finds bike
     * 
     * @param bikeNumber nummer des gesuchten bikes
     * @return bike falls ein bike nummer hat, sonst null
     */
    public Bike findBike(String bikeNumber) {
        DataProvider prov = DataProvider.getInstance();
        return prov.getBike(bikeNumber);
    }

}
