package fitness;

public class FitnessRectangle {

    private double xMin, xMax, yMin, yMax;

    public FitnessRectangle(double xMin, double xMax, double yMin, double yMax) {

        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;

    }

    public double getArea() {
        return (xMax - xMin) * (yMax - yMin);
    }

    public double getxMax() {
        return xMax;
    }

    public double getxMin() {
        return xMin;
    }

    public double getyMax() {
        return yMax;
    }

    public double getyMin() {
        return yMin;
    }

}
