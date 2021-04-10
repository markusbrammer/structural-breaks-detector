package bp;

import data.TimeSeries;
import ga.Individual;
import ga.Population;

import java.util.Random;

public class BreakPointProcedures {

    private static Random rand = new Random();

    public static char mutateAllele(double breakPointProb) {
        if (rand.nextDouble() < breakPointProb) {
            return Statics.breakPointAllele;
        } else {
            return Statics.nullAllele;
        }
    }

    public static int noOfBreakPoints(Individual individual) {
        int noOfBreakPoints = 0;
        for (int i = 0; i < individual.getNoOfGenes(); i++) {
            char allele = individual.getAllele(i);
            if (allele == Statics.breakPointAllele)
                noOfBreakPoints++;
        }
        return noOfBreakPoints;
    }



    public static Individual selectIndividualIndex(Population population, TimeSeries timeSeries) {
        double sumOfSquaredFitnesses = Fitness.sumOfSquaredFitnesses(population, timeSeries);
        double threshold = rand.nextDouble() * sumOfSquaredFitnesses;
        double currentSumOfSquaredFitnesses = 0;
        int i = 0;
        do {
            Individual individual = population.getIndividual(i);
            double individualFitness = Fitness.getFitness(individual, timeSeries);
            currentSumOfSquaredFitnesses += (individualFitness * individualFitness);
            i++;
        } while (currentSumOfSquaredFitnesses <= threshold);
        return population.getIndividual(i - 1); // TODO: Fix! Ugly solution to fence-post problem.
    }



    private static boolean intArrayContains(int value, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value)
                return true;
        }
        return false;
    }

    private static void placeBreakPoints(Individual individual, int[] breakPointIndexes) {

    }

}
