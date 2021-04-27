package sample;

import data.TimeSeries;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataGraph<X, Y> extends LineChart {

    // A lot of good stuff here: https://www.codesd.com/item/how-to-add-two-vertical-lines-with-javafx-linechart.html
    // Also: https://stackoverflow.com/questions/38871202/how-to-add-shapes-on-javafx-linechart

    private ObservableList<Data<X, Y>> fitnessNodeMarkersBackup =
            FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});

    private ObservableList<Data<Data<X, Y>, Data<X,Y>>> fitnessNodeMarkers =
            FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});

    private TimeSeries timeSeries;

    private List<Shape> shapes = new ArrayList<>();

    public DataGraph(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
        fitnessNodeMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
        fitnessNodeMarkersBackup.addListener((InvalidationListener) observable -> layoutPlotChildren());
    }

    public void setTimeSeries(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
        readTimeSeriesPoints();
    }

    public void addRectangle(double xMin, double xMax, double yMin, double yMax) {


        Rectangle rectangle = new Rectangle();
        Data<X, Y> upperLeft = (Data<X, Y>) new Data<>(xMin, yMax);
        Data<X, Y> lowerRight = (Data<X, Y>) new Data<>(xMax, yMin);
        Data<Data<X, Y>, Data<X,Y>> marker = (Data<Data<X, Y>, Data<X,Y>>) new Data<>(upperLeft, lowerRight);

        marker.setNode(rectangle);
        shapes.add(rectangle);

        getPlotChildren().add(rectangle);
        fitnessNodeMarkers.add(marker);

    }

    public void addVerticalRangeMarker(Data<X, Y> marker) {
        Objects.requireNonNull(marker, "the marker must not be null");
        if (fitnessNodeMarkersBackup.contains(marker)) return;

        Rectangle rectangle = new Rectangle(0,0,0,0);
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.2));

        marker.setNode( rectangle);

        getPlotChildren().add(rectangle);
        fitnessNodeMarkersBackup.add(marker);
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

//        for (int i = 0; i < fitnessNodeMarkers.size(); i++) {
//            Data<X, Y> marker = fitnessNodeMarkers.get(i);
//            Rectangle rectangle = (Rectangle) marker.getNode();
//            Rectangle rectangle1 = (Rectangle) shapes.get(i);
//
//            double width =
//                    getXAxis().getDisplayPosition(rectangle1.getX() + rectangle1.getWidth()) - getXAxis().getDisplayPosition(rectangle1.getX());
//
//            System.out.println(getXAxis().getDisplayPosition(100));
//
//            rectangle.setWidth(width);
//            rectangle.setHeight(500);
//            rectangle.setX(getXAxis().getDisplayPosition(marker.getXValue()));
//            rectangle.setY(getYAxis().getDisplayPosition(marker.getYValue()));
//
//        }

        for (Data<X, Y> verticalRangeMarker : fitnessNodeMarkersBackup) {

            Rectangle rectangle = (Rectangle) verticalRangeMarker.getNode();
            rectangle.setX( getXAxis().getDisplayPosition(verticalRangeMarker.getXValue()) + 0.5);  // 0.5 for crispness
            rectangle.setWidth( getXAxis().getDisplayPosition(verticalRangeMarker.getYValue()) - getXAxis().getDisplayPosition(verticalRangeMarker.getXValue()));
            rectangle.setY(0d);
            rectangle.setHeight(getBoundsInLocal().getHeight());
            rectangle.toBack();

        }

    }

    private void readTimeSeriesPoints() {
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

        XYChart.Series<Number, Number> graphPoints = new XYChart.Series<>();

        double[] timeSeriesTimes = timeSeries.getTimes();
        int noOfElementsInTimeSeries = timeSeries.getLength();
        for (int i = 0; i < noOfElementsInTimeSeries; i++) {
            double x = timeSeriesTimes[i];
            double y = timeSeries.getObservations()[1][i];
            graphPoints.getData().add(new XYChart.Data<Number, Number>(x, y));
        }

        String timeSeriesName = timeSeries.getName();
        graphPoints.setName(timeSeriesName);

        getData().add(graphPoints);

    }

}


