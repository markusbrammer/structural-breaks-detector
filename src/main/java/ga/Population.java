package ga;

import data.TimeSeries;
import fitness.FitnessModel;

import java.util.*;

public class Population {

    private final Individual[] individuals;

    private final Random RAND = new Random();

    public Population(int populationSize, TimeSeries timeSeries,
                      FitnessModel fitnessModel) throws Exception {

        individuals = new Individual[populationSize];
        for (int i = 0; i < individuals.length; i++) {

            // Individual with break point at beginning and end
            int endIndex = timeSeries.getLength();
            Individual individual = new Individual(0, endIndex, fitnessModel);

            // Place noOfBPs ([1, kMax]) break points at random indexes.
            int kMax = fitnessModel.getMaxNoOfBreakPoints();
            int noOfBPs = kMax > 0 ? 1 + RAND.nextInt(kMax) : 1;
            for (int j = 0; j < noOfBPs; j++) {
                int index = 1 + RAND.nextInt(endIndex - 1);
                BreakPoint breakPoint = fitnessModel.newBreakPoint();
                individual.addBreakPoint(index, breakPoint);
            }

            // Assign fitness value to the individual
            double fitnessVal =
                    fitnessModel.calculateFitness(individual, timeSeries);
            individual.setFitness(fitnessVal);

            individuals[i] = individual;
        }

    }

    /**
     * Select a random individual from the population. Weighted with the fitness
     * of the individuals.
     * @return A random Individual in the time series
     */
    public Individual selectRandomIndividual() {

        // Calculate the total sum of squared fitness for all individuals
        double sumOfSquaredFitness = 0;
        for (Individual individual : individuals) {
            double fitnessVal = individual.getFitness();
            sumOfSquaredFitness += fitnessVal * fitnessVal;
        }

        // Extract a random individual by adding squared fitnesses until a
        // random threshold. The individual whose fitness, when added. crosses
        // the threshold is returned.
        double threshold = RAND.nextDouble() * sumOfSquaredFitness;
        double squaredFitnessCounter = 0;
        int i = -1;
        while (squaredFitnessCounter < threshold) {
            i++;
            double fitness = individuals[i].getFitness();
            squaredFitnessCounter += fitness * fitness;
        }
        return individuals[i];

    }

    /**
     * Get the fittest individual in the population.
     * @return An Individual with the highest fitness
     */
    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (Individual individual : individuals) {
            if (individual.getFitness() > fittest.getFitness())
                fittest = individual;
        }
        return fittest;
    }

    /**
     * Replace the individual with the lowest fitness in the population with a
     * new individual.
     * @param replacement The individual to replace the least fit individual
     */
    public void replaceLeastFit(Individual replacement) {
        int leastFitIndex = 0;
        double lowestFitness = individuals[0].getFitness();
        for (int i = 0; i < individuals.length; i++) {
            double fitness = individuals[i].getFitness();
            if (fitness < lowestFitness) {
                lowestFitness = fitness;
                leastFitIndex = i;
            }
        }
        individuals[leastFitIndex] = replacement;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Individual individual : individuals)
            s.append(individual).append("\n");
        return s.toString();
    }
}
