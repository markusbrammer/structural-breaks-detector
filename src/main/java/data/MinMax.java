package data;

/**
 * A simple object for storing minimum and maximum values
 */
public class MinMax {

    private double min;
    private double max;

    public MinMax(double min, double max) {
        this.min = min;
        this.max = max;
        assert min <= max : "MinMax assertion";
    }

    /**
     * Get the combined minimum and maximum from two MinMax objects
     * @param a MinMax object
     * @param b Another MinMax Object
     * @return A MinMax object. A combination of the two.
     */
    public static MinMax combine(MinMax a, MinMax b) {
        double min = Math.min(a.getMin(), b.getMin());
        double max = Math.max(a.getMax(), b.getMax());
        return new MinMax(min, max);
    }

    @Override
    public String toString() {
        return "Min: " + min + ", max: " + max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getDifference() {
        return max - min;
    }
}
