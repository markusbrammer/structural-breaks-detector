import algorithm.Individual;
import data.TimeSeries;

public class TestIndividual {

    public static void main(String[] args) {
        TimeSeries timeSeries = new TimeSeries("src/test/resources/1Breaks_1K.json");
        Individual individual = new Individual(timeSeries, 3);
        System.out.println(individual.getGenes());
    }

}
