import bp.BreakPointAlgorithm;
import data.TimeSeries;
import ga.Individual;
import org.junit.Before;
import org.junit.Test;

public class TestAlgorithm {

    TimeSeries timeSeries;
    BreakPointAlgorithm algorithm;

    @Before
    public void before() {
        algorithm = new BreakPointAlgorithm();
    }

    @Test
    public void test() throws Exception {
        timeSeries = new TimeSeries("src/test/resources/1Breaks_1K.json");
        algorithm.setTimeSeries(timeSeries);
        algorithm.setMaxNoOfBreakPoints(2);
        algorithm.setAlpha(0.35);
        Individual individual = algorithm.findBreakPoints();
        System.out.println(individual);
    }



}
