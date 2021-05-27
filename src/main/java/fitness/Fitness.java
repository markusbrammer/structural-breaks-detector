package fitness;

import data.TimeSeries;
import ga.Individual;

public abstract class Fitness {

    private TimeSeries ts;
    private double alphaValue;
    private char nonBPAllele = '*';
    private int maxNoOfBreakPoints;

    public abstract double getFitnessOfIndividual(Individual individual) throws Exception;

    protected abstract char getBPAllele();

    public void setTimeSeries(TimeSeries timeSeries) {
        this.ts = timeSeries;
    }

    public void setAlphaValue(double alphaValue) {
        this.alphaValue = alphaValue;
    }

    public double getAlphaValue() {
        return alphaValue;
    }

    protected TimeSeries getTimeSeries() {
        return ts;
    }

    protected int getMaxNoOfBreakPoints() {
        return maxNoOfBreakPoints;
    }
}
