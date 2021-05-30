package fitness;

import data.MinMax;
import data.TimeSeries;
import fitness.rectangle.RectBreakPoint;
import fitness.rectangle.RectangleNode;
import ga.Allele;
import ga.BreakPoint;
import ga.Genome;
import ga.Individual;

import java.util.ArrayList;
import java.util.List;

public class RectangleFitness extends FitnessModel {

    @Override
    public double calculateFitness(Individual individual,
                                   TimeSeries timeSeries) throws Exception {

        // Calculate area of rectangle encapsulating the entire time series
        int tsLength = timeSeries.getLength();
        double timeSeriesArea = calculateArea(timeSeries, 0, tsLength - 1);

        // Calculate area of the fitness rectangles
        double rectangleAreas = 0;
        Genome genome = individual.getGenome();
        for (int g = 0; g < genome.size() - 1; g++) {
            Allele allele = genome.get(g);
            int index = genome.get(g).getIndex();
            int nextIndex = genome.get(g + 1).getIndex() - 1;
            rectangleAreas += calculateArea(timeSeries, index, nextIndex);
        }

        // Get the p(k) / penalty part. Penalty function is dependant on whether
        // or not a max. number of break points has been set.
        double alpha = this.getAlphaValue();
        int kMax = getMaxNoOfBreakPoints();
        int k = genome.size() - 2; // subtracts first and last
        double penalty;
        if (kMax > 0) {
            penalty = Math.max(0, (double) ((kMax - k + 1) / kMax));
        } else {
            penalty = 1 / Math.sqrt(k);
        }

        // Return fitness calculated from above values. Eq. (8) in the paper.
        return (timeSeriesArea - rectangleAreas) / timeSeriesArea
                + alpha * penalty;
    }

    @Override
    public BreakPoint newBreakPoint() {
        return new RectBreakPoint();
    }

    @Override
    public String getModelCode() {
        return "Rectangle";
    }

    @Override
    public List<FitnessNode> getNodes(TimeSeries timeSeries,
                                      Individual individual) {

        List<FitnessNode> nodes = new ArrayList<>();


        // Calculate area of the fitness rectangles
        double rectangleAreas = 0;
        Genome genome = individual.getGenome();
        for (int g = 0; g < genome.size() - 1; g++) {
            Allele allele = genome.get(g);
            int index = genome.get(g).getIndex();
            int nextIndex = genome.get(g + 1).getIndex() - 1;
            nodes.add(new RectangleNode(index, nextIndex, timeSeries));
        }

        return nodes;
    }


    /**
     * Calculate the area between two indexes in the time series. The indexes
     * are indexes in arrays, NOT time. The height is based on min and max in
     * the interval.
     * @param ts A time series
     * @param index0 The index to the left
     * @param index1 The index to the right
     * @return The area
     * @throws Exception The index is not valid
     */
    private double calculateArea(TimeSeries ts, int index0, int index1)
            throws Exception {

        double timeZero = ts.getTimeAtIndex(index0);
        double timeFinal = ts.getTimeAtIndex(index1);
        MinMax minMax = ts.getMinMaxInIndexInterval(index0, index1);
        return (timeFinal - timeZero) * minMax.getDifference();

    }


}
