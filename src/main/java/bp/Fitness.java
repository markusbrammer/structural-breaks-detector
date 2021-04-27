package bp;

import data.TimeSeries;
import ga.Individual;

import java.util.ArrayList;

public class Fitness {

    public static double getFitness(Individual individual,
                                    TimeSeries timeSeries) {
        // Fitness function, equation (8) in the paper


        double timeSeriesRectangleArea = getTimeSeriesRectangleArea(timeSeries);

        double sum = timeSeriesRectangleArea;

        ArrayList<Integer> breakPointIndexes = findBreakPointIndexes(individual);
        for (int i = 0; i < breakPointIndexes.size() - 1; i++) {
            int startIndex = 1 + breakPointIndexes.get(i);
            int endIndex = breakPointIndexes.get(i + 1);
            double rectangleArea = getSmallRectangleArea(startIndex, endIndex, timeSeries);
            sum -= rectangleArea;
        }
        sum /= timeSeriesRectangleArea;
        sum += Statics.alpha * 1 / Math.sqrt(breakPointIndexes.size() - 2);
        return sum;
    }

    private static double getSmallRectangleArea(int startIndex, int endIndex, TimeSeries timeSeries) {
        // TODO: Make it work with multiple dimensions

        double[] values = timeSeries.getObservations()[1];

        double[] minAndMaxValues = getMinAndMaxInInterval(values, startIndex, endIndex);
        double minValue = minAndMaxValues[0];
        double maxValue = minAndMaxValues[1];

        FitnessRectangle fitnessRectangle = new FitnessRectangle(startIndex, endIndex, minValue, maxValue);
        return fitnessRectangle.getArea();

    }

    private static double getTimeSeriesRectangleArea(TimeSeries timeSeries) {
        // TODO: Make it work with multiple dimensions

        int indexZero = 0;
        int lastIndex = timeSeries.getLength() - 1;

        double[] values = timeSeries.getObservations()[1];

        double[] minAndMaxValues = getMinAndMaxInInterval(values, indexZero, lastIndex);
        double minValue = minAndMaxValues[0];
        double maxValue = minAndMaxValues[1];

        FitnessRectangle timeSeriesFitnessRectangle = new FitnessRectangle(indexZero, lastIndex, minValue, maxValue);
        return timeSeriesFitnessRectangle.getArea();

    }

    private static ArrayList<Integer> findBreakPointIndexes(Individual individual) {
        ArrayList<Integer> breakPointIndexes = new ArrayList<>();
        for (int i = 0; i < individual.getNoOfGenes(); i++) {
            char allele = individual.getAllele(i);
            if (allele == Statics.breakPointAllele)
                breakPointIndexes.add(i);
        }
        return breakPointIndexes;
    }

    private static double[] getMinAndMaxInInterval(double[] array, int lowerBound, int upperBound) {
        double min = array[lowerBound];
        double max = array[upperBound];
        for (int i = lowerBound; i <= upperBound; i++) {
            double value = array[i];
            if (value < min) {
                min = value;
            } else if (value > max) {
                max = value;
            }
        }
        return new double[] {min, max};
    }

}
