/**
 * @author David
 * @author Nisa
 * @author Eike Elias
 */
package de.wwu.sopra.bikemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sothawo.mapjfx.Coordinate;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.BikeStation;

/**
 * KontrollKlasse zur Staitions-Manager GUI
 */
public class BikeManagementCTRL {

    /**
     * DataProvider-Instanz
     */
    private DataProvider prov = DataProvider.getInstance();
    /**
     * Random-Instanz
     */
    private Random random  = new Random();
    /**
     * Methode zum Laden der Bikes
     * @return Liste der gespeicherten Bikes
     */
    public List<Bike> loadBikes() {
        return DataProvider.getInstance().getBikes();
    }
    
    /**
     * Methode zum Laden der BikeStations
     * @return Liste der BikeStations
     */
    public List<String> loadStations(){
        List<BikeStation> stations = DataProvider.getInstance().getStations();
        List<String> stationStrings = new ArrayList<>();
        for(BikeStation b: stations) {
            stationStrings.add(b.getName());
        }
        return stationStrings;
    }
    
    /**
     * Methode zum Laden einer Bikestation mit einem bestimmten Namen
     * @param name Name der Station
     * @return Station mit Name name, null falls keine existiert
     */
    public BikeStation getStation(String name) {
        List<BikeStation> stations = DataProvider.getInstance().getStations(station ->{
            return station.getName().equals(name);
        });
        if(!stations.isEmpty())
            return stations.getFirst();
        else
            return null;
    }
    
    /**
     * Methode um Fahrräder eines Zustandes zu laden
     * @param status Fahrräder mit diesem Verfügbarkeits-Status laden
     * @return Liste von Fahrrädern mit dem gegebenen Verfügbarkeits-Status
     */
    public List<Bike> loadBikes(Availability status) {
        List<Bike> bikes = new ArrayList<>();
        bikes = prov.getBikes(bike ->{
            if(bike.getAvailability() == status)
                return true;
            return false;
        });
        return bikes;
    }
    
    /**
     * Methode zum Blockieren eines Fahrrads
     * @param bike zu blockierendes Fahrrad
     */
    public void blockBike(Bike bike) {
        bike.setAvailability(Availability.BLOCKED);
        
        List<BikeStation> stations = prov.getStations(station ->{
            return station.getBikes().contains(bike);
        });
        for(BikeStation s:stations) {
            s.getBikes().remove(bike);
        }
    }
    
    /**
     * Methode zum ent-Blockieren eines Fahrrads
     * @param bike zu ent-blockierendes fahrrad
     * @param station station der das Bike hinzugefügt wird
     */
    public void deBlockBike(Bike bike, String station) {
        bike.setAvailability(Availability.AVAILABLE);
        BikeStation bStation = getStation(station);
        
        var basePosition = bStation.getLocation();
        
        var metersToMove = random.nextInt(5);
        var metersLat = random.nextInt(5);
        var latNegative = random.nextInt(2) == 1;
        var lngNegative = random.nextInt(2) == 1;

        var lng = basePosition.getLongitude()
                + ((180 / Math.PI) * ((metersToMove-metersLat) / 6378137D) * (lngNegative ? -1 : 1)) / Math.cos(basePosition.getLatitude());
        var lat = basePosition.getLatitude()
                + (180 / Math.PI) * ((metersToMove-metersLat) / 6378137D) * (latNegative ? -1 : 1);

        bike.setLocation(new Coordinate(lat, lng));
        bStation.addBike(bike);
    }
    
}
