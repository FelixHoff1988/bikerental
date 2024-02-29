package de.wwu.sopra;

import com.sothawo.mapjfx.Coordinate;
import de.wwu.sopra.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die DataProvider Klasse
 */
public class DataProviderTests {
    // Es folgen Mock-Daten
    private DataProvider provider = DataProvider.getInstance();
    private final String bikeTypeName = "Fahrrad";
    private final StandardType mockBikeType = new StandardType(bikeTypeName, 52, 100);

    private final Coordinate[] mockCoordinates = new Coordinate[] {
            new Coordinate(0.0, 0.0),
            new Coordinate(1.0, 1.0),
    };
    private final GeofencingArea mockGeoArea = new GeofencingArea(new ArrayList<>(List.of(mockCoordinates)));

    private final String mockBikeId = "RM001896T8";
    private final Bike mockBike = new Bike(mockBikeType, mockBikeId, mockCoordinates[0], mockGeoArea);

    private final String stationName = "Meine Station";
    private final BikeStation mockBikeStation = new BikeStation(stationName, mockCoordinates[1], 20);

    private final String userEmail = "günther-der-große@gmail.com";
    private final User mockUser = new User(
            "Heinz",
            "Günther",
            "Fress-Mich-Doch-Weg",
            (int) 15,
            (int) 76856,
            "Maiwöldchen",
            userEmail,
            "DE87 3728 0000 1123 3947 12",
            "CIBILIDUSQUACK",
            new PasswordHashing.PasswordHash(
                    // Password: ¦'?>4g¾ì©íc¨0IfûÃßO`»)c={kAl¡Ñ`d;;ÝxgrkK#÷Ý¿M°XãËÿ¦³;Ãf~\c(huÏMú
                    "b6e8012386d585bbd9b5057b759ebc9d",
                    "2ac4fcabf13bf1cb57ab450f553e747bb9fc2f0c9f709645bac62b27c0e66371496764a5638dc84524a8d47c388b2c690016c02c55a7a7f03c146bd6d2a4b17f"),
            UserRole.MAINTAINER);

    private final LocalDateTime reservationStartDate = LocalDateTime.of(2024, 2, 24, 10, 42);
    private final Reservation mockReservation = new Reservation(
            reservationStartDate,
            mockUser,
            mockBike);

    private final LocalDateTime serviceStartDate = LocalDateTime.of(2024, 2, 25, 9, 42);
    private final Service mockService = new Maintenance(
            serviceStartDate,
            
            mockBike);

    /**
     * Setzt den Provider auf einen optimalen Testzustand zurück
     */
    private void resetProvider(boolean resetData) {
        try {
            // Setze den DataProvider zurück
            var instanceField = DataProvider.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);

            // Setze einen anderen Dateipfad zum Testen
            var dataPathField = DataProvider.class.getDeclaredField("dataFilePath");
            dataPathField.setAccessible(true);
            dataPathField.set(null, "./testdata.xml");

            if (resetData) {
                // Lösche die Datei mit den Testdaten
                var testFile = new File("./testdata.xml");
                if (testFile.exists())
                    testFile.delete();
            }

            // Reinstanziere den provider
            provider = DataProvider.getInstance();
        } catch (Exception ignored) {
            fail();
        }
    }

    /**
     * Setzt den Provider auf einen optimalen Testzustand zurück
     */
    @BeforeEach
    public void resetProvider() {
        resetProvider(true);
    }

    /**
     * Initialisiere den DataProvider mit Mock-Daten
     */
    private void prepareData() {
        // Füge die Mock-Daten zum DataProvider hinzu
        provider.addBikeType(mockBikeType);
        provider.addGeoArea(mockGeoArea);
        provider.addBike(mockBike);
        provider.addStation(mockBikeStation);
        provider.addUser(mockUser);
        provider.addReservation(mockReservation);
        provider.addService(mockService);
    }

    /**
     * Dieser Test überprüft, ob das Singleton Konzept eingehalten wurde.
     */
    @Test
    public void singleton() {
        var provider = DataProvider.getInstance();
        var anotherProvider = DataProvider.getInstance();

        assertEquals(provider, anotherProvider);
    }

    /**
     * Generischer Test für die getList ohne Filter Methoden
     * @param functionToTest Zu testende Funktion
     * @param mock Zu vergleichendes MockObjekt
     * @param <T> Zu testender Datentyp
     */
    private <T> void testGetList(Supplier<List<T>> functionToTest, T mock) {
        prepareData();

        var data = functionToTest.get();

        // Es sollte sich mock Objekt in der Liste befinden
        assertEquals(1, data.size());

        // Testet, ob die Liste wirklich unveränderbar ist
        var immutable = false;
        try {
            data.removeFirst();
        } catch (UnsupportedOperationException e) {
            immutable = true;
        }
        assertTrue(immutable);

        // Der mock sollte nun in der Liste sein
        assertEquals(mock, data.getFirst());
    }

    /**
     * Generischer Test für die getList mit Filter Methoden
     * @param functionToTest Zu testende Funktion
     * @param mock Zu vergleichendes MockObjekt
     * @param <T> Zu testender Datentyp
     */
    private <T> void testGetListFilter(Function<Predicate<T>, List<T>> functionToTest, T mock) {
        prepareData();

        var data = functionToTest.apply(obj -> obj == mock);

        // Es sollte sich mock Objekt in der Liste befinden
        assertEquals(1, data.size());

        // Testet, ob die Liste wirklich unveränderbar ist
        var immutable = false;
        try {
            data.removeFirst();
        } catch (UnsupportedOperationException e) {
            immutable = true;
        }
        assertTrue(immutable);

        // Der mock sollte nun in der Liste sein
        assertEquals(mock, data.getFirst());

        // Teste anderen Filter: Die Liste sollte leer sein
        data = functionToTest.apply(user -> false);
        assertEquals(0, data.size());
    }

    /**
     * Testet die getUser Methode
     */
    @Test
    void getUser() {
        prepareData();
        assertEquals(mockUser, provider.getUser(mockUser.getEmail()));
    }

    /**
     * Testet die getUsers Methode (ohne Filter)
     */
    @Test
    void getUsers() {
        testGetList(provider::getUsers, mockUser);
    }

    /**
     * Testet die getUsers Methode (mit Filter)
     */
    @Test
    void testGetUsers() {
        testGetListFilter(provider::getUsers, mockUser);
    }


    /**
     * Testet das Entfernen eines Users
     */
    @Test
    void removeUser() {
        prepareData();
        assertTrue(provider.removeUser(mockUser));
        assertTrue(provider.getUsers().isEmpty());
    }


    /**
     * Testet das Hinzufügen eines Users
     */
    @Test
    void addUser() {
        prepareData();
        // Ein Nutzer muss eine eindeutige E-Mail haben
        assertFalse(provider.addUser(mockUser));
        assertFalse(provider.getUsers(user -> user == mockUser).isEmpty());
        // Nach dem Löschen des Nutzers solle er sich wieder hinzufügen lassen
        assertTrue(provider.removeUser(mockUser));
        assertTrue(provider.addUser(mockUser));
        assertFalse(provider.getUsers(user -> user == mockUser).isEmpty());
    }

    /**
     * Testet die getBike Methode
     */
    @Test
    void getBike() {
        prepareData();
        assertEquals(mockBike, provider.getBike(mockBike.getFrameId()));
    }

    /**
     * Testet die getBikes Methode (ohne Filter)
     */
    @Test
    void getBikes() {
        testGetList(provider::getBikes, mockBike);
    }

    /**
     * Testet die getBikes Methode (mit Filter)
     */
    @Test
    void testGetBikes() {
        testGetListFilter(provider::getBikes, mockBike);
    }


    /**
     * Testet das Entfernen eines Fahrrads
     */
    @Test
    void removeBike() {
        prepareData();
        assertTrue(provider.removeBike(mockBike));
        assertTrue(provider.getBikes().isEmpty());
    }

    /**
     * Testet das Hinzufügen eines Fahrrads
     */
    @Test
    void addBike() {
        prepareData();
        // Ein Fahrrad muss eine eindeutige Rahmennummer haben
        assertFalse(provider.addBike(mockBike));
        assertFalse(provider.getBikes(bike -> bike == mockBike).isEmpty());
        // Nach dem Löschen des Fahrrads solle es sich wieder hinzufügen lassen
        assertTrue(provider.removeBike(mockBike));
        assertTrue(provider.addBike(mockBike));
        assertFalse(provider.getBikes(bike -> bike == mockBike).isEmpty());
    }


    /**
     * Testet die getBikeTypes Methode (ohne Filter)
     */
    @Test
    void getBikeTypes() {
        testGetList(provider::getBikeTypes, mockBikeType);
    }


    /**
     * Testet die getBikeTypes Methode (mit Filter)
     */
    @Test
    void testGetBikeTypes() {
        this.<BikeType>testGetListFilter(provider::getBikeTypes, mockBikeType);
    }


    /**
     * Testet das Entfernen eines Fahrradtypen
     */
    @Test
    void removeBikeType() {
        prepareData();
        // Da ein Fahrrad des Typen existiert sollte er nicht entfernt werden können
        assertFalse(provider.removeBikeType(mockBikeType));
        // Nach dem Entfernen des Fahrrads dann schon
        assertTrue(provider.removeBike(mockBike));
        assertTrue(provider.removeBikeType(mockBikeType));
        assertTrue(provider.getBikeTypes().isEmpty());
    }

    /**
     * Testet das Hinzufügen von neuen Fahrradtypen
     */
    @Test
    void addBikeType() {
        assertTrue(provider.addBikeType(mockBikeType));
        assertFalse(provider.getBikeTypes(type -> type == mockBikeType).isEmpty());
    }


    /**
     * Testet die getReservations Methode (ohne Filter)
     */
    @Test
    void getReservations() {
        testGetList(provider::getReservations, mockReservation);
    }


    /**
     * Testet die getReservations Methode (mit Filter)
     */
    @Test
    void testGetReservations() {
        testGetListFilter(provider::getReservations, mockReservation);
    }

    /**
     * Testet das Hinzufügen neuer Reservierungen
     */
    @Test
    void addReservation() {
        // ohne zugehörigen Nutzer und Fahrrad soll das Hinzufügen fehlschlagen
        assertFalse(provider.addReservation(mockReservation));
        assertTrue(provider.getReservations(r -> r == mockReservation).isEmpty());

        assertTrue(provider.addBikeType(mockBikeType));
        assertTrue(provider.addBike(mockBike));
        assertTrue(provider.addUser(mockUser));

        assertTrue(provider.addReservation(mockReservation));
        assertFalse(provider.getReservations(r -> r == mockReservation).isEmpty());
    }


    /**
     * Testet die getStations Methode (ohne Filter)
     */
    @Test
    void getStations() {
        testGetList(provider::getStations, mockBikeStation);
    }


    /**
     * Testet die getStations Methode (mit Filter)
     */
    @Test
    void testGetStations() {
        testGetListFilter(provider::getStations, mockBikeStation);
    }


    /**
     * Testet das Entfernen einer Fahrradstation
     */
    @Test
    void removeStation() {
        prepareData();
        assertTrue(provider.removeStation(mockBikeStation));
        assertTrue(provider.getStations().isEmpty());
    }

    /**
     * Testet das Hinzufügen neuer Stationen
     */
    @Test
    void addStation() {
        assertTrue(provider.addStation(mockBikeStation));
        assertFalse(provider.getStations(s -> s == mockBikeStation).isEmpty());
    }


    /**
     * Testet die getGeoAreas Methode (ohne Filter)
     */
    @Test
    void getGeoAreas() {
        testGetList(provider::getGeoAreas, mockGeoArea);
    }


    /**
     * Testet die getGeoAreas Methode (mit Filter)
     */
    @Test
    void testGetGeoAreas() {
        testGetListFilter(provider::getGeoAreas, mockGeoArea);
    }

    /**
     * Testet das Entfernen einer GeofencingArea
     */
    @Test
    void removeGeoArea() {
        prepareData();
        assertTrue(provider.removeGeoArea(mockGeoArea));
        assertTrue(provider.getGeoAreas().isEmpty());
    }

    /**
     * Testet das Hinzufügen neuer GeofencingAreas
     */
    @Test
    void addGeoArea() {
        assertTrue(provider.addGeoArea(mockGeoArea));
        assertFalse(provider.getGeoAreas(area -> area == mockGeoArea).isEmpty());
    }


    /**
     * Testet die getServices Methode (ohne Filter)
     */
    @Test
    void getServices() {
        testGetList(provider::getServices, mockService);
    }


    /**
     * Testet die getServices Methode (mit Filter)
     */
    @Test
    void testGetServices() {
        testGetListFilter(provider::getServices, mockService);
    }

    /**
     * Testet das Hinzufügen neuer Services
     */
    @Test
    void addService() {
        // Existiert das Bike noch nicht, so wird der Service nicht hinzugefügt
        assertFalse(provider.addService(mockService));

        assertTrue(provider.addBikeType(mockBikeType));
        assertTrue(provider.addBike(mockBike));
        assertTrue(provider.addService(mockService));
        assertFalse(provider.getServices(s -> s == mockService).isEmpty());
    }

    /**
     * Testet das Speichern und Laden von Daten
     */
    @Test
    public void saveAndLoadData() {
        prepareData();
        provider.saveData();
        resetProvider(false);

        // mockService vorhanden
        assertFalse(provider.getServices(service -> service.getStartTime().isEqual(serviceStartDate)).isEmpty());
        // mockReservation vorhanden
        assertFalse(provider.getReservations(reservation -> reservation.getStartTime().isEqual(reservationStartDate)).isEmpty());
        // mockUser vorhanden
        assertFalse(provider.getUsers(user -> user.getEmail().equals(userEmail)).isEmpty());
        // mockStation vorhanden
        assertFalse(provider.getStations(station -> station.getName().equals(stationName)).isEmpty());
        // mockBike vorhanden
        assertFalse(provider.getBikes(bike -> bike.getFrameId().equals(mockBikeId)).isEmpty());
        // mockGeoArea vorhanden
        assertFalse(provider.getGeoAreas(area -> area.getEdges().getLast().getLatitude().equals(1.0)).isEmpty());
        // mockBikeType vorhanden
        assertFalse(provider.getBikeTypes(bikeType -> bikeType.getModel().equals(bikeTypeName)).isEmpty());
    }
}
