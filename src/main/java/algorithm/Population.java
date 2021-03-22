package algorithm;

import java.util.ArrayList;
import java.util.Random;

public class Population {

    private int noOfIndividuals;
    private Individual[] individuals;

    private Random rand = new Random();

    public Population(int noOfIndividuals, int maxNoOfBreakPoints,
                      int timeSeriesLength) {
        /*
         * Init-method from pseudo-code.
         */

        this.noOfIndividuals = noOfIndividuals;
        this.individuals = new Individual[noOfIndividuals];

        int noOfBreakPoints;
        ArrayList<Integer> breakPointIndexes;
        for (int i = 0; i < noOfIndividuals; i++) {
            noOfBreakPoints = 1 + rand.nextInt(maxNoOfBreakPoints);
            breakPointIndexes = getBreakPointIndexes(noOfBreakPoints);
            individuals[i] = new Individual(timeSeriesLength);
            for (int index : breakPointIndexes) {
                individuals[i].addBreakPoint(index);
            }


        }

    }

    private ArrayList<Integer> getBreakPointIndexes(int noOfBreakPoints) {
        /*
         *  Returns a list of noOfBreakPoints unique indexes for which to
         *  place a break point in the solution/individual string.
         */

        ArrayList<Integer> breakPointsIndexes = new ArrayList<>();
        int randomIndex;
        for (int i = 0; i < noOfBreakPoints; i++) {
            // Make sure all indexes are unique (not already in list)
            do {
                randomIndex = rand.nextInt();
            } while (breakPointsIndexes.contains(randomIndex));
            breakPointsIndexes.add(randomIndex);
        }
        return breakPointsIndexes;

    }

    public Individual randomSelectIndividual() {

    }

    private Double sumOfSquaredFitnesses() {

    }


}
