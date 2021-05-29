import bp.BreakPointAlgorithm;
import data.TimeSeries;
import org.junit.Test;

public class TestSimpleMinMax {

    TimeSeries timeSeries;
    BreakPointAlgorithm algorithm;
    RunTimeTimer timer = new RunTimeTimer();

    @Test
    public void simpleMinMax_1kDataPoints() throws Exception {

        String filePath = "src/test/resources/1Breaks_1K.json";
        timeSeries = new TimeSeries(filePath);
        algorithm = new BreakPointAlgorithm();
        algorithm.setTimeSeries(timeSeries);

        timer.start();
        algorithm.findBreakPoints();
        timer.stop();

    }

    @Test
    public void simpleMinMax_1MDataPoints() throws Exception {
        String filePath = "src/test/resources/5Breaks_1M.json";
        timeSeries = new TimeSeries(filePath);
        algorithm = new BreakPointAlgorithm();
        algorithm.setTimeSeries(timeSeries);

        timer.start();
        algorithm.findBreakPoints();
        timer.stop();
    }

}
