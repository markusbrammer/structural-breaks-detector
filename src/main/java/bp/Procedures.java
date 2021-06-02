package bp;

import fitness.FitnessModel;
import ga.Allele;
import ga.Genome;
import ga.Individual;

import java.util.Random;

public class Procedures {

    private static double pm = 0.2;
    private static double pb = 0.2;

    private static final Random RAND = new Random();

    /**
     * Takes an individual and returns a mutated individual.
     */
    public static Individual mutate(Individual parent,
                                    FitnessModel fitnessModel) {

        double mutateProb = 0.6;
        Individual offspring = new Individual(parent);

        int mutations = 1;
        int maxIndex = parent.getEndAllele().getIndex();

        for (int m = 0; m < mutations; m++) {
            int index = 1 + RAND.nextInt(maxIndex  - 1);
            if (RAND.nextDouble() < mutateProb) {
                offspring.addBreakPoint(index, fitnessModel.newBreakPoint());
            } else if (offspring.breakPointAtIndex(index)) {
                offspring.removeBreakPoint(index);
            }
        }

        return offspring;

    }

    /**
     * Performs a uniform crossover between two parent individuals and returns the offspring individual.
     *
     * Length of the individuals and the character for null-allele must be the same for both parents.
     */
    public static Individual uniformCrossover(Individual parent1,
                                              Individual parent2) {

        Allele startAllele = parent1.getStartAllele();
        Allele endAllele = parent1.getEndAllele();
        Individual offspring = new Individual(startAllele, endAllele);

        // Add all break points from parent1 with a 50-50 chance. Do not look
        // at allele at gene 0 and gene (genome.size - 1)
        Genome genome1 = parent1.getGenome();
        Genome genome2 = parent2.getGenome();
        int g1 = 1;
        int g2 = 1;
        while (g1 < genome1.size() - 1 || g2 < genome2.size() - 1) {

            Allele a1 = genome1.get(g1);
            Allele a2 = genome2.get(g2);

            if (a1.getIndex() < a2.getIndex()) {
                if (RAND.nextDouble() < 0.5)
                    offspring.addBreakPoint(a1);
                g1++;
            } else if (a1.getIndex() > a2.getIndex()) {
                if (RAND.nextDouble() < 0.5)
                    offspring.addBreakPoint(a2);
                g2++;
            } else {
                if (RAND.nextDouble() < 0.5) {
                    offspring.addBreakPoint(a1);
                } else {
                    offspring.addBreakPoint(a2);
                }
                g1++;
                g2++;
            }

        }

        return offspring;
    }

    /**
     * Performs uniform crossover between two individuals.
     * @param parent1 An individual
     * @param parent2 Another individual
     * @return An offspring being a combination of the two parents.
     */
    public static Individual onePointCrossover(Individual parent1,
                                               Individual parent2) {

        Allele startAllele = parent1.getStartAllele();
        Allele endAllele = parent1.getEndAllele();
        int maxIndex = endAllele.getIndex();
        Individual offspring = new Individual(startAllele, endAllele);

        int index = RAND.nextInt(maxIndex);

        Genome genome1 = parent1.getGenome();
        for (int g = 1; g < genome1.size() - 1; g++) {
            Allele a = genome1.get(g);
            if (a.getIndex() < index)
                offspring.addBreakPoint(a);
        }

        Genome genome2 = parent2.getGenome();
        for (int g = 1; g < genome2.size() - 1; g++) {
            Allele a = genome2.get(g);
            if (a.getIndex() >= index)
                offspring.addBreakPoint(a);
        }

        return offspring;
    }

}
