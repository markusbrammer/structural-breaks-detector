package fitness.rectangle;

import data.MinMax;
import data.TimeSeries;
import fitness.FitnessNode;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleNode extends FitnessNode {

    public RectangleNode(double xMin, double xMax, double yMin, double yMax) {
        setX(xMin);
        setY(yMax);
        setWidth(Math.abs(xMax - xMin));
        setHeight(Math.abs(yMax - yMin));
    }

    public RectangleNode(int bpIndex0, int bpIndex1, TimeSeries timeSeries) {

        double time0 = timeSeries.getTimeAtIndex(bpIndex0);
        double time1 = timeSeries.getTimeAtIndex(bpIndex1);
        setWidth(Math.abs(time1 - time0));
        setX(time0);

        MinMax minMax = null;
        try {
            minMax = timeSeries.getMinMaxInIndexInterval(bpIndex0, bpIndex1);
            setY(minMax.getMax());
            setHeight(Math.abs(minMax.getMax() - minMax.getMin()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public double getArea() {
        return getWidth() * getHeight();
    }

    @Override
    public Shape getVisual() {
        Rectangle rectangle = new Rectangle();
        rectangle.setId("chart-rectangle");
        return rectangle;
    }

}
