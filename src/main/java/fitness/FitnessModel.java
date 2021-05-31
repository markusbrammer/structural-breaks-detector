package fitness;

import data.TimeSeries;
import ga.Individual;

import java.util.List;

public abstract class FitnessModel {

    private double alphaValue;
    private int maxNoOfBreakPoints;
    private int minDistance;
    private List<FitnessNode> nodes;

    public abstract double fitnessOf(Individual individual,
                                     TimeSeries timeSeries) throws Exception;

    public abstract BreakPoint newBreakPoint();

    public abstract String getModelCode();

    public abstract List<FitnessNode> getNodes(TimeSeries timeSeries,
                                               Individual individual);


    public void setAlphaValue(double alphaValue) {
        this.alphaValue = alphaValue;
    }

    public void setMaxNoOfBreakPoints(int maxNoOfBreakPoints) {
        this.maxNoOfBreakPoints = maxNoOfBreakPoints;
    }

    public double getAlphaValue() {
        return alphaValue;
    }

    public int getMaxNoOfBreakPoints() {
        return maxNoOfBreakPoints;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }
}
