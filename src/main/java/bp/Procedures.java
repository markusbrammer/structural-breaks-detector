package bp;

import fitness.FitnessModel;
import ga.Allele;
import ga.Individual;

import java.util.Random;

public class Procedures {

    private static double pm = 0.2;
    private static double pb = 0.2;

    private static final Random RAND = new Random();

    /**
     * Takes an individual and returns a mutated individual.
     */
    public static Individual mutate(Individual individual, double mutateProb,
                                    double breakPointProb,
                                    FitnessModel fitnessModel) {

        Individual offspring = new Individual(individual);


        int maxIndex = individual.getGenome().getTailElement().getIndex();
        for (int i = 1; i < maxIndex - 1; i++) {
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

        Individual offspring = new Individual();

        int headIndex = parent1.getGenome().getHeadElement().getIndex();
        int tailIndex = parent1.getGenome().getTailElement().getIndex();

        // Add all break points from parent1 with a 50-50 chance
        for (Allele a : parent1.getGenome()) {
            if (RAND.nextDouble() < 0.5 || a.getIndex().equals(headIndex)
                || a.getIndex().equals(tailIndex))
                offspring.addBreakPoint(a);
        }

        // Add all break point from parent2 with a 50-50 chance. If the two
        // parents share a break point index, choose parent1's
        // TODO change to pick random between shared break point indexes
        for (Allele a : parent2.getGenome()) {
            if (RAND.nextDouble() < 0.5) {
                if (!offspring.breakPointAtIndex(a.getIndex()))
                    offspring.addBreakPoint(a);
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

        Individual offspring = new Individual();

        int index = RAND.nextInt(maxIndex);
        parent1.getGenome().stream()
                .filter(allele -> allele.getIndex() < index)
                .forEach(offspring::addBreakPoint);
        parent2.getGenome().stream()
                .filter(allele -> allele.getIndex() >= index)
                .forEach(offspring::addBreakPoint);

        return offspring;
    }

}
