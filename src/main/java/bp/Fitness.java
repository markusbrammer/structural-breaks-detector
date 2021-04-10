package bp;

import data.TimeSeries;
import ga.Individual;
import ga.Population;

import java.util.ArrayList;
import java.util.List;

public class Fitness {

    public static double getFitness(Individual individual,
                                    TimeSeries timeSeries) {
        // Fitness function, equation (8) in the paper

        ArrayList<Integer> breakPointIndexes = findBreakPointIndexes(individual);
        double sum = timeSeries.getRectangleArea();

        for (int i = 0; i < breakPointIndexes.size() - 1; i++) {
            int startIndex = 1 + breakPointIndexes.get(i);
            int endIndex = breakPointIndexes.get(i + 1);
            Rectangle smallRectangle = new Rectangle(startIndex, endIndex, timeSeries);
            sum -= smallRectangle.getArea();
        }
        sum /= timeSeries.getRectangleArea();
        sum += Statics.alpha * 1 / Math.sqrt(breakPointIndexes.size() - 2);
        return sum;
    }

    public static double sumOfSquaredFitnesses(Population population, TimeSeries timeSeries) {
        double sum = 0;
        for (Individual individual : population.getIndividuals()) {
            double individualFitness = getFitness(individual, timeSeries);
            sum += (individualFitness * individualFitness);
        }
        return sum;
    }

    public static int lowestFitnessIndex(Population population, TimeSeries timeSeries) {
        double minimumFitess = getFitness(population.getIndividual(0), timeSeries);
        int lowestIndex = 0;
        for (int i = 1; i < population.getNoOfIndividuals(); i++) {
            Individual individual = population.getIndividual(i);
            double individualFitness = getFitness(individual, timeSeries);
            if (individualFitness < minimumFitess) {
                minimumFitess = individualFitness;
                lowestIndex = 1;
            }
        }
        return lowestIndex;
    }

    public static int highestFitnessIndex(Population population, TimeSeries timeSeries) {
        double topFitness = getFitness(population.getIndividual(0), timeSeries);
        int fittestIndex = 0;
        for (int i = 1; i < population.getNoOfIndividuals(); i++) {
            Individual individual = population.getIndividual(i);
            double individualFitness = getFitness(individual, timeSeries);
            if (individualFitness > topFitness) {
                topFitness = individualFitness;
                fittestIndex = 1;
            }
        }
        return fittestIndex;
    }

    private static ArrayList<Integer> findBreakPointIndexes(Individual individual) {
        ArrayList<Integer> breakPointIndexes = new ArrayList<>();
        for (int i = 0; i < individual.getNoOfGenes(); i++) {
            char allele = individual.getAllele(i);
            if (allele == Statics.breakPointAllele)
                breakPointIndexes.add(i);
        }
        return breakPointIndexes;
    }

}
