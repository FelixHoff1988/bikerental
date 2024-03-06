package de.wwu.sopra.reviewbusinessstatistics;

import de.wwu.sopra.entity.BikeType;
import de.wwu.sopra.entity.CargoBike;
import de.wwu.sopra.entity.EBike;
import de.wwu.sopra.entity.StandardType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Anzeige der Statistiken für die Geschäftsführung
 */
public class ReviewBusinessStatisticsGUI extends GridPane {
    private final ReviewBusinessStatisticsCTRL ctrl = new ReviewBusinessStatisticsCTRL();

    /**
     * Konstruktor: Initialisiert die Anzeige
     */
    public ReviewBusinessStatisticsGUI()
    {
        init();
    }

    /**
     * Baut die Anzeige auf
     */
    public void init()
    {
        // Eingabe der Suchparameter
        CheckBox checkBoxNormale = new CheckBox("Normales Fahrrad");
        CheckBox checkBoxLasten = new CheckBox("Lastenfahrrad");
        CheckBox checkBoxElektro = new CheckBox("Elektrofahrrad");

        checkBoxNormale.setSelected(true);

        VBox searchSettings = new VBox();
        searchSettings.setSpacing(5);
        searchSettings.setPadding(new Insets(10, 0, 0, 60));

        Slider slider = new Slider(0, 100, 0);
        slider.setMajorTickUnit(10.0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setValue(40);
        Label letzteXTage = new Label("betrachter Zeitraum: letzte 40 Tage");

        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            letzteXTage.textProperty().setValue("Betrachter Zeitraum: letzte " + newValue.intValue() + " Tage");
        });
        
        var revenueChart  = createChart("Tag", "Umsatz", "Umsätze");
        var bookingsChart = createChart("Tag", "Buchungen", "Buchungen");
        var bikeChart     = createChart("Tag", "Fahrräder", "Reparatur");

        revenueChart.setLegendVisible(false);
        bookingsChart.setLegendVisible(false);
        bikeChart.setLegendVisible(false);

        // inserting data
        var data = ctrl.calculateData(
                (int) slider.getValue(),
                checkboxToBikeTypes(
                        checkBoxNormale.isSelected(),
                        checkBoxLasten.isSelected(),
                        checkBoxElektro.isSelected()));
        revenueChart.getData().add(data.get("revenue"));
        bookingsChart.getData().add(data.get("bookings"));
        bikeChart.getData().add(data.get("bikes"));

        Button submitButton = new Button("Zeige Statistiken");
        submitButton.setOnAction(event -> {
            var calculated = ctrl.calculateData(
                    (int) slider.getValue(),
                    checkboxToBikeTypes(
                            checkBoxNormale.isSelected(),
                            checkBoxLasten.isSelected(),
                            checkBoxElektro.isSelected()));
            changeChartData(revenueChart, calculated.get("revenue"));
            changeChartData(bookingsChart, calculated.get("bookings"));
            changeChartData(bikeChart, calculated.get("bikes"));
        });

        searchSettings.getChildren().add(checkBoxNormale);
        searchSettings.getChildren().add(checkBoxLasten);
        searchSettings.getChildren().add(checkBoxElektro);
        searchSettings.getChildren().add(letzteXTage);
        searchSettings.getChildren().add(slider);
        searchSettings.getChildren().add(submitButton);
        searchSettings.setMinWidth(300);
        searchSettings.setMaxWidth(300);
        searchSettings.setAlignment(Pos.CENTER_LEFT);

        this.add(searchSettings, 0, 1);
        
        this.add(revenueChart, 1, 1);
        this.add(bookingsChart, 0, 2);
        this.add(bikeChart, 1, 2);
        this.setPadding(new Insets(50));
        this.setAlignment(Pos.CENTER);
    }
    
    private LineChart<String, Number> createChart(String xAxisName, String yAxisName, String Title)
    {
        // creating axis
        var xAxis = new CategoryAxis();
        var yAxis = new NumberAxis();
        xAxis.setLabel(xAxisName);
        yAxis.setLabel(yAxisName);
        
        //creating chart
        var lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(Title);

        return lineChart;
    }
    
    private void changeChartData(LineChart<String, Number> line, XYChart.Series<String, Number> data)
    {
        line.getData().removeAll(line.getData());
        line.getData().add(data);
    }

    private List<Class<? extends BikeType>> checkboxToBikeTypes(boolean eBike, boolean cargoBike, boolean standardBike) {
        var list = new ArrayList<Class<? extends BikeType>>();
        if (eBike)
            list.add(EBike.class);
        if (cargoBike)
            list.add(CargoBike.class);
        if (standardBike)
            list.add(StandardType.class);

        return list;
    }
}
