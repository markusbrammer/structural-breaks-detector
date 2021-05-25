package fitness;

import data.TimeSeries;
import ga.Individual;

public abstract class FitnessCalculator {

    private TimeSeries timeSeries;
    private double alphaValue;
    private char nonBPAllele = '*';

    public abstract double getFitnessOfIndividual(Individual individual);

    public void setTimeSeries(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
    }

    public void setAlphaValue(double alphaValue) {
        this.alphaValue = alphaValue;
    }

    public double getAlphaValue() {
        return alphaValue;
    }

    protected TimeSeries getTimeSeries() {
        return timeSeries;
    }
}
