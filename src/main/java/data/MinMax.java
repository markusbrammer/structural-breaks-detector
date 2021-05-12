package data;

public class MinMax {

    private double min;
    private double max;

    public MinMax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public MinMax() {
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
    }

    public void merge(MinMax otherMinMax) {
        this.setMin(Math.min(this.min, otherMinMax.getMin()));
        this.setMax(Math.max(this.max, otherMinMax.getMax()));
    }

    @Override
    public String toString() {
        return "Min: " + min + ", max: " + max;
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
}
