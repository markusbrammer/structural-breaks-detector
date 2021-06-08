package bp;

import data.TimeSeries;
import fitness.FitnessModel;
import fitness.FitnessNode;
import fitness.rectangle.RectangleFitness;
import ga.Individual;
import ga.Population;

import java.util.*;

public class BreakPointAlgorithm {

    private FitnessModel fitnessModel;
    private TimeSeries timeSeries;

    // Add all fitness models to the hash map in the constructor
    private final Map<String, FitnessModel> fitnessModels = new HashMap<>();

    // Statics
    private int populationSize = InitValues.POP_SIZE;
    private int maxNoOfBreakPoints = InitValues.MAX_BP;
    private int minDistance = InitValues.MIN_DIST;
    private double alpha = InitValues.ALPHA;
    private double uniformCrossoverProb = InitValues.UNI_PROB;
    private double onePointCrossoverProb = InitValues.ONE_POINT_PROB;
    private double mutateProb = InitValues.MUTATE_PROB;
    private int iterationLimit = InitValues.ITERATIONS;

    private final Random RAND = new Random();

    public BreakPointAlgorithm() {

        // Load an instance of all fitness models and them to the hash map
        RectangleFitness rectangleFitness = new RectangleFitness();
        fitnessModels.put(rectangleFitness.getModelCode(), rectangleFitness);

        setFitnessModel(rectangleFitness.getModelCode());
    }

    public Individual findBreakPoints() throws Exception {

        // Initialize a new population of individuals
        Population population = new Population(populationSize, timeSeries,
                fitnessModel);

        // TODO The algorithm will sometimes run for a long time if a new,
        //  better solution is found often. Perhaps implement a upper ceiling
        //  of a maximum number of iterations.
        // No, the algorithm becomes slow become the solution strings have so
        // many break points that the algorithm itself becomes slow.
        // Currently solved by settings the fitness of individuals with more
        // than 100 break points to 0.

        int i = 0;
        while (i < iterationLimit) {

            // Get two different parent individuals for mutation/crossover
            Individual parent1 = population.selectRandomIndividual();
            Individual parent2 = population.selectRandomIndividual();

            if (mutateProb + uniformCrossoverProb + onePointCrossoverProb != 1)
                throw new Exception("Something is wrong with the probs");

            Individual offspring;
            double randomValue = RAND.nextDouble();
            if (randomValue < mutateProb) {
                offspring = Procedures.mutate(parent1, fitnessModel);
            } else if (randomValue < mutateProb + uniformCrossoverProb) {
                offspring = Procedures.uniformCrossover(parent1, parent2);
            } else {
                offspring = Procedures.onePointCrossover(parent1, parent2);
            }

            // Store the (in a moment) previously highest fitness
            double prevFittestFitness = population.getFittest().getFitness();

            // Calculate the fitness of the offspring and (perhaps) add it to
            // the population, replacing the least fit.
            // Reset iteration counter if offspring the new fittest
            double fitness = fitnessModel.fitnessOf(offspring, timeSeries);
            offspring.setFitness(fitness);
            double leastFitFitness = population.leastFitFitness();
            if (RAND.nextDouble() < fitness / (fitness + leastFitFitness)) {
                population.replaceLeastFit(offspring);
                if (prevFittestFitness < fitness)
                    i = 0;
            }

            i++;

        }

        Individual fittest = population.getFittest();
        List<FitnessNode> fitnessNodes = fitnessModel.getNodes(timeSeries, fittest);
        fittest.setFitnessNodes(fitnessNodes);
        return fittest;

    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
        fitnessModel.setAlphaValue(alpha);
    }

    public void setMaxNoOfBreakPoints(int maxNoOfBreakPoints) {
        this.maxNoOfBreakPoints = maxNoOfBreakPoints;
        fitnessModel.setMaxNoOfBreakPoints(maxNoOfBreakPoints);
    }

    public void setMutateProb(double mutateProb) {
        this.mutateProb = mutateProb;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setOnePointCrossoverProb(double onePointCrossoverProb) {
        this.onePointCrossoverProb = onePointCrossoverProb;
    }

    public void setUniformCrossoverProb(double uniformCrossoverProb) {
        this.uniformCrossoverProb = uniformCrossoverProb;
    }

    public void setTimeSeries(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
    }

    public void setFitnessModel(String fitnessModelCode) {
        fitnessModel = fitnessModels.get(fitnessModelCode);
        fitnessModel.setAlphaValue(alpha);
        fitnessModel.setMaxNoOfBreakPoints(maxNoOfBreakPoints);
        fitnessModel.setMinDistance(minDistance);
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
        fitnessModel.setMinDistance(minDistance);
    }

    public List<String> getFitnessModelCodes() {
        List<String> codes = new ArrayList<>(fitnessModels.size());
        // Help from here:
        // https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/
        for (Map.Entry<String, FitnessModel> set : fitnessModels.entrySet())
            codes.add(set.getKey());

        return codes;
    }

}
