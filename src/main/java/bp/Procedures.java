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
    public static Individual mutate(Individual individual,
                                    FitnessModel fitnessModel) {

        Individual offspring = new Individual(individual);
        Genome genome = offspring.getGenome();

        int T = offspring.getEndAllele().getIndex();

        int noOfBreakPoints = offspring.getNoOfBreakPoints();
        double mutateProb = 2. * noOfBreakPoints / T;
        double breakPointProb = 0.6;

        for (int i = 1; i < T - 1; i++) {
            if (RAND.nextDouble() < mutateProb) {
                if (RAND.nextDouble() < breakPointProb) {
                    offspring.addBreakPoint(i, fitnessModel.newBreakPoint());
                } else if (offspring.breakPointAtIndex(i)) {
                    offspring.removeBreakPoint(i);
                }
            }
        }
        return individual;
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
        int size = Math.max(genome1.size(), genome2.size());
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
     * @param maxIndex The maximum index for the split. This is most likely the
     *                 length of the time series.
     * @param parent1 An individual
     * @param parent2 Another individual
     * @return An offspring being a combination of the two parents.
     */
    public static Individual onePointCrossover(int maxIndex, Individual parent1,
                                               Individual parent2) {

        Allele startAllele = parent1.getStartAllele();
        Allele endAllele = parent1.getEndAllele();
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
