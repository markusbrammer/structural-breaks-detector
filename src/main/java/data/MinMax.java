package data;

import javafx.util.Pair;

public class MinMax {

    private double min;
    private double max;

    public MinMax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public static MinMax combine(MinMax a, MinMax b) {
        double min = Math.min(a.getMin(), b.getMin());
        double max = Math.max(a.getMax(), b.getMax());
        return new MinMax(min, max);
    }

    @Override
    public String toString() {
        return "Min: " + min + ", max: " + max;
    }

    public Pair<Double, Double> toPair() {
        return new Pair<>(min, max);
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getDifference() {
        return max - min;
    }
}
