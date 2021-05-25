import bp.BreakPointAlgorithm;
import bp.Statics;
import data.InvalidDimensionException;
import data.TimeSeries;
import ga.Individual;
import ga.Population;

public class TestInitPopulation {

    public static void main(String[] args) throws InvalidDimensionException {

        TimeSeries timeSeries =
                new TimeSeries("src/test/resources/1Breaks_1K.json");

        BreakPointAlgorithm algorithm = new BreakPointAlgorithm(timeSeries);
        Population population = algorithm.getPopulation();
        for (Individual individual : population.getIndividuals())
            printBreakPoints(individual);


    }

    private static void printBreakPoints(Individual individual) {

        String s = "";
        for (int i = 0; i < individual.getNoOfGenes(); i++) {
            char allele = individual.getAllele(i);
            if (allele == Statics.breakPointAllele)
                s += (i + " ");
        }
        System.out.println(s);

    }


}
