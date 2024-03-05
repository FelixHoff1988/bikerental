package de.wwu.sopra.reviewbusinessstatistics;

import java.util.ArrayList;

import de.wwu.sopra.biketypeadministration.EditBikeTypeCTRL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
        
        Label startZeit = new Label("Zeitpunkt von");
        ChoiceBox<Integer> startZeitpunktChoiceBox = new ChoiceBox<Integer>();
        for (int i = 1; i <= 24; i++)
        {
            startZeitpunktChoiceBox.getItems().add(i);
        }
        
        Label endZeit = new Label("Zeitpunkt von");
        ChoiceBox<Integer> endZeitpunktChoiceBox = new ChoiceBox<Integer>();
        for (int i = 1; i <= 24; i++)
        {
            endZeitpunktChoiceBox.getItems().add(i);
        }
        
        LineChart umsatzChart = createChart("Tag", "Umsatz", "Umsätze der letzten Woche");
        LineChart buchungenChart = createChart("Tag", "Buchungen", "Buchungen der letzten Woche");
        LineChart fahrradChart = createChart("Tag", "Fahrräder", "Reperaturen der letzten Woche");
        
        Button submitButton = new Button("Zeige Statistiken");
        submitButton.setOnAction(event -> {
            changeChartData(umsatzChart, ctrl.calculateDataReal((int) slider.getValue(), checkBoxNormale.isSelected(), checkBoxLasten.isSelected(), checkBoxElektro.isSelected()));
        });
        
        v.getChildren().add(checkBoxNormale);
        v.getChildren().add(checkBoxLasten);
        v.getChildren().add(checkBoxElektro);         
        v.getChildren().add(letzteXTage);
        v.getChildren().add(slider);
        v.getChildren().add(submitButton);
        /*v.getChildren().add(startZeit);
        v.getChildren().add(startZeitpunktChoiceBox);
        v.getChildren().add(endZeit);
        v.getChildren().add(endZeitpunktChoiceBox);*/
        v.setMinWidth(300);
        v.setMaxWidth(300);
        
        grid.add(v, 0, 1);
        
        grid.add(umsatzChart, 1, 1);
        grid.add(buchungenChart, 1, 2);
        grid.add(fahrradChart, 1, 3);
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
    
    public void changeChartData(LineChart line, XYChart.Series data)
    {
        line.getData().removeAll(line.getData());
        line.getData().add(data);
    }
}
