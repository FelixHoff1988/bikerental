package de.wwu.sopra.reviewbusinessstatistics;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.BikeType;
import javafx.scene.chart.XYChart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Steuerungsklasse für das Geschäftsführer GUI
 */
public class ReviewBusinessStatisticsCTRL {
    /**
     * Standardkonstruktor
     */
    public ReviewBusinessStatisticsCTRL() {}

    /**
     * Berechnet die Daten für die Diagramme
     *
     * @param slider Slider
     * @param bikeTypes Zugelassene Fahrradtypen
     * @return Diagrammdaten
     */
    public HashMap<String, XYChart.Series<String, Number>> calculateData(
            int slider,
            List<Class<? extends BikeType>> bikeTypes)
    {
        var priceData   = new XYChart.Series<String, Number>();
        var bookingData = new XYChart.Series<String, Number>();
        var bikeData    = new XYChart.Series<String, Number>();

        var filteredReservations = DataProvider.getInstance()
                .getReservations(reservation -> reservation.getBike() != null
                        && reservation.getEndTime() != null
                        && reservation.getBookingTime() != null
                        && bikeTypes.contains(reservation.getBike().getType().getClass()));

        // berechne gesuchten Wert und füge data hinzu
        for (int i = slider; i >= 0 ; i--)
        {
            var correctDay = LocalDateTime.now().minusDays(i);
            
            var reservationsOfDay = filteredReservations
                                        .stream()
                                        .filter(r -> r.getStartTime().getDayOfYear() == correctDay.getDayOfYear())
                                        .toList();
            var foundBikes = new HashSet<Bike>();

            var calculatedSumOfDay = 0F;
            var bikeCount = 0;
            for (var reservation : reservationsOfDay)
            {
                var minutes = reservation.getStartTime().until(reservation.getEndTime(), ChronoUnit.MINUTES);
                var price   = reservation.getPrice() * (minutes / 60F);
                calculatedSumOfDay += price;

                if (reservation.getBike() != null && bikeTypes.contains(reservation.getBike().getType().getClass())) {
                    var bike = reservation.getBike();
                    var bikeFound = foundBikes.contains(bike);
                    if (!bikeFound) {
                        foundBikes.add(bike);
                        bikeCount += 1;
                    }
                }
            }

            var dayString = correctDay.format(DateTimeFormatter.ofPattern("d MMM uuuu"));
            priceData.getData().add(new XYChart.Data<>(dayString, Math.abs(calculatedSumOfDay)));
            bookingData.getData().add(new XYChart.Data<>(dayString, reservationsOfDay.size()));
            bikeData.getData().add(new XYChart.Data<>(dayString, bikeCount));
        }

        var result = new HashMap<String, XYChart.Series<String, Number>>();
        result.put("revenue", priceData);
        result.put("bookings", bookingData);
        result.put("bikes", bikeData);
        return result;
    }
    
    /**
     * Prüft, ob alle Elemente einer ArrayList von Typ Boolean wahr sind
     * 
     * @param array
     * @return
     */
    private static boolean areAllTrue(ArrayList<Boolean> array) {
        for (boolean b : array)
            if (!b)
                return false;
        return true;
    }
    
    /**
     * Prüft, ob alle Elemente einer ArrayList von Typ Boolean wahr sind
     * 
     * @param array
     * @return
     */
    private static boolean oneTrue(ArrayList<Boolean> array) {
        for (boolean b : array)
            if (b)
                return true;
        return false;
    }
}
