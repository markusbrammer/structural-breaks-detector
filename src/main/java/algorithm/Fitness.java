package algorithm;

import data.TimeSeries;

import java.util.ArrayList;

public class Fitness {

    private static final double ALPHA = 0.4;

    public static double getFitness(Individual individual,
                                    TimeSeries timeSeries) {
        // Fitness function, equation (8) in the paper

        ArrayList<Integer> breakPointIndexes =
                individual.getBreakPointIndexes();

        double bigRectangleArea =
                Rectangle.getTimeSeriesGraphRectangleArea(timeSeries);
        double sum = bigRectangleArea;
        for (int i = 0; i < breakPointIndexes.size() - 1; i++) {

            int startIndex = breakPointIndexes.get(i);
            int endIndex = breakPointIndexes.get(i + 1);
            Rectangle smallRectangle = new Rectangle(startIndex, endIndex,
                    timeSeries);

            sum -= smallRectangle.getArea();
        }
        sum /= bigRectangleArea;
        sum += ALPHA * 1 / Math.sqrt(breakPointIndexes.size());
        return sum;

    }
}
