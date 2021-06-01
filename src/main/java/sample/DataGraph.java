package sample;

        import data.MinMax;
import data.TimeSeries;
import fitness.FitnessNode;
import ga.Individual;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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

    private final String DISPLAY_AVERAGE = "Average";
    private final String DISPLAY_MINMAX = "Min and Max";
    public final String[] DISPLAY_MODES = {DISPLAY_AVERAGE, DISPLAY_MINMAX};
    private String displayMode;
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
        displayMode = DISPLAY_MODES[0];
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
        if (fitnessNodeList.size() > 60)
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
     */
    private void readTimeSeriesPoints() throws Exception {

        int maxNoOfPoints = 1000;

        // Clear previous fitness markers
        fitnessNodes.forEach(data -> getPlotChildren().remove(data.getNode()));
        fitnessNodes.clear();
        anchorPane.getChildren().clear();

        getData().clear();

        // Get time series parameters and arrays
        double[] times = timeSeries.getTimes();
        double[] values = timeSeries.getObservations();
        int length = timeSeries.getLength();

        // Determine number of time series points per point on chart
        int valuesPerPoint = (int) Math.ceil(length / (double) maxNoOfPoints);

        if (valuesPerPoint > 1 && displayMode.equals(DISPLAY_AVERAGE)) {
            int i = 0;
            Series<Number, Number> points = new Series<>();
            points.setName(timeSeries.getName() + " (average)");
            while (i < times.length - valuesPerPoint) {
                int next = Math.min(i + valuesPerPoint, times.length - 1);
                double sum = 0;
                for (int j = i; j < next; j++) {
                    sum += values[j];
                }
                double average = sum / valuesPerPoint;
                double time = times[i];
                points.getData().add(new Data<>(time, average));
                i += valuesPerPoint;
            }
            getData().add(points);
        } else if (valuesPerPoint > 1 && displayMode.equals(DISPLAY_MINMAX)) {
            int i = 0;
            Series<Number, Number> minSeries = new Series<>();
            minSeries.setName(timeSeries.getName() + " (min)");
            Series<Number, Number> maxSeries = new Series<>();
            maxSeries.setName(timeSeries.getName() + " (max)");
            while (i < times.length - valuesPerPoint) {

                int next = Math.min(i + valuesPerPoint, times.length - 1);
                MinMax minMax = timeSeries.getMinMax(i, next);

                double time = times[i];
                minSeries.getData().add(new Data<>(time, minMax.getMin()));
                maxSeries.getData().add(new Data<>(time, minMax.getMax()));

                i += valuesPerPoint;
            }
            getData().add(minSeries);
            getData().add(maxSeries);
        } else {
            Series<Number, Number> points = new Series<>();
            points.setName(timeSeries.getName());
            for (int i = 0; i < times.length; i++) {
                points.getData().add(new Data<>(times[i], values[i]));
            }
            getData().add(points);
        }

        MinMax xMinMax = new MinMax(times[0], times[times.length - 1]);
        setTickUnit((NumberAxis) getXAxis(), xMinMax);


        MinMax minMax = timeSeries.getMinMax();
        setTickUnit((NumberAxis) getYAxis(), minMax);

        this.updateLegend();

    }

    private void setTickUnit(NumberAxis axis, MinMax minMax) {

        double min = minMax.getMin();
        double max = minMax.getMax();
        double range = Math.abs(minMax.getDifference());

        double factor = Math.floor(Math.log10(range));
        double tens = Math.pow(10, factor);
        double multiplier = range / tens;

        double tickUnit = multiplier < 4 ? tens / 4 : tens / 2;
        double lowerBound = Math.floor(min / tickUnit) * tickUnit;
        double upperBound = Math.ceil(max / tickUnit) * tickUnit;

        axis.setTickUnit(tickUnit);
        axis.setLowerBound(lowerBound);
        axis.setUpperBound(upperBound);

    }

    @Override
    public void layoutPlotChildren() {

        super.layoutPlotChildren();
        anchorPane.toFront();

        if (timeSeries != null) {

            double xMin = timeSeries.getTimeAtIndex(0);
            double xMax = timeSeries.getTimeAtIndex(timeSeries.getLength() - 1);
            double xMinScreen = getXAxis().getDisplayPosition(xMin);
            double xMaxScreen = getXAxis().getDisplayPosition(xMax);
            double xScale = Math.abs((xMax - xMin) / (xMaxScreen - xMinScreen));

            MinMax minMax = timeSeries.getMinMax();
            double yMinScreen = getYAxis().getDisplayPosition(minMax.getMin());
            double yMaxScreen = getYAxis().getDisplayPosition(minMax.getMax());
            double yScale = Math.abs((minMax.getDifference()) /
                    (yMaxScreen - yMinScreen));

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

    public String[] getDisplayModeList() {
        return DISPLAY_MODES;
    }
}

