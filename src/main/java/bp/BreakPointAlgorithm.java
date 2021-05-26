package bp;

import data.TimeSeries;
import fitness.FitnessCalculator;
import fitness.FitnessStringCodes;
import fitness.RectangleFitness;
import ga.Individual;
import ga.Population;
import ga.Procedures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BreakPointAlgorithm {

    private TimeSeries timeSeries;
    private Population population;

    private FitnessCalculator fitness;

    private double[] populationFitnesses;

    // Statics
    private int noOfIndividuals = 50;
    private int maxNoOfBreakPoints = 3;
    private double alpha = 0.25;
    private double uniformCrossoverProb = 0.3;
    private double onePointCrossoverProb = 0.3;
    private double mutateProb = 1 - uniformCrossoverProb - onePointCrossoverProb;

    private Random rand = new Random();

    public BreakPointAlgorithm(TimeSeries timeSeries) {
        fitness = new RectangleFitness();
        this.timeSeries = timeSeries;
        fitness.setTimeSeries(timeSeries);
    }

    public BreakPointAlgorithm() {}

    public Individual findBreakPoints() {

        fitness.setAlphaValue(alpha);
        initializePopulation();
        populationFitnesses = new double[noOfIndividuals];
        calculateFitnesses();

        for (int i = 0; i < Statics.STOP_CRITERIUM; i++) {

            // Get two different "parent" individuals for mutation/crossover
            Individual parent1 = selectRandomIndividual();
            Individual parent2 = selectRandomIndividual();
            while (parent1 == parent2)
                parent2 = selectRandomIndividual();

            Individual offspring = geneticAlgorithmProcedure(parent1, parent2);
            replaceMinimumFitness(offspring);

        }

        int fittestIndividualIndex = getMaximumFitnessIndividualIndex();
        return population.getIndividual(fittestIndividualIndex);

    }

    private void replaceMinimumFitness(Individual offspring) {

        int minimumFitnessIndex = getMinimumFitnessIndividualIndex();
        double minimumFitness = populationFitnesses[minimumFitnessIndex];

        double childFitness = fitness.getFitnessOfIndividual(offspring);

        double probabilityOfReplacement = childFitness / (childFitness + minimumFitness);
        double randomValue = rand.nextDouble();
        if (randomValue < probabilityOfReplacement) {
            population.setIndividual(minimumFitnessIndex, offspring);
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

        Individual offspring;
        double randomValue = rand.nextDouble();
        if (randomValue < mutateProb) {
            offspring = Procedures.mutate(parent1);
        } else if (randomValue < mutateProb + uniformCrossoverProb) {
            offspring = Procedures.uniformCrossover(parent1, parent2);
        } else {
            offspring = Procedures.onePointCrossover(parent1, parent2);
        }

        return offspring;


    }

    public Population getPopulation() {
        return population;
    }

    private void initializePopulation() {

        population = new Population(noOfIndividuals);

        Individual individual;
        for (int i = 0; i < noOfIndividuals; i++) {

            individual = generateIndividualWithBreakPoints();
            population.setIndividual(i, individual);

        }
    }

    private void calculateFitnesses() {
        for (int i = 0; i < noOfIndividuals; i++) {
            Individual individual = population.getIndividual(i);
            double fitness = this.fitness.getFitnessOfIndividual(individual);
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
        int noOfBreakPoints = 1 + rand.nextInt(maxNoOfBreakPoints);
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

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setMaxNoOfBreakPoints(int maxNoOfBreakPoints) {
        this.maxNoOfBreakPoints = maxNoOfBreakPoints;
    }

    public void setMutateProb(double mutateProb) {
        this.mutateProb = mutateProb;
    }

    public void setNoOfIndividuals(int noOfIndividuals) {
        this.noOfIndividuals = noOfIndividuals;
    }

    public void setOnePointCrossoverProb(double onePointCrossoverProb) {
        this.onePointCrossoverProb = onePointCrossoverProb;
    }

    public void setPopulationFitnesses(double[] populationFitnesses) {
        this.populationFitnesses = populationFitnesses;
    }

    public void setUniformCrossoverProb(double uniformCrossoverProb) {
        this.uniformCrossoverProb = uniformCrossoverProb;
    }

    public void setTimeSeries(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
        fitness.setTimeSeries(timeSeries);
    }



    public TimeSeries getTimeSeries() {
        return timeSeries;
    }

    /**
     *
     * @param fitnessCode A String code for a given fitness. See file
     *                    FitnessStringCodes.java.
     */
    public void setFitness(String fitnessCode) {
        if (FitnessStringCodes.RECTANGLE_FITNESS_CODE.equals(fitnessCode)) {
            fitness = new RectangleFitness();
        }
        fitness.setTimeSeries(timeSeries);
    }
}

//    // ONLY FOR TESTING! Remove!
//    private static void printBreakPointLocations(Individual individual) {
//        String s = "";
//        for (int i = 0; i < individual.getNoOfGenes(); i++) {
//            if (individual.getAllele(i) == Statics.breakPointAllele)
//                s += (i + " ");
//        }
//        System.out.println(s);
//    }