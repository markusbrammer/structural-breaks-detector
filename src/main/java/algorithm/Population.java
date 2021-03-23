package algorithm;

import data.TimeSeries;

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
            breakPointIndexes = setBreakPointIndexes(noOfBreakPoints);
            individuals[i] = new Individual(timeSeriesLength);
            for (int index : breakPointIndexes) {
                individuals[i].addBreakPoint(index);
            }


        }

    }

    private ArrayList<Integer> setBreakPointIndexes(int noOfBreakPoints) {
        /*
         *  Returns a list of noOfBreakPoints unique indexes for which to
         *  place a break point in the solution/individual string.
         */

        ArrayList<Integer> breakPointsIndexes = new ArrayList<>();
        int randomIndex;
        for (int i = 0; i < noOfBreakPoints; i++) {
            // Make sure all indexes are unique (not already in list)
            do {
                randomIndex = rand.nextInt(1000);
            } while (breakPointsIndexes.contains(randomIndex));
            breakPointsIndexes.add(randomIndex);
        }
        return breakPointsIndexes;

    }

    public Individual randomSelectIndividual(TimeSeries timeSeries) {
        // TODO make more readable - taken directly from pseudocode
        double sumOfSquaredFitnesses = getSumOfSquaredFitnesses(timeSeries);
        Random rand = new Random();
        double r = rand.nextDouble() * sumOfSquaredFitnesses;
        double h = (Fitness.getFitness(individuals[0], timeSeries));
        h *= h;
        int i = 0;
        while (h < r) {
            i += 1;
            double fitness = Fitness.getFitness(individuals[i], timeSeries);
            double squareFitness = fitness * fitness;
            h += squareFitness;
        }
        return individuals[i];
    }

    private double getSumOfSquaredFitnesses(TimeSeries timeSeries) {
        double sum = 0;
        for (Individual individual : individuals) {
            double individualFitness = Fitness.getFitness(individual,
                    timeSeries);
            sum += individualFitness * individualFitness;
        }
        return sum;
    }

    private int lowestFitness(TimeSeries timeSeries) {
        double currentLowest = 0;
        int lowestIndex = 0;
        for (int i = 0; i < noOfIndividuals; i++) {
            double fitness = Fitness.getFitness(individuals[i], timeSeries);
            if (fitness < currentLowest) {
                currentLowest = fitness;
                lowestIndex = i;
            }
        }
        return lowestIndex;
    }

    public Individual fittestIndividual(TimeSeries timeSeries) {
        double currentHighest = 0;
        int highestIndex = 0;
        for (int i = 0; i < noOfIndividuals; i++) {
            double fitness = Fitness.getFitness(individuals[i], timeSeries);
            if (fitness > currentHighest) {
                currentHighest = fitness;
                highestIndex = i;
            }
        }
        return individuals[highestIndex];
    }

    public void replaceXMin(Individual replacement, TimeSeries timeSeries) {
        int lowestFitnessIndex = lowestFitness(timeSeries);
        double fitC = (double) Fitness.getFitness(replacement, timeSeries);
        double fitX = (double) Fitness.getFitness(individuals[lowestFitnessIndex], timeSeries);
        double p = fitC / (fitC + fitX);
        double r = rand.nextDouble();
        if (r <= p) {
            individuals[lowestFitnessIndex] = replacement;
        }
    }

}
