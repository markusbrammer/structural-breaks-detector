//import ga.Individual;
//import data.TimeSeries;
//import ga.Procedures;
//
//import java.util.Random;
//
//public class TestIndividual {
//
//    private static Random rand = new Random();
//
//    public static void main(String[] args) {
//        Individual parent1 = individualWithBreakPoints(5, '*', '!');
//        System.out.println(parent1);
//        Individual parent2 = individualWithBreakPoints(5, '*', '!');
//        System.out.println(parent2);
//        Individual child1 = Procedures.mutate(parent1);
//        System.out.println(child1);
//        Individual child2 = Procedures.onePointCrossover(parent1, parent2);
//        System.out.println(child2);
//    }
//
//
//    private static Individual individualWithBreakPoints(int length, char n, char bpAllele) {
//        Individual individual = new Individual(length);
//        double breakPointProb = 0.6;
//        for (int i = 0; i < length; i++) {
//            if (rand.nextDouble() < breakPointProb)
//                individual.setAllele(i, bpAllele);
//        }
//        return individual;
//    }
//
//
//}
