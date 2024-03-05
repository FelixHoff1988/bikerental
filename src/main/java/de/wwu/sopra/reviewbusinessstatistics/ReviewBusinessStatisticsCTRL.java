package de.wwu.sopra.reviewbusinessstatistics;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.entity.BikeType;
import de.wwu.sopra.entity.Reservation;
import javafx.scene.chart.XYChart;

public class ReviewBusinessStatisticsCTRL {
    public XYChart.Series calculateData()
    {
        XYChart.Series<Number, Number> data = new XYChart.Series<Number, Number>();
        data.getData().add(new XYChart.Data( 1, 567));
        data.getData().add(new XYChart.Data( 5, 612));
        data.getData().add(new XYChart.Data(10, 800));
        data.getData().add(new XYChart.Data(20, 780));
        data.getData().add(new XYChart.Data(40, 810));
        data.getData().add(new XYChart.Data(80, 850));
        return data;
    }
    
    public XYChart.Series calculateDataReal(int slider, boolean normal, boolean lasten, boolean elektro)
    {
        XYChart.Series<Number, Number> data = new XYChart.Series<Number, Number>();
        List<Reservation> unfilteredReservations = DataProvider.getInstance().getReservations();
        System.out.println("Unfiltered Bikes");
        for (Reservation element: unfilteredReservations)
        {
            System.out.println(element.getBike().getType().getTypeString());
            System.out.println(element.getPrice());
        }
        
        List<Reservation> filteredReservations = DataProvider.getInstance().getReservations(Reservation -> {
            ArrayList<Boolean> pruefungenTypeBike = new ArrayList<Boolean>();
            if (normal)
            {
                pruefungenTypeBike.add(Reservation.getBike().getType().getTypeString().equals("Standart"));
            }
            if (lasten)
            {
                pruefungenTypeBike.add(Reservation.getBike().getType().getTypeString().equals("CargoBike"));
            }
            if (elektro)
            {
                pruefungenTypeBike.add(Reservation.getBike().getType().getTypeString().equals("EBike"));
            }
            
            ArrayList<Boolean> allePruefungen = new ArrayList<Boolean>();
            // check selected Bike Types
            allePruefungen.add(oneTrue(pruefungenTypeBike));
            
            //check when started
            /*LocalDateTime now = LocalDateTime.now();
            Period goBack = Period.ofDays(slider);
            LocalDateTime firstDate = now.minus(goBack);
            
            allePruefungen.add(firstDate.isBefore(Reservation.getStartTime()));*/
            
            // to be implemented: check for time 
            return areAllTrue(allePruefungen);
            
        });
        
        System.out.println("Filtered Bikes");
        for (Reservation element: filteredReservations)
        {
            System.out.println(element.getBike().getType().getTypeString());
            System.out.println(element.getPrice());
            System.out.println(element.getStartTime());
        }
        
        // berechne gesuchten Wert und füge data hinzu
        for (int i = slider; i >= 0 ; i--)
        {
            LocalDateTime now = LocalDateTime.now();
            Period goBack = Period.ofDays(i);
            LocalDateTime correctDay = now.minus(goBack);
            System.out.println(correctDay);
            
            Stream<Reservation> reservationsOfDayStream = filteredReservations.stream().filter(r -> r.getStartTime().getDayOfYear() == correctDay.getDayOfYear());
            List<Reservation> reservationsOfDay = reservationsOfDayStream.toList();
            
            int calculatedSumOfDay = 0;
            System.out.println(i);
            for (Reservation element: reservationsOfDay)
            {
                System.out.println(element.getBike().getType().getTypeString());
                calculatedSumOfDay = calculatedSumOfDay + element.getPrice();
            }
            System.out.println(calculatedSumOfDay);
            
            data.getData().add(new XYChart.Data(slider - i, calculatedSumOfDay));
        }
        return data;
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
