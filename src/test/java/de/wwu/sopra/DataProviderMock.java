package de.wwu.sopra;

import com.sothawo.mapjfx.Coordinate;
import de.wwu.sopra.entity.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * DataProvider Mock Daten
 */
public class DataProviderMock {
    // Es folgen Mock-Daten
    private static final String bikeTypeName_nc = "Fahrrad";
    private static final StandardType mockBikeType_nc = new StandardType(bikeTypeName_nc, 52, 100);

    private static final Coordinate[] mockCoordinates_nc = new Coordinate[] {
            new Coordinate(0.0, 0.0),
            new Coordinate(1.0, 1.0),
    };
    private static final GeofencingArea mockGeoArea_nc = new GeofencingArea(new ArrayList<>(List.of(mockCoordinates_nc)));

    private static final String mockBikeId_nc = "RM001896T8";
    private static final Bike mockBike_nc = new Bike(mockBikeType_nc, mockBikeId_nc, mockCoordinates_nc[0], mockGeoArea_nc);

    private static final String stationName_nc = "Meine Station";
    private static final BikeStation mockBikeStation_nc = new BikeStation(stationName_nc, mockCoordinates_nc[1], 20);

    private static final String userEmail_nc = "günther-der-große@gmail.com";
    private static final User mockUser_nc = new User(
            "Heinz",
            "Günther",
            "Fress-Mich-Doch-Weg",
            15,
            76856,
            "Maiwöldchen",
            userEmail_nc,
            "DE87 3728 0000 1123 3947 12",
            "CIBILIDUSQUACK",
            new PasswordHashing.PasswordHash(
                    // Password: ¦'?>4g¾ì©íc¨0IfûÃßO`»)c={kAl¡Ñ`d;;ÝxgrkK#÷Ý¿M°XãËÿ¦³;Ãf~\c(huÏMú
                    "b6e8012386d585bbd9b5057b759ebc9d",
                    "2ac4fcabf13bf1cb57ab450f553e747bb9fc2f0c9f709645bac62b27c0e66371496764a5638dc84524a8d47c388b2c690016c02c55a7a7f03c146bd6d2a4b17f"),
            UserRole.MAINTAINER);

    private static final LocalDateTime reservationStartDate_nc = LocalDateTime.of(2024, 2, 24, 10, 42);
    private static final Reservation mockReservation_nc = new Reservation(
            reservationStartDate_nc,
            mockUser_nc,
            mockBike_nc);

    private static final LocalDateTime serviceStartDate_nc = LocalDateTime.of(2024, 2, 25, 9, 42);
    private static final Service mockService_nc = new Maintenance(
            serviceStartDate_nc,
            mockBike_nc);

    public static String bikeTypeName;
    public static StandardType mockBikeType;
    public static Coordinate[] mockCoordinates;
    public static GeofencingArea mockGeoArea;
    public static String mockBikeId;
    public static Bike mockBike;
    public static String stationName;
    public static BikeStation mockBikeStation;
    public static String userEmail;
    public static User mockUser;
    public static LocalDateTime reservationStartDate;
    public static Reservation mockReservation;
    public static LocalDateTime serviceStartDate;
    public static Service mockService;

    /**
     * Setze alle statischen Mock-Daten zurück.
     */
    private static void resetStatics() {
        bikeTypeName = mockBikeType_nc.getModel();
        mockBikeType = new StandardType(
                bikeTypeName,
                mockBikeType_nc.getSize(),
                mockBikeType_nc.getPrice());
        mockCoordinates = new Coordinate[] {
                mockCoordinates_nc[0],
                mockCoordinates_nc[1],
        };
        mockGeoArea = new GeofencingArea(new ArrayList<>(Arrays.stream(mockCoordinates).toList()));
        mockBikeId = mockBike_nc.getFrameId();
        mockBike = new Bike(mockBikeType, mockBikeId, mockCoordinates[0], mockGeoArea);
        stationName = mockBikeStation_nc.getName();
        mockBikeStation = new BikeStation(stationName, mockCoordinates[1], mockBikeStation_nc.getCapacity());
        userEmail = mockUser_nc.getEmail();
        mockUser = new User(
                mockUser_nc.getFirstName(),
                mockUser_nc.getLastName(),
                mockUser_nc.getStreet(),
                mockUser_nc.getHouseNumber(),
                mockUser_nc.getPostalCode(),
                mockUser_nc.getCity(),
                userEmail,
                mockUser_nc.getIban(),
                mockUser_nc.getBic(),
                new PasswordHashing.PasswordHash(
                        mockUser_nc.getPasswordHash().salt,
                        mockUser_nc.getPasswordHash().hash),
                mockUser_nc.getRole());
        reservationStartDate = mockReservation_nc.getStartTime();
        mockReservation = new Reservation(reservationStartDate, mockUser, mockBike);
        serviceStartDate = mockService_nc.getStartTime();
        mockService = new Maintenance(serviceStartDate, mockBike);
    }

    /**
     * Setzt den Provider auf einen optimalen Testzustand zurück
     */
    public static DataProvider resetProvider(boolean resetData) {
        resetStatics();

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
            return DataProvider.getInstance();
        } catch (Exception ignored) {
            fail();
        }

        return null;
    }

    /**
     * Initialisiere den DataProvider mit Mock-Daten
     */
    public static void prepareData(DataProvider provider) {
        // Füge die Mock-Daten zum DataProvider hinzu
        provider.addBikeType(mockBikeType);
        provider.addGeoArea(mockGeoArea);
        provider.addBike(mockBike);
        provider.addStation(mockBikeStation);
        provider.addUser(mockUser);
        provider.addReservation(mockReservation);
        provider.addService(mockService);
    }
}
