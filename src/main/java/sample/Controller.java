package sample;

import data.TimeSeries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

public class Controller {

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addObserver(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private File dataFile;

    @FXML
    void buttonOnAction(ActionEvent event) {
        System.out.println("Hello World");
    }

    public void openFileChooser(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select time series data file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        File tempDataFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (tempDataFile != null) {
            dataFile = tempDataFile;
            support.firePropertyChange("dataFile", null, dataFile);
            displayTimeSeries();
        }
    }

    public String getDataFilePath() {
        return dataFile.getAbsolutePath();
    }

    private void displayTimeSeries() {

        // How to find nodes (in this case the line chart with fx:id "tsgraph"
        // ((LineChart<Double, Double>) Main.getPrimaryStage().getScene().lookup("#tsgraph"))

        TimeSeries timeSeries = new TimeSeries(dataFile.getAbsolutePath());
        XYChart.Series<Double, Double> coordinates = readTimeSeriesPoints(timeSeries);

        ((LineChart<Double, Double>) Main.getPrimaryStage().getScene().lookup("#tsgraph")).getData().add(coordinates);

    }

    private XYChart.Series<Double, Double> readTimeSeriesPoints(TimeSeries timeSeries) {
        // TODO: From DataGraph.java, temporary fix
        /*
         * Read all points from the time series into a XYChart object. Add time
         * series name as legend name for data points.
         *
         * Important: This can only be called, when the TimeSeries field for a
         * DataGraph is not null.
         *
         * Made by: Markus B. Jensen (s183816)
         */

        XYChart.Series<Double, Double> graphPoints = new XYChart.Series<>();

        double[] timeSeriesTimes = timeSeries.getTimes();
        int noOfElementsInTimeSeries = timeSeries.getLength();
        for (int i = 0; i < noOfElementsInTimeSeries; i++) {
            double x = timeSeriesTimes[i];
            double y = timeSeries.getObservations()[1][i];
            graphPoints.getData().add(new XYChart.Data<Double,Double>(x, y));
        }

        String timeSeriesName = timeSeries.getName();
        graphPoints.setName(timeSeriesName);
        return graphPoints;

    }
}
