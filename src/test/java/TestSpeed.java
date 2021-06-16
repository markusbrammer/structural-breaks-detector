import breakpointalgorithm.BreakPointAlgorithm;
import data.TimeSeries;
import org.junit.Before;
import org.junit.Test;

public class TestSpeed {

    TimeSeries timeSeries;
    BreakPointAlgorithm algorithm;
    RunTimeTimer timer;

    @Before
    public void init() throws Exception {
        algorithm = new BreakPointAlgorithm();
        timeSeries = new TimeSeries("src/test/resources/5Breaks_1M.json");
        algorithm.setTimeSeries(timeSeries);
        algorithm.setMaxNoOfBreakPoints(6);
        algorithm.setMinDistance(10000);
        timer = new RunTimeTimer();
    }

    @Test
    public void testWithOptimizations() throws Exception {
        timer.start();
        for (int i = 0; i < 30; i++)
            algorithm.findBreakPoints();
        timer.stop();
    }

}
