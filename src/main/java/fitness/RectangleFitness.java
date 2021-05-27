package fitness;

import data.MinMax;
import data.TimeSeries;
import ga.Individual;

import java.util.List;

public class RectangleFitness extends Fitness {

    private char bPAllele = '!';

    @Override
    public double getFitnessOfIndividual(Individual individual) throws Exception {

        TimeSeries timeSeries = getTimeSeries();

        // Calculate area of rectangle encapsulating the entire time series
        int tsLength = timeSeries.getLength();
        double timeSeriesArea = calculateArea(timeSeries, 0, tsLength - 1);

        // Calculate area of rectangles between break points
        List<Integer> breakPointIndexes = individual.allIndexesOf(bPAllele);
        double rectangleAreas = 0;
        for (int i = 0; i < breakPointIndexes.size() - 1; i++) {
            int startIndex = 1 + breakPointIndexes.get(i);
            int lastIndex = breakPointIndexes.get(i + 1);
            double rectangleArea =
                    calculateArea(timeSeries, startIndex, lastIndex);
            rectangleAreas += rectangleArea;
        }

        // Get the p(k) / penalty part. Penalty function is dependant on whether
        // or not a max. number of break points has been set.
        double alpha = this.getAlphaValue();
        int kMax = getMaxNoOfBreakPoints();
        int k = breakPointIndexes.size() - 2; // subtracts first and last
        double penalty;
        if (kMax > 0) {
            penalty = Math.max(0, (kMax - k + 1) / kMax);
        } else {
            penalty = 1 / Math.sqrt(k);
        }

        // Return fitness calculated from above values. Eq. (8) in the paper.
        return (timeSeriesArea + rectangleAreas) / timeSeriesArea
                + alpha * penalty;
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

    @Override
    protected char getBPAllele() {
        return bPAllele;
    }


}
