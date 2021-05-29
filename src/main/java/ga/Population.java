package ga;

import data.TimeSeries;
import fitness.FitnessModel;

import java.util.*;

public class Population implements Iterable<Individual> {

    private final PriorityQueue<Individual> individuals = new PriorityQueue<>();

    private final Random RAND = new Random();

    public Population(int populationSize, TimeSeries timeSeries,
                      FitnessModel fitnessModel) throws Exception {

        while (size() < populationSize) {

            Individual individual = new Individual();

            // Place a break point at index 0 and last index of time series.
            individual.addBreakPoint(0, fitnessModel.newBreakPoint());
            individual.addBreakPoint(timeSeries.getLength() - 1,
                    fitnessModel.newBreakPoint());

            // Place noOfBPs ([1, kMax]) break points at random, unique indexes.
            int kMax = fitnessModel.getMaxNoOfBreakPoints();
            int noOfBPs = kMax > 0 ? 1 + RAND.nextInt(kMax) : 1;
            int placedBPs = 0;
            while (placedBPs < noOfBPs) {
                int index = 1 + RAND.nextInt(timeSeries.getLength() - 1);
                if (!individual.breakPointAtIndex(index)) {
                    individual.addBreakPoint(index, fitnessModel.newBreakPoint());
                    placedBPs++;
                }
            }

            double fitnessVal = fitnessModel.calculateFitness(individual, timeSeries);
            individual.setFitness(fitnessVal);
            add(individual);
        }

    }

    public Individual selectRandomIndividual() throws Exception {

        double sumOfSquaredFitness = 0;
        for (Individual individual : individuals) {
            double fitnessVal = individual.getFitness();
            sumOfSquaredFitness += fitnessVal * fitnessVal;
        }


        double threshold = RAND.nextDouble() * sumOfSquaredFitness;
        double squaredFitnessCounter = 0;
        for (Individual individual : this) {
            double fitness = individual.getFitness();
            squaredFitnessCounter += fitness * fitness;
            if (squaredFitnessCounter >= threshold)
                return individual;
        }
        throw new Exception("Something went wrong in Select");
    }

    public Individual getFittest() {
        // It is not possible to iterate over PriorityQueue in sorted order.
        Individual fittest = individuals.peek();
        for (Individual i : this) {
            assert fittest != null;
            if (fittest.getFitness() < i.getFitness())
                fittest = i;
        }
        return fittest;
    }

    public void replaceLeastFit(Individual replacement) {
        Individual leastFit = individuals.peek();
        individuals.remove(leastFit);
        individuals.add(replacement);
    }

    public void add(Individual individual) {
        individuals.add(individual);
    }

    public int size() {
        return individuals.size();
    }

    @Override
    public Iterator<Individual> iterator() {
        return individuals.iterator();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        this.forEach(i -> s.append(i).append("\n"));
        return s.toString();
    }
}
