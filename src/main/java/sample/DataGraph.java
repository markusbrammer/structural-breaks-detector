package sample;

        import data.MinMax;
        import data.TimeSeries;
import fitness.FitnessNode;
import ga.Individual;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
        import javafx.scene.chart.NumberAxis;
        import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * A lot of good stuff here:
 *  - https://www.codesd.com/item/how-to-add-two-vertical-lines-with-javafx-linechart.html
 *  - https://stackoverflow.com/questions/38871202/how-to-add-shapes-on-javafx-linechart
 */
public class DataGraph extends LineChart<Number, Number> {

    private List<Data<Number, Number>> fitnessNodes = new ArrayList<>();
    private TimeSeries timeSeries;
    private final int MAX_NO_OF_PLOT_POINTS = 1000;
    private XYChart.Series<Number, Number> graphPoints = new Series<>();

    public static final String[] DISPLAY_MODES = {"Average", "Min and Max"};
    private String displayMode = DISPLAY_MODES[0];
    AnchorPane anchorPane = new AnchorPane();


    public DataGraph(Axis<Number> xAxis, Axis<Number> yAxis) {
        super(xAxis, yAxis);
        getXAxis().setAutoRanging(false);
        getYAxis().setAutoRanging(false);
        ((NumberAxis) getXAxis()).setForceZeroInRange(false);
        ((NumberAxis) getYAxis()).setForceZeroInRange(false);
        this.setAnimated(false);
        this.setCreateSymbols(false);
        getPlotChildren().add(anchorPane);
    }

    public void setTimeSeries(TimeSeries timeSeries) throws Exception {
        this.timeSeries = timeSeries;
        readTimeSeriesPoints();
    }

    public void drawFitness(Individual individual) throws Exception {

        // Clear previous fitness markers
        fitnessNodes.forEach(data -> getPlotChildren().remove(data.getNode()));
        fitnessNodes.clear();
        anchorPane.getChildren().clear();

        List<FitnessNode> fitnessNodeList = individual.getFitnessNodes();
        if (fitnessNodeList.size() > 20)
            throw new TooManyBreakPointsException("Too many break points detected. " +
                    "Tweak parameters to get fewer break points");

        // Add all the fitness break point markers to "plot children" list and
        // fitnessNodes list
        for (FitnessNode node : fitnessNodeList) {
            Data<Number, Number> marker = new Data<>(node.getX(), node.getY());
            Rectangle rectangle = (Rectangle) node.getVisual();
            marker.setNode(rectangle);
            marker.setExtraValue(node);
            // getPlotChildren().add(rectangle);
            fitnessNodes.add(marker);
        }

        // Show the fitness break point markers
        layoutPlotChildren();
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
    private void readTimeSeriesPoints() throws Exception {

        System.out.println("Hello");
        getData().clear();
        assert timeSeries != null;

        graphPoints = new XYChart.Series<>();

        double[] timeSeriesTimes = timeSeries.getTimes();
        double[] values = timeSeries.getObservations();
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
                y += values[i];
                z++;
            } else {
                x /= noOfValuesPerPoint;
                y /= noOfValuesPerPoint;
                graphPoints.getData().add(new XYChart.Data<>(x, y));
                x = 0;
                y = 0;
                z = 0;
                i--;
            }
        }

        MinMax xMinMax = new MinMax(timeSeriesTimes[0], timeSeriesTimes[timeSeriesTimes.length - 1]);
        ((NumberAxis) getXAxis()).setLowerBound(xMinMax.getMin() - xMinMax.getDifference() / 20.);
        ((NumberAxis) getXAxis()).setUpperBound(xMinMax.getMax() + xMinMax.getDifference() / 20.);
        ((NumberAxis) getXAxis()).setTickUnit(xMinMax.getDifference() / 5);

        MinMax minMax = timeSeries.getMinMaxInIndexInterval(0, timeSeriesTimes.length - 1);
        ((NumberAxis) getYAxis()).setLowerBound(minMax.getMin() - minMax.getDifference() / 20.);
        ((NumberAxis) getYAxis()).setUpperBound(minMax.getMax() + minMax.getDifference() / 20.);
        ((NumberAxis) getYAxis()).setTickUnit(minMax.getDifference() / 10.);


        String timeSeriesName = timeSeries.getName();
        graphPoints.setName(timeSeriesName);

        getData().add(graphPoints);

    }

    @Override
    public void layoutPlotChildren() {

        super.layoutPlotChildren();
        anchorPane.toFront();

        if (!graphPoints.getData().isEmpty()) {
            ObservableList<Data<Number, Number>> points = graphPoints.getData();

            double xMin = (double) points.get(0).getXValue();
            double xMax = (double) points.get(points.size() - 1).getXValue();
            double xMinScreen = getXAxis().getDisplayPosition(xMin);
            double xMaxScreen = getXAxis().getDisplayPosition(xMax);
            double xScale = Math.abs((xMax - xMin) / (xMaxScreen - xMinScreen));

            double yMin = (double) points.get(0).getYValue();
            double yMax = yMin;
            for (Data<Number, Number> data : points) {
                double yVal = (double) data.getYValue();
                if (yVal < yMin) {
                    yMin = yVal;
                } else if (yVal > yMax) {
                    yMax = yVal;
                }
            }
            double yMinScreen = getYAxis().getDisplayPosition(yMin);
            double yMaxScreen = getYAxis().getDisplayPosition(yMax);
            double yScale = Math.abs((yMax - yMin) / (yMaxScreen - yMinScreen));

            for (Data<Number, Number> data : fitnessNodes) {

                Rectangle node = (Rectangle) data.getNode();
                FitnessNode fitnessNode = (FitnessNode) data.getExtraValue();

                double height = fitnessNode.getHeight() / yScale;
                double width = fitnessNode.getWidth() / xScale;

                node.setX(getXAxis().getDisplayPosition(fitnessNode.getX()));
                node.setY(getYAxis().getDisplayPosition(fitnessNode.getY()));
                node.setHeight(height);
                node.setWidth(width);

                AnchorPane.setLeftAnchor(node, node.getX());
                AnchorPane.setTopAnchor(node, node.getY());
                if (!anchorPane.getChildren().contains(node))
                    anchorPane.getChildren().add(node);

            }
        }

    }

    public void setDisplayMode(String mode) throws Exception {
        if (!displayMode.equals(mode)) {
            displayMode = mode;
            if (timeSeries != null)
                readTimeSeriesPoints();
        }
    }
}

