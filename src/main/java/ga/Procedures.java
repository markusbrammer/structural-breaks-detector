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

        Individual offspring = new Individual(noOfGenes);

        // Individuals must have break points at 0th and last index
        offspring.setAllele(0, Statics.breakPointAllele);
        offspring.setAllele(noOfGenes - 1, Statics.breakPointAllele);

        for (int i = 1; i < noOfGenes - 1; i++) {
            if (rand.nextDouble() < mutateProb) {
                char allele = BreakPointProcedures.mutateAllele(breakPointProb);
                offspring.setAllele(i, allele);
            }
        }

        return offspring;
    }

    public static Individual uniformCrossover(Individual parent1, Individual parent2) {
        /**
         * Performs a uniform crossover between two parent individuals and returns the offspring individual.
         *
         * Length of the individuals and the character for null-allele must be the same for both parents.
         */

        int noOfGenes = parent1.getNoOfGenes();

        Individual offspring = new Individual(noOfGenes);
        for (int i = 0; i < noOfGenes; i++) {
            char allele1 = parent1.getAllele(i);
            char allele2 = parent2.getAllele(i);
            char allele = (rand.nextDouble() < 0.5 ? allele1 : allele2);
            offspring.setAllele(i, allele);
        }

        return offspring;
    }

    public static Individual onePointCrossover(Individual parent1, Individual parent2) {
        /**
         * Performs one-point crossover between two parent individuals and returns the offspring individual.
         *
         * Length of the individuals and the character for null-allele must be the same for both parents.
         */
        int noOfGenes = parent1.getNoOfGenes();

        int crossoverIndex = rand.nextInt(noOfGenes);
        Individual offspring = new Individual(noOfGenes);
        for (int i = 0; i < noOfGenes; i++) {
            char allele1 = parent1.getAllele(i);
            char allele2 = parent2.getAllele(i);
            char allele = (i < crossoverIndex ? allele1 : allele2);
            offspring.setAllele(i, allele);
        }

        return offspring;
    }

}
