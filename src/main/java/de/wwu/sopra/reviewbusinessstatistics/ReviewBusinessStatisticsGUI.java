package de.wwu.sopra.reviewbusinessstatistics;

import de.wwu.sopra.biketypeadministration.EditBikeTypeCTRL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReviewBusinessStatisticsGUI extends HBox{
    private ReviewBusinessStatisticsCTRL ctrl = new ReviewBusinessStatisticsCTRL();
    
    public ReviewBusinessStatisticsGUI()
    {
        init();
    }
    
    public void init()
    {
        GridPane grid = new GridPane();
        Label header = new Label("Geschäftsstatistiken");
        
        // Eingabe der Suchparameter
        grid.add(header, 0, 0);
        CheckBox checkBoxNormale = new CheckBox("Normales Fahrrad");
        CheckBox checkBoxLasten = new CheckBox("Lastenfahrrad");
        CheckBox checkBoxElektro = new CheckBox("Elektrofahrrad");
        VBox v = new VBox();
        v.getChildren().add(checkBoxNormale);
        v.getChildren().add(checkBoxLasten);
        v.getChildren().add(checkBoxElektro);
        Slider slider = new Slider(0, 100, 0);
        slider.setMajorTickUnit(10.0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        Label letzteXTage = new Label("betrachter Zeitraum: letzte 0 Tage");
        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
               ObservableValue<? extends Number> observableValue, 
               Number oldValue, 
               Number newValue) { 
                  letzteXTage.textProperty().setValue(
                          "betrachter Zeitraum: letzte " + String.valueOf(newValue.intValue())  + " Tage");
              }
        });
                  
        v.getChildren().add(letzteXTage);
        v.getChildren().add(slider);
        grid.add(v, 0, 1);
        
        grid.add(createChart("Tag", "Umsatz", "Umsätze der letzten Woche"), 1, 1);
        grid.add(createChart("Tag", "Buchungen", "Buchungen der letzten Woche"), 1, 2);
        grid.add(createChart("Tag", "Fahrräder", "Reperaturen der letzten Woche"), 1, 3);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(grid);
    }
    
    public LineChart createChart(String xAxisName, String yAxisName, String Title)
    {
        // creating axis
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xAxisName);
        yAxis.setLabel(yAxisName);
        
        //creating chart
        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle(Title);
        
        // inserting data
        lineChart.getData().add(ctrl.calculateData());
        return lineChart;
    }
}
