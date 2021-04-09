package algorithm;

import data.TimeSeries;

import java.util.ArrayList;
import java.util.Random;

public class Individual {

    char[] genes;
    private int noOfGenes;
    private double fitness;
    private ArrayList<Integer> breakPointIndexes = new ArrayList<>();

    final char NO_BREAK_POINT_CHAR = '0';
    final char BREAK_POINT_CHAR = '1';

    public Individual(TimeSeries timeSeries, int maxNoOfBreakPoints) {
        /*
         * Initialize an individual with break points.
         */

        int length = timeSeries.getT();
        genes = new char[length];
        noOfGenes = length;
        breakPointIndexes = setBreakPointIndexes(maxNoOfBreakPoints);
        for (int i = 0; i < length; i++) {
            if (indexIsBreakPointIndex(i)) {
                genes[i] = BREAK_POINT_CHAR;
            } else {
                genes[i] = NO_BREAK_POINT_CHAR;
            }
        }
        calcFitness(timeSeries);
        System.out.println(fitness);
    }

    public Individual(int length) {
        genes = new char[length];
        noOfGenes = length;
    }

    public void addBreakPoint(int index) {
        genes[index] = BREAK_POINT_CHAR;
    }

    public char[] getGenes() {
        return genes;
    }

    public boolean indexIsBreakPointIndex(int index) {
        for (int breakPointIndex : breakPointIndexes) {
            if (index == breakPointIndex) {
                return true;
            }
        }
        return false;
    }

    public void removeBreakPoint(int i) {
        genes[i] = NO_BREAK_POINT_CHAR;
    }

    public void calcFitness(TimeSeries timeSeries) {
        ArrayList<Integer> breakPointIndexes = getBreakPointIndexes();

        double timeSeriesArea = timeSeries.getRectangleArea();
        double sum = timeSeriesArea;
        for (int i = 0; i < breakPointIndexes.size() - 1; i++) {

            int startIndex = breakPointIndexes.get(i);
            int endIndex = breakPointIndexes.get(i + 1);
            Rectangle smallRectangle = new Rectangle(startIndex, endIndex,
                    timeSeries);

            sum -= smallRectangle.getArea();
        }
        sum /= timeSeriesArea;
        sum += Statics.alpha * 1 / Math.sqrt(breakPointIndexes.size());
        this.fitness = sum;
    }

    public double getFitness() {
        return fitness;
    }


    private ArrayList<Integer> setBreakPointIndexes(int maxNoOfBreakPoints) {
        /*
         *  Returns a list of noOfBreakPoints unique indexes for which to
         *  place a break point in the solution/individual string.
         */
        Random rand = new Random();
        ArrayList<Integer> breakPointsIndexes = new ArrayList<>();
        int randomIndex;
        int noOfBreakPoints = rand.nextInt(maxNoOfBreakPoints);
        for (int i = 0; i < noOfBreakPoints; i++) {
            // Make sure all indexes are unique (not already in list)
            do {
                randomIndex = rand.nextInt(noOfGenes);
                System.out.println(randomIndex);
            } while (breakPointsIndexes.contains(randomIndex));
            breakPointsIndexes.add(randomIndex);
        }
        return breakPointsIndexes;

    }

    public ArrayList<Integer> getBreakPointIndexes() {
        return breakPointIndexes;
    }

}
