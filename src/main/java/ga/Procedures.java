package ga;

import bp.BreakPointProcedures;
import bp.Statics;

import java.util.Random;

public class Procedures {

    // TODO: Fix interface between Individual and Procedures. Currently all methods make a lot of the same calls.
    //  Perhaps a copy method or something like that.

    private static Random rand = new Random();

    public static Individual mutate(Individual individual) {
        /**
         * Takes an individual and returns a mutated individual.
         */

        int noOfGenes = individual.getNoOfGenes();
        int noOfBreakPoints = BreakPointProcedures.noOfBreakPoints(individual);

        double mutateProb = 2. * noOfBreakPoints / noOfGenes;
        double breakPointProb = 0.6;

        Individual mutatedIndividual = new Individual(noOfGenes);
        for (int i = 0; i < noOfGenes; i++) {
            if (rand.nextDouble() < mutateProb) {
                char allele = BreakPointProcedures.mutateAllele(breakPointProb);
                mutatedIndividual.setAllele(i, allele);
            }
        }

        return mutatedIndividual;
    }

    public static Individual uniformCrossover(Individual parent1, Individual parent2) {
        /**
         * Performs a uniform crossover between two parent individuals and returns the child individual.
         *
         * Length of the individuals and the character for null-allele must be the same for both parents.
         */

        int noOfGenes = parent1.getNoOfGenes();

        Individual child = new Individual(noOfGenes);
        for (int i = 0; i < noOfGenes; i++) {
            char allele1 = parent1.getAllele(i);
            char allele2 = parent2.getAllele(i);
            char allele = (rand.nextDouble() < 0.5 ? allele1 : allele2);
            child.setAllele(i, allele);
        }

        return child;
    }

    public static Individual onePointCrossover(Individual parent1, Individual parent2) {
        /**
         * Performs one-point crossover between two parent individuals and returns the child individual.
         *
         * Length of the individuals and the character for null-allele must be the same for both parents.
         */
        int noOfGenes = parent1.getNoOfGenes();

        int crossoverIndex = rand.nextInt(noOfGenes);
        Individual child = new Individual(noOfGenes);
        for (int i = 0; i < noOfGenes; i++) {
            char allele1 = parent1.getAllele(i);
            char allele2 = parent2.getAllele(i);
            char allele = (i < crossoverIndex ? allele1 : allele2);
            child.setAllele(i, allele);
        }

        return child;
    }

}
