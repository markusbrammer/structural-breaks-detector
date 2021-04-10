import bp.BreakPointAlgorithm;
import bp.Statics;
import ga.Individual;
import ga.Population;
import data.TimeSeries;
import ga.Procedures;

import java.sql.Time;
import java.util.Random;

public class TestAlgorithm {

    public static void main(String[] args) {
        TimeSeries timeSeries = new TimeSeries("src/test/resources/1Breaks_1K.json");
        BreakPointAlgorithm algorithm = new BreakPointAlgorithm(timeSeries);
        Individual individual = algorithm.findBreakPoints();
        printBreakPointLocations(individual);
    }

    private static void printBreakPointLocations(Individual individual) {
        String s = "";
        for (int i = 0; i < individual.getNoOfGenes(); i++) {
            if (individual.getAllele(i) == Statics.breakPointAllele)
                s += (i + " ");
        }
        System.out.println(s);
    }



}
