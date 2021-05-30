import data.TimeSeries;
import fitness.FitnessModel;
import fitness.RectangleFitness;
import ga.Population;
import org.junit.Before;

public class TestInitPopulation {

    TimeSeries timeSeries;
    Population population;
    FitnessModel fitnessModel;

    final int kMax = 3;

    @Before
    public void before() {
        fitnessModel = new RectangleFitness();
        fitnessModel.setMaxNoOfBreakPoints(kMax);
    }

//    @Test
//    public void testInit() throws Exception {
//        timeSeries = new TimeSeries("src/test/resources/1Breaks_1K.json");
//        population = new Population(10, timeSeries, fitnessModel);
//        System.out.println(population);
//        boolean b = true;
//        for (Individual i : population)
//            b = b && i.getGenome().getLength() - 2 <= kMax
//                    && i.getGenome().stream().allMatch(a -> a.getIndex() < timeSeries.getLength());
//        Assert.assertTrue(b);
//    }

}
