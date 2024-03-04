package de.wwu.sopra;

import de.wwu.sopra.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static de.wwu.sopra.DataProviderMock.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die DataProvider Klasse
 */
public class DataProviderTests {
    /**
     * Instanz des DataProviders
     */
    private DataProvider provider;
    
    /**
     * Setzt den Provider auf einen optimalen Testzustand zurück
     */
    @BeforeEach
    public void resetProvider() {
        this.provider = DataProviderMock.resetProvider(true);
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
        DataProviderMock.prepareData(provider);

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
        DataProviderMock.prepareData(provider);

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
        DataProviderMock.prepareData(provider);
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
        DataProviderMock.prepareData(provider);
        assertTrue(provider.removeUser(mockUser));
        assertTrue(provider.getUsers().isEmpty());
    }


    /**
     * Testet das Hinzufügen eines Users
     */
    @Test
    void addUser() {
        DataProviderMock.prepareData(provider);
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
        DataProviderMock.prepareData(provider);
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
        DataProviderMock.prepareData(provider);
        assertTrue(provider.removeBike(mockBike));
        assertTrue(provider.getBikes().isEmpty());
    }

    /**
     * Testet das Hinzufügen eines Fahrrads
     */
    @Test
    void addBike() {
        DataProviderMock.prepareData(provider);
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
        DataProviderMock.prepareData(provider);
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

        // Bike und User sollten die Reservierung noch nicht enthalten
        assertTrue(mockBike.getReservationList().isEmpty());
        assertTrue(mockUser.getReservationList().isEmpty());

        assertTrue(provider.addReservation(mockReservation));
        assertFalse(provider.getReservations(r -> r == mockReservation).isEmpty());
        assertSame(mockBike.getReservationList().getLast(), mockReservation);
        assertSame(mockUser.getReservationList().getLast(), mockReservation);
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
        DataProviderMock.prepareData(provider);
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
        DataProviderMock.prepareData(provider);
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

        // Das Bike sollte nun noch keine Services haben
        assertTrue(mockBike.getServiceList().isEmpty());

        assertTrue(provider.addService(mockService));

        // Der Service ist vorhanden
        assertFalse(provider.getServices(s -> s == mockService).isEmpty());
        // Auch im Bike
        assertSame(mockBike.getServiceList().getLast(), mockService);
    }

    /**
     * Testet das Speichern und Laden von Daten
     */
    @Test
    public void saveAndLoadData() {
        DataProviderMock.prepareData(provider);
        provider.saveData();
        this.provider = DataProviderMock.resetProvider(false);

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
