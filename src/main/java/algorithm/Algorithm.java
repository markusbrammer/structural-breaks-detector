package algorithm;

import data.TimeSeries;

import java.util.Random;

public class Algorithm {


    private TimeSeries timeSeries;
    private Population population;
    private Random rand = new Random();

    // TODO All values are currently final - change to make them non-final
    private final int NO_OF_INDIVIDUALS = 30;
    private final int NO_OF_BREAK_POINTS = 3;
    private final double puc = 0.3;
    private final double popc = 0.3;
    private final double pmu = puc - popc;

    public Algorithm(TimeSeries timeSeries, Population population) {
        this.timeSeries = timeSeries;
        this.population = population;
    }

    public Individual findBreakPoints() {

        if (population == null) {
            population = new Population(NO_OF_INDIVIDUALS, NO_OF_BREAK_POINTS,
                    timeSeries);
        }


        // TODO Implement proper stop criterium
        int i = 0;
        Individual c;
        while (i < 100) {
            Individual xi = population.randomSelectIndividual(timeSeries);
            Individual xj = population.randomSelectIndividual(timeSeries);

            double r = rand.nextDouble();
            if (r < puc) {
                c = mutate(xi);
            } else if (r < puc + popc) {
                c = onePointCrossover(xi, xj);
            } else {
                c = onePointCrossover(xi, xj);
            }
            c.calcFitness(timeSeries);
            population.replaceXMin(c, timeSeries);
            i++;
        }
        Individual yeslawd = population.fittestIndividual(timeSeries);
        System.out.println(yeslawd.getBreakPointIndexes());
        return yeslawd;

    }

    public Individual mutate(Individual xi) {
        Individual c = xi;
        char[] genes = xi.genes;
        int T = genes.length;
        int k = xi.getBreakPointIndexes().size();
        double pm = (double) 2 * k / T;
        double pb = 0.6;
        for (int i = 0; i < genes.length; i++) {
            double r = rand.nextDouble();
            if (r < pm) {
                r = rand.nextDouble();
                if (r < pb) {
                    c.addBreakPoint(i);
                } else {
                    c.removeBreakPoint(i);
                }
            }
        }
        c.calcFitness(timeSeries);
        return c;
    }

    public Individual uniformCrossover(Individual xi, Individual xj) {
        // TODO Make genes private (and all other fields) and change
        //  if-statement to be on one line
        char[] genes = xi.genes;
        Individual c = new Individual(genes.length);
        for (int i = 0; i < genes.length; i++) {
            if (rand.nextDouble() < 0.5) {
                c.genes[i] = xi.genes[i];
            } else {
                c.genes[i] = xj.genes[i];
            }
        }
        c.calcFitness(timeSeries);
        return c;
    }

    public Individual onePointCrossover(Individual xi, Individual xj) {
        char[] genes = xi.genes;
        int k = rand.nextInt(genes.length);
        Individual c = new Individual(genes.length);
        for (int i = 0; i < genes.length; i++) {
            if (i < k) {
                c.genes[i] = xi.genes[i];
            } else {
                c.genes[i] = xj.genes[i];
            }
        }
        c.calcFitness(timeSeries);
        return c;
    }

}
