package sample;

import data.TimeSeries;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class DataGraph<X, Y> extends LineChart {

    // A lot of good stuff here: https://www.codesd.com/item/how-to-add-two-vertical-lines-with-javafx-linechart.html
    // Also: https://stackoverflow.com/questions/38871202/how-to-add-shapes-on-javafx-linechart

    private ObservableList<Data<X, Y>> fitnessNodeMarkersBackup =
            FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});

    private ObservableList<Data<Data<X, Y>, Data<X,Y>>> fitnessNodeMarkers =
            FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});

    private TimeSeries timeSeries;

    private List<Shape> shapes = new ArrayList<>();

    private final int MAX_NO_OF_PLOT_POINTS = 1000;

    public DataGraph(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
        getXAxis().setAutoRanging(true);
        getYAxis().setAutoRanging(true);
        fitnessNodeMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
        fitnessNodeMarkersBackup.addListener((InvalidationListener) observable -> layoutPlotChildren());
        this.setAnimated(false);
        this.setCreateSymbols(false);
    }

    public void setTimeSeries(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
        readTimeSeriesPoints();
    }

    public void clearFitnessMarkers() {
        for (Data<Data<X, Y>, Data<X,Y>> fitnessMarker : fitnessNodeMarkers) {
            Node node = fitnessMarker.getNode();
            getPlotChildren().remove(node);
        }
        fitnessNodeMarkers.clear();
    }

    public void addRectangle(double xMin, double xMax, double yMin, double yMax) {

        Rectangle rectangle = new Rectangle();
        Data<X, Y> upperLeft = (Data<X, Y>) new Data<>(xMin, yMax);
        Data<X, Y> lowerRight = (Data<X, Y>) new Data<>(xMax, yMin);
        Data<Data<X, Y>, Data<X,Y>> marker = (Data<Data<X, Y>, Data<X,Y>>) new Data<>(upperLeft, lowerRight);

        rectangle.setId("chart-rectangle");

        marker.setNode(rectangle);
        shapes.add(rectangle);

        getPlotChildren().add(rectangle);
        fitnessNodeMarkers.add(marker);

    }

    @Override
    public void layoutPlotChildren() {

        super.layoutPlotChildren();

        for (Data<Data<X, Y>, Data<X,Y>> fitnessMarker : fitnessNodeMarkers) {
            Rectangle rectangle = (Rectangle) fitnessMarker.getNode();
            Number xMin = (Number) fitnessMarker.getXValue().getXValue();
            Number yMax = (Number) fitnessMarker.getXValue().getYValue();
            Number xMax = (Number) fitnessMarker.getYValue().getXValue();
            Number yMin = (Number) fitnessMarker.getYValue().getYValue();

            rectangle.setX(getXAxis().getDisplayPosition(xMin));
            rectangle.setY(getYAxis().getDisplayPosition(yMax));

            double width = getXAxis().getDisplayPosition(xMax) - getXAxis().getDisplayPosition(xMin);
            double height = getYAxis().getDisplayPosition(yMin) - getYAxis().getDisplayPosition(yMax);

            rectangle.setWidth(width);
            rectangle.setHeight(height);

        }

    }

    /**
     * Read all points from the time series into a XYChart object. Add time
     * series name as legend name for data points.
     *
     * Important: This can only be called, when the TimeSeries field for a
     * DataGraph is not null.
     *
     * @author Markus B. Jensen (s183816)
     */
    private void readTimeSeriesPoints() {

        assert timeSeries != null;

        XYChart.Series<Number, Number> graphPoints = new XYChart.Series<>();

        double[] timeSeriesTimes = timeSeries.getTimes();
        int noOfElementsInTimeSeries = timeSeries.getLength();

        // JavaFX' LineGraph becomes slow when showing too many points. With a
        // lot of values, the average of a number of values is plotted.
        int noOfValuesPerPoint;
        if (noOfElementsInTimeSeries > MAX_NO_OF_PLOT_POINTS) {
            noOfValuesPerPoint = noOfElementsInTimeSeries / MAX_NO_OF_PLOT_POINTS;
        } else {
            noOfValuesPerPoint = 1;
        }

        int z = 0;
        double x = 0;
        double y = 0;
        for (int i = 0; i < noOfElementsInTimeSeries; i++) {
            if (z < noOfValuesPerPoint) {
                x += timeSeriesTimes[i];
                y += timeSeries.getObservations()[1][i];
                z++;
            } else {
                x /= noOfValuesPerPoint;
                y /= noOfValuesPerPoint;
                graphPoints.getData().add(new XYChart.Data<Number, Number>(x, y));
                x = 0;
                y = 0;
                z = 0;
                i--;
            }
        }

        String timeSeriesName = timeSeries.getName();
        graphPoints.setName(timeSeriesName);

        getData().add(graphPoints);

    }

}


