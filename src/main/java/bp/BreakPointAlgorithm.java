package bp;

import data.TimeSeries;
import ga.Individual;
import ga.Population;
import ga.Procedures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BreakPointAlgorithm {

    private TimeSeries timeSeries;
    private Population population;
    private double[] populationFitnesses;

    private Random rand = new Random();

    public BreakPointAlgorithm(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
        initializePopulation();
        populationFitnesses = new double[Statics.NO_OF_INDIVIDUALS];
        calculateFitnesses();
    }

    public Individual findBreakPoints() {

        for (int i = 0; i < Statics.STOP_CRITERIUM; i++) {

            // Get two different "parent" individuals for mutation/crossover
            Individual parent1 = selectRandomIndividual();
            Individual parent2 = selectRandomIndividual();
            while (parent1 == parent2)
                parent2 = selectRandomIndividual();

            Individual child = geneticAlgorithmProcedure(parent1, parent2);

            replaceMinimumFitness(child);

        }

        int fittestIndividualIndex = getMaximumFitnessIndividualIndex();
        return population.getIndividual(fittestIndividualIndex);

    }

    private void replaceMinimumFitness(Individual child) {

        int minimumFitnessIndex = getMinimumFitnessIndividualIndex();
        double minimumFitness = populationFitnesses[minimumFitnessIndex];

        double childFitness = Fitness.getFitness(child, timeSeries);

        double probabilityOfReplacement = childFitness / (childFitness + minimumFitness);
        double randomValue = rand.nextDouble();
        if (randomValue < probabilityOfReplacement) {
            population.setIndividual(minimumFitnessIndex, child);
            populationFitnesses[minimumFitnessIndex] = childFitness;
        }

    }

    private int getMaximumFitnessIndividualIndex() {

        int maximumFitnessIndex = 0;
        double maximumFitness = populationFitnesses[0];
        for (int i = 1; i < populationFitnesses.length; i++) {
            double fitness = populationFitnesses[i];
            if (fitness > maximumFitness) {
                maximumFitnessIndex = i;
                maximumFitness = fitness;
            }
        }
        return maximumFitnessIndex;

    }

    private int getMinimumFitnessIndividualIndex() {

        int minimumFitnessIndex = 0;
        double minimumFitness = populationFitnesses[0];
        for (int i = 1; i < populationFitnesses.length; i++) {
            double fitness = populationFitnesses[i];
            if (fitness < minimumFitness) {
                minimumFitnessIndex = i;
                minimumFitness = fitness;
            }
        }
        return minimumFitnessIndex;

    }


    private Individual geneticAlgorithmProcedure(Individual parent1, Individual parent2) {

        Individual child;
        double randomValue = rand.nextDouble();
        if (randomValue < Statics.pmu) {
            child = Procedures.mutate(parent1);
        } else if (randomValue < Statics.pmu + Statics.puc) {
            child = Procedures.uniformCrossover(parent1, parent2);
        } else {
            child = Procedures.onePointCrossover(parent1, parent2);
        }

        return child;


    }

    public Population getPopulation() {
        return population;
    }

    private void initializePopulation() {

        population = new Population(Statics.NO_OF_INDIVIDUALS);

        Individual individual;
        for (int i = 0; i < Statics.NO_OF_INDIVIDUALS; i++) {

            individual = generateIndividualWithBreakPoints();
            population.setIndividual(i, individual);

        }
    }

    private void calculateFitnesses() {

        for (int i = 0; i < Statics.NO_OF_INDIVIDUALS; i++) {

            Individual individual = population.getIndividual(i);
            double fitness = Fitness.getFitness(individual, timeSeries);
            populationFitnesses[i] = fitness;

        }

    }

    private Individual selectRandomIndividual() {

        double sumOfSquaredFitnesses = getSumOfSquaredFitnesses();

        double threshold = rand.nextDouble() * sumOfSquaredFitnesses;
        double squaredFitnessCounter = populationFitnesses[0] * populationFitnesses[0];
        int i = 0;
        while (squaredFitnessCounter < threshold) {
            i++;
            double fitness = populationFitnesses[i];
            squaredFitnessCounter += (fitness * fitness);
        }
        return population.getIndividual(i);
    }

    private double getSumOfSquaredFitnesses() {
        double sum = 0;
        for (double fitness : populationFitnesses)
            sum += (fitness * fitness);
        return sum;
    }

    private Individual generateIndividualWithBreakPoints() {

        int individualSize = timeSeries.getLength();
        Individual individual = new Individual(individualSize);

        // Fill individual with non-break-point-alleles.
        for (int i = 0; i < individualSize; i++)
            individual.setAllele(i, Statics.nullAllele);

        // Place break points at random indexes
        List<Integer> breakPointIndexes = generateBreakPointIndexes(individualSize);
        for (int index : breakPointIndexes)
            individual.setAllele(index, Statics.breakPointAllele);

        return individual;
    }

    private List<Integer> generateBreakPointIndexes(int individualSize) {

        List<Integer> breakPointIndexes = new ArrayList<>();

        // Place unique breakpoints at random locations (not first and last index)
        int noOfBreakPoints = 1 + rand.nextInt(Statics.MAX_NO_OF_BREAK_POINTS);
        while (breakPointIndexes.size() < noOfBreakPoints) {

            int randomIndex = 1 + rand.nextInt(individualSize - 2);
            if (!breakPointIndexes.contains(randomIndex))
                breakPointIndexes.add(randomIndex);

        }

        // Place breakpoints at first and last gene of individual
        breakPointIndexes.add(0);
        breakPointIndexes.add(individualSize - 1);

        return breakPointIndexes;
    }


//    public Individual findBreakPoints() {
//
//        population = initPopulation(Statics.NO_OF_INDIVIDUALS, timeSeries.getLength(), Statics.MAX_NO_OF_BREAK_POINTS);
//
//        int i = 0;
//        while (i < Statics.STOP_CRITERIUM) {
//
//            Individual individual1 = BreakPointProcedures.selectIndividualIndex(population, timeSeries);
//            Individual individual2 = BreakPointProcedures.selectIndividualIndex(population, timeSeries);
//            Individual individual;
//
//            double coinToss = rand.nextDouble();
//            if (coinToss < Statics.pmu) {
//                individual = Procedures.mutate(individual1);
//            } else if (coinToss < Statics.pmu + Statics.puc) {
//                individual = Procedures.uniformCrossover(individual1, individual2);
//            } else {
//                individual = Procedures.onePointCrossover(individual1, individual2);
//            }
//
//            int lowestIndividualIndex = Fitness.lowestFitnessIndex(population, timeSeries);
//            Individual lowestIndividual = population.getIndividual(lowestIndividualIndex);
//            double individualFitness = Fitness.getFitness(individual, timeSeries);
//            double lowestIndividualFitness = Fitness.getFitness(lowestIndividual, timeSeries);
//
//            if (rand.nextDouble() < individualFitness / (individualFitness + lowestIndividualFitness)) {
//                population.setIndividual(lowestIndividualIndex, individual);
//            }
//
//            i++;
//        }
//
//        int fittestIndex = Fitness.highestFitnessIndex(population, timeSeries);
//        return population.getIndividual(fittestIndex);
//    }
//
//    private Population initPopulation(int noOfIndividuals, int individualSize, int maxNoOfBreakPoints) {
//
//        Population population = new Population(noOfIndividuals);
//        for (int i = 0; i < noOfIndividuals; i++) {
//            int noOfBreakPoints = rand.nextInt(maxNoOfBreakPoints); // TODO understand (or fix) this line
//            Individual individual = new Individual(individualSize, Statics.nullAllele);
//            int[] breakPointIndexes = getBreakPointIndexes(noOfBreakPoints, individualSize);
//            individual.setAllele(0, Statics.breakPointAllele);
//            individual.setAllele(individualSize - 1, Statics.breakPointAllele);
//            for (int breakPointIndex : breakPointIndexes) {
//                individual.setAllele(breakPointIndex, Statics.breakPointAllele);
//            }
//            population.setIndividual(i, individual);
//        }
//        return population;
//    }
//
//    public int[] getBreakPointIndexes(int noOfBreakPoints, int individualSize) {
//        int[] indexes = new int[noOfBreakPoints];
//        int breakPointCount = 0;
//        while (breakPointCount < noOfBreakPoints) {
//            int randomIndex = rand.nextInt(individualSize);
//            if (!intArrayContains(randomIndex, indexes)) {
//                indexes[breakPointCount] = randomIndex;
//                breakPointCount++;
//            }
//
//        }
//        return indexes;
//    }

}
