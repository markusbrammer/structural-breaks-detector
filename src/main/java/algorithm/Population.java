package algorithm;

import data.TimeSeries;

import java.util.ArrayList;
import java.util.Random;

public class Population {

    private int noOfIndividuals;
    private Individual[] individuals;

    private Random rand = new Random();

    public Population(int noOfIndividuals, int maxNoOfBreakPoints,
                      TimeSeries timeSeries) {
        /*
         * Init-method from pseudo-code.
         */

        int timeSeriesLength = timeSeries.getT();
        this.noOfIndividuals = noOfIndividuals;
        this.individuals = new Individual[noOfIndividuals];
        for (int i = 0; i < noOfIndividuals; i++) {
            individuals[i] = new Individual(timeSeries, maxNoOfBreakPoints);
        }

    }


    public Individual randomSelectIndividual(TimeSeries timeSeries) {
        // TODO make more readable - taken directly from pseudocode
        double sumOfSquaredFitnesses = getSumOfSquaredFitnesses();
        Random rand = new Random();
        double r = rand.nextDouble() * sumOfSquaredFitnesses;
        double h = individuals[0].getFitness();
        h *= h;
        int i = 0;
        while (h < r) {
            i += 1;
            double fitness = individuals[i].getFitness();
            double squareFitness = fitness * fitness;
            h += squareFitness;
        }
        return individuals[i];
    }


    private double getSumOfSquaredFitnesses() {
        double sum = 0;
        for (Individual individual : individuals) {
            double individualFitness = individual.getFitness();
            sum += individualFitness * individualFitness;
        }
        return sum;
    }

    private int lowestFitness(TimeSeries timeSeries) {
        double currentLowest = 0;
        int lowestIndex = 0;
        for (int i = 0; i < noOfIndividuals; i++) {
            double fitness = individuals[i].getFitness();
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
            double fitness = individuals[i].getFitness();
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
