package de.wwu.sopra;

import com.sothawo.mapjfx.Coordinate;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import de.wwu.sopra.entity.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Verwaltet das Laden und Speichern der Anwendungsdaten per XStream.
 * Eine Instanz kann per getInstance() geholt werden (Singleton Entwurfsmuster).
 */
public class DataProvider {
    /**
     * Hilfsklasse zur Serialisierung der Daten per XStream
     */
    private static class AppData {
        /**
         * Liste aller verwalteten Fahrräder
         */
        @XStreamAlias("bikes")
        ArrayList<Bike> bikes = new ArrayList<>();
        /**
         * Liste aller verwalteten Fahrradtypen
         */
        @XStreamAlias("bikeTypes")
        ArrayList<BikeType> bikeTypes = new ArrayList<>();
        /**
         * Liste aller verwalteten Reservierungen
         */
        @XStreamAlias("reservations")
        ArrayList<Reservation> reservations = new ArrayList<>();
        /**
         * Liste aller verwalteten Fahrradstationen
         */
        @XStreamAlias("stations")
        ArrayList<BikeStation> stations = new ArrayList<>();
        /**
         * Liste aller verwalteten GeofencingAreas
         */
        @XStreamAlias("geoAreas")
        ArrayList<GeofencingArea> geoAreas = new ArrayList<>();
        /**
         * Liste aller verwalteten Wartungen/Reparaturen
         */
        @XStreamAlias("services")
        ArrayList<Service> services = new ArrayList<>();
        /**
         * Liste aller verwalteten Nutzer
         */
        @XStreamAlias("users")
        ArrayList<User> users = new ArrayList<>();
    }

    /**
     * Aktuelle instanz des DataProviders
     */
    private static DataProvider instance;
    /**
     * Dateipfad zu der Datei, in welcher Anwendungsdaten gespeichert werden.
     */
    private static String dataFilePath = "./appdata.xml";

    /**
     * Aktuelle Anwendungsdaten
     */
    private final AppData appData;
    /**
     * XStream-Objekt zur (De)Serialisierung der Daten.
     */
    private final XStream xStream;

    /**
     * Privater Konstruktor, welcher das XStream Objekt initialisiert, dessen Sicherheitsoptionen konfiguriert sowie
     * abschließend die Anwendungsdaten lädt.
     */
    private DataProvider() {
        this.xStream = new XStream();
        this.xStream.allowTypes(new Class[]{
                Bike.class,
                Reservation.class,
                BikeStation.class,
                GeofencingArea.class,
                Service.class,
                Maintenance.class,
                Repair.class,
                User.class,
                Coordinate.class,
                AppData.class,
                StandardType.class,
                EBike.class,
                CargoBike.class,
                PasswordHashing.PasswordHash.class,
        });

        this.appData = loadData();
    }

    /**
     * Gibt eine Instanz des DataProviders zurück.
     *
     * @return Instanz des DataProviders
     */
    public static synchronized DataProvider getInstance() {
        if (DataProvider.instance == null)
            DataProvider.instance = new DataProvider();

        return DataProvider.instance;
    }

    /**
     * Lädt den gespeicherten Anwendungszustand aus der Datei. Fall noch keine Datei vorliegt, werden alle
     * Datenlisten leer initialisiert.
     *
     * @return Geladene AppData
     */
    private AppData loadData() {
        var dataFile = new File(dataFilePath);

        // Wenn keine Datei mit bereits vorhandenen Daten existiert gebe default Werte zurück
        if (!dataFile.exists())
            return new AppData();

        // Sonst deserialize die gegebenen Daten
        return (AppData) xStream.fromXML(new File(dataFilePath));
    }

    /**
     * Speichert die Anwendungsdaten in eine Xml Datei.
     */
    public void saveData() {
        try {
            var writer = new OutputStreamWriter(new FileOutputStream(dataFilePath), StandardCharsets.UTF_8);
            xStream.toXML(this.appData, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("Fatal exception: Not able to save app data.");
            e.printStackTrace();
        }
    }

    /**
     * Sucht nach einem Nutzer mit der angegebenen E-Mail-Adresse und gibt diesen zurück.
     * Sollte kein solcher Nutzer vorhanden sein, wird null zurückgegeben.
     *
     * @param email E-Mail des gesuchten Nutzers
     * @return Erster Nutzer mit der E-Mail-Adresse oder null
     */
    public User getUser(String email) {
        var match = this.appData.users
                .stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .toArray(User[]::new);

        return match.length > 0 ? match[0] : null;
    }

    /**
     * Gibt eine unveränderbare Liste aller Nutzer zurück.
     *
     * @return Liste aller Nutzer
     */
    public List<User> getUsers() {
        return Collections.unmodifiableList(this.appData.users);
    }

    /**
     * Gibt eine unveränderbare Liste aller Nutzer zurück.
     * Diese kann nach beliebigen Kriterien gefiltert werden.
     *
     * @param filter Filterfunktion (user -> "Boolean Value")
     * @return Liste gesuchter Nutzer
     */
    public List<User> getUsers(Predicate<User> filter) {
        return this.appData.users.stream().filter(filter).toList();
    }

    /**
     * Entfernt einen Nutzer aus den Anwendungsdaten.
     * Außerdem wird der Nutzer von zugehörigen Reservierungen und Wartungseinträgen entfernt.
     *
     * @param user Zu entfernender Nutzer
     * @return true, falls der Nutzer entfernt werden konnte.
     */
    public boolean removeUser(User user) {
        var removed = this.appData.users.remove(user);
        if (!removed)
            return false;

        // entferne Nutzer von allen zugehörigen Reservierungen
        this.appData.reservations
                .stream()
                .filter(reservation -> reservation.getUser() == user)
                .forEach(reservation -> reservation.setUser(null));
        // entferne den Nutzer von allen zugehörigen Wartungen
        this.appData.services
                .stream()
                .filter(service -> service.getUser() == user)
                .forEach(service -> service.setUser(null));

        return true;
    }

    /**
     * Fügt einen neuen Nutzer den Anwendungsdaten hinzu.
     * Eine E-Mail-Adresse darf nur einem Nutzer zugeordnet werden.
     *
     * @param user Hinzuzufügender Nutzer
     * @return true, wenn der Nutzer erfolgreich hinzugefügt wurde.
     */
    public boolean addUser(User user) {
        if (this.appData.users.stream().anyMatch(otherUser -> Objects.equals(otherUser.getEmail(), user.getEmail())))
            return false;

        return this.appData.users.add(user);
    }

    /**
     * Sucht ein Fahrrad mit der gegebenen Rahmennummer. Sollte dies nicht existieren, wird null zurückgegeben.
     *
     * @param frameId Rahmennummer des Fahrrads
     * @return Erstes Fahrrad mit gesuchter Rahmennummer oder null
     */
    public Bike getBike(String frameId) {
        var match = this.appData.bikes
                .stream()
                .filter(bike -> Objects.equals(bike.getFrameId(), frameId))
                .toArray(Bike[]::new);

        return match.length > 0 ? match[0] : null;
    }

    /**
     * Gibt eine unveränderbare Liste aller Fahrräder zurück.
     *
     * @return Liste aller Fahrräder
     */
    public List<Bike> getBikes() {
        return Collections.unmodifiableList(this.appData.bikes);
    }

    /**
     * Gibt eine unveränderbare Liste aller Fahrräder zurück.
     * Diese kann nach beliebigen Kriterien gefiltert werden.
     *
     * @param filter Filterfunktion (bike -> "Boolean Value")
     * @return Liste gesuchter Fahrräder
     */
    public List<Bike> getBikes(Predicate<Bike> filter) {
        return this.appData.bikes.stream().filter(filter).toList();
    }

    /**
     * Entfernt ein Fahrrad aus den Anwendungsdaten.
     * Außerdem wird das Fahrrad von zugehörigen Reservierungen und Wartungseinträgen entfernt.
     *
     * @param bike Zu entfernendes Fahrrad
     * @return true, falls das Fahrrad entfernt werden konnte.
     */
    public boolean removeBike(Bike bike) {
        var removed = this.appData.bikes.remove(bike);
        if (!removed)
            return false;

        // Entferne das Fahrrad von zugehörigen Wartungen
        this.appData.services
                .stream()
                .filter(service -> service.getBike() == bike)
                .forEach(service -> service.setBike(null));
        // Entferne das Fahrrad von zugehörigen Reservierungen
        this.appData.reservations
                .stream()
                .filter(reservation -> reservation.getBike() == bike)
                .forEach(reservation -> reservation.setBike(null));

        return true;
    }

    /**
     * Fügt ein neues Fahrrad den Anwendungsdaten hinzu.
     * Eine Rahmennummer darf nur einem Fahrrad zugeordnet werden.
     * Ein Fahrrad kann nur hinzugefügt werden, wenn der BikeType bereits existiert.
     *
     * @param bike Hinzuzufügendes Fahrrad
     * @return true, wenn das Fahrrad erfolgreich hinzugefügt wurde.
     */
    public boolean addBike(Bike bike) {
        if (this.appData.bikes.stream().anyMatch(otherBike -> Objects.equals(otherBike.getFrameId(), bike.getFrameId())))
            return false;
        if (this.appData.bikeTypes.stream().noneMatch(type -> type == bike.getType()))
            return false;

        return this.appData.bikes.add(bike);
    }

    /**
     * Gibt eine unveränderbare Liste aller Fahrradtypen zurück.
     *
     * @return Liste aller Nutzer
     */
    public List<BikeType> getBikeTypes() {
        return Collections.unmodifiableList(this.appData.bikeTypes);
    }

    /**
     * Gibt eine unveränderbare Liste aller Fahrradtypen zurück.
     * Diese kann nach beliebigen Kriterien gefiltert werden.
     *
     * @param filter Filterfunktion (bikeType -> "Boolean Value")
     * @return Liste gesuchter Nutzer
     */
    public List<BikeType> getBikeTypes(Predicate<BikeType> filter) {
        return this.appData.bikeTypes.stream().filter(filter).toList();
    }

    /**
     * Entfernt einen Fahrradtyp aus den Anwendungsdaten.
     * Ein Fahrradtyp kann nicht gelöscht werden, wenn noch Fahrräder des Typs existieren.
     *
     * @param bikeType Zu entfernender Fahrradtyp
     * @return true, falls der Fahrradtyp entfernt werden konnte.
     */
    public boolean removeBikeType(BikeType bikeType) {
        // wenn noch Fahrräder mit dem Typen existieren, ist ein Löschen nicht möglich
        if (this.appData.bikes.stream().anyMatch(bike -> bike.getType() == bikeType))
            return false;

        return this.appData.bikeTypes.remove(bikeType);
    }

    /**
     * Fügt einen neuen Fahrradtyp den Anwendungsdaten hinzu.
     *
     * @param bikeType Hinzuzufügender Fahrradtyp
     * @return true, wenn der Fahrradtyp erfolgreich hinzugefügt wurde.
     */
    public boolean addBikeType(BikeType bikeType) {
        return this.appData.bikeTypes.add(bikeType);
    }

    /**
     * Gibt eine unveränderbare Liste aller Reservierungen zurück.
     *
     * @return Liste aller Reservierungen
     */
    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(this.appData.reservations);
    }

    /**
     * Gibt eine unveränderbare Liste aller Reservierungen zurück.
     * Diese kann nach beliebigen Kriterien gefiltert werden.
     *
     * @param filter Filterfunktion (reservation -> "Boolean Value")
     * @return Liste gesuchter Reservierungen
     */
    public List<Reservation> getReservations(Predicate<Reservation> filter) {
        return this.appData.reservations.stream().filter(filter).toList();
    }

    /**
     * Fügt eine neue Reservierung den Anwendungsdaten hinzu.
     * Eine Reservierung kann nur erstellt werden, wenn der zugehörige Nutzer und das Fahrrad bereits existieren.
     *
     * @param reservation Hinzuzufügende Reservierung
     * @return true, wenn die Reservierung erfolgreich hinzugefügt wurde.
     */
    public boolean addReservation(Reservation reservation) {
        if (this.appData.bikes.stream().noneMatch(bike -> bike == reservation.getBike())
            || this.appData.users.stream().noneMatch(user -> user == reservation.getUser()))
            return false;

        return this.appData.reservations.add(reservation);
    }

    /**
     * Gibt eine unveränderbare Liste aller Fahrradstationen zurück.
     *
     * @return Liste aller Fahrradstationen
     */
    public List<BikeStation> getStations() {
        return Collections.unmodifiableList(this.appData.stations);
    }

    /**
     * Gibt eine unveränderbare Liste aller Fahrradstationen zurück.
     * Diese kann nach beliebigen Kriterien gefiltert werden.
     *
     * @param filter Filterfunktion (station -> "Boolean Value")
     * @return Liste gesuchter Fahrradstationen
     */
    public List<BikeStation> getStations(Predicate<BikeStation> filter) {
        return this.appData.stations.stream().filter(filter).toList();
    }

    /**
     * Entfernt eine Fahrradstation aus den Anwendungsdaten.
     *
     * @param station Zu entfernende Fahrradstation
     * @return true, falls die Fahrradstation entfernt werden konnte.
     */
    public boolean removeStation(BikeStation station) {
        return this.appData.stations.remove(station);
    }

    /**
     * Fügt eine neue Fahrradstation den Anwendungsdaten hinzu.
     *
     * @param station Hinzuzufügende Fahrradstation
     * @return true, wenn die Fahrradstation erfolgreich hinzugefügt wurde.
     */
    public boolean addStation(BikeStation station) {
        return this.appData.stations.add(station);
    }

    /**
     * Gibt eine unveränderbare Liste aller GeofencingAreas zurück.
     *
     * @return Liste aller GeofencingAreas
     */
    public List<GeofencingArea> getGeoAreas() {
        return Collections.unmodifiableList(this.appData.geoAreas);
    }

    /**
     * Gibt eine unveränderbare Liste aller GeofencingAreas zurück.
     * Diese kann nach beliebigen Kriterien gefiltert werden.
     *
     * @param filter Filterfunktion (geoArea -> "Boolean Value")
     * @return Liste gesuchter GeofencingAreas
     */
    public List<GeofencingArea> getGeoAreas(Predicate<GeofencingArea> filter) {
        return this.appData.geoAreas.stream().filter(filter).toList();
    }

    /**
     * Entfernt eine GeofencingArea aus den Anwendungsdaten.
     *
     * @param geoArea Zu entfernende GeofencingArea
     * @return true, falls die GeofencingArea entfernt werden konnte.
     */
    public boolean removeGeoArea(GeofencingArea geoArea) {
        // entferne die GeoArea von zugehörigen Fahrrädern
        this.appData.bikes
                .stream()
                .filter(bike -> bike.getCurrentArea() == geoArea)
                .forEach(bike -> bike.setCurrentArea(null));

        return this.appData.geoAreas.remove(geoArea);
    }

    /**
     * Fügt eine neue GeofencingArea den Anwendungsdaten hinzu.
     *
     * @param geoArea Hinzuzufügende GeofencingArea
     * @return true, wenn die GeofencingArea erfolgreich hinzugefügt wurde.
     */
    public boolean addGeoArea(GeofencingArea geoArea) {
        return this.appData.geoAreas.add(geoArea);
    }

    /**
     * Gibt eine unveränderbare Liste aller Reparaturen/Wartungen zurück.
     *
     * @return Liste aller Reparaturen/Wartungen
     */
    public List<Service> getServices() {
        return Collections.unmodifiableList(this.appData.services);
    }

    /**
     * Gibt eine unveränderbare Liste aller Reparaturen/Wartungen zurück.
     * Diese kann nach beliebigen Kriterien gefiltert werden.
     *
     * @param filter Filterfunktion (service -> "Boolean Value")
     * @return Liste gesuchter Reparaturen/Wartungen
     */
    public List<Service> getServices(Predicate<Service> filter) {
        return this.appData.services.stream().filter(filter).toList();
    }

    /**
     * Fügt eine neue Reparatur/Wartung den Anwendungsdaten hinzu.
     * Ein Service kann nur hinzugefügt werden, wenn das zugehörige Fahrrad bereits existiert.
     *
     * @param service Hinzuzufügende Reparatur/Wartung
     * @return true, wenn die Reparatur/Wartung erfolgreich hinzugefügt wurde.
     */
    public boolean addService(Service service) {
        if (this.appData.bikes.stream().noneMatch(bike -> bike == service.getBike()))
            return false;

        return this.appData.services.add(service);
    }
}
