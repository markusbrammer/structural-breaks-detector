package bp;

import ga.Individual;

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



}
