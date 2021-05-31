//import fitness.FitnessModel;
//import fitness.rectangle.RectangleFitness;
//import ga.Individual;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Arrays;
//
//public class TestIndividual {
//
//    Individual individual;
//    FitnessModel fitnessModel;
//
//    @Before
//    public void before() {
//        fitnessModel = new RectangleFitness();
//        individual = new Individual();
//    }
//
//    @Test
//    public void initIndividualAndAddAFewBreakPoints() {
//        int[] indexes = {3, 10, 0};
//        for (int index : indexes)
//            individual.addBreakPoint(index, fitnessModel.newBreakPoint());
//
//        Assert.assertTrue(Arrays.stream(indexes)
//                .allMatch(i -> individual.breakPointAtIndex(i)));
//    }
//
//    @Test
//    public void breakPointNotInIndividual() {
//        int[] indexes = {3, 10, 0};
//        for (int index : indexes)
//            individual.addBreakPoint(index, fitnessModel.newBreakPoint());
//
//        indexes[0] = 2;
//        Assert.assertFalse(Arrays.stream(indexes)
//                .allMatch(i -> individual.breakPointAtIndex(i)));
//    }
//
//}
