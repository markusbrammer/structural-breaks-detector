package bp;

import data.TimeSeries;
import fitness.FitnessModel;
import fitness.FitnessNode;
import fitness.rectangle.RectangleFitness;
import ga.Individual;
import ga.Population;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class BreakPointAlgorithm {

    PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void addObserver(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private FitnessModel fitnessModel;
    private TimeSeries timeSeries;

    // Add all fitness models to the hash map in the constructor
    private final Map<String, FitnessModel> fitnessModels = new HashMap<>();

    // Statics
    private int populationSize = 50;
    private int maxNoOfBreakPoints = 3;
    private int minDistance = 450;
    private double alpha = 0.25;
    private double uniformCrossoverProb = 0.3;
    private double onePointCrossoverProb = 0.3;
    private double mutateProb = 1 - uniformCrossoverProb - onePointCrossoverProb;
    private int iterationLimit = 800;

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

        int i = 0;
        while (i < iterationLimit) {

            // Get two different parent individuals for mutation/crossover
            Individual parent1 = population.selectRandomIndividual();
            Individual parent2 = population.selectRandomIndividual();

            Individual offspring;
            double randomValue = RAND.nextDouble();
            if (randomValue < mutateProb) {
                offspring = Procedures.mutate(parent1, fitnessModel);
            } else if (randomValue < mutateProb + uniformCrossoverProb) {
                offspring = Procedures.uniformCrossover(parent1, parent2);
            } else if (randomValue < mutateProb
                    + uniformCrossoverProb + onePointCrossoverProb) {
                offspring = Procedures.onePointCrossover(timeSeries.getLength(),
                        parent1, parent2);
            } else {
                throw new Exception("The probabilities are incorrect");
            }

            double prevFittestFitness = population.getFittest().getFitness();

            double fitness = fitnessModel.fitnessOf(offspring, timeSeries);
            offspring.setFitness(fitness);

            // TODO implement probability here
            population.replaceLeastFit(offspring);

            double fittestFitness = population.getFittest().getFitness();

            if (prevFittestFitness != fittestFitness) {
                i = 0;
            } else {
                i++;
            }

        }

        Individual fittest = population.getFittest();
        List<FitnessNode> fitnessNodes = fitnessModel.getNodes(timeSeries, fittest);
        fittest.setFitnessNodes(fitnessNodes);
        return fittest;

    }

    public void setAlpha(double alpha) {
        double prevAlpha = this.alpha;
        this.alpha = alpha;
        fitnessModel.setAlphaValue(alpha);
        support.firePropertyChange("alpha", prevAlpha, alpha);
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
