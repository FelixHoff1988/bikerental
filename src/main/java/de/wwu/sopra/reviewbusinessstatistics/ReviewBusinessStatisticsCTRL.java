package de.wwu.sopra.reviewbusinessstatistics;

import javafx.scene.chart.XYChart;

public class ReviewBusinessStatisticsCTRL {
    public XYChart.Series calculateData()
    {
        XYChart.Series data = new XYChart.Series();
        data.getData().add(new XYChart.Data( 1, 567));
        data.getData().add(new XYChart.Data( 5, 612));
        data.getData().add(new XYChart.Data(10, 800));
        data.getData().add(new XYChart.Data(20, 780));
        data.getData().add(new XYChart.Data(40, 810));
        data.getData().add(new XYChart.Data(80, 850));
        return data;
    }
}
