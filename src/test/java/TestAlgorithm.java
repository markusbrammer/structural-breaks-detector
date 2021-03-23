import algorithm.Algorithm;
import algorithm.Population;
import data.TimeSeries;

public class TestAlgorithm {

    public static void main(String[] args) {
        TimeSeries timeSeries = new TimeSeries("src/test/resources/1Breaks_1K.json");
        Population population = new Population(10, 3, timeSeries.getT());
        Algorithm algorithm = new Algorithm(timeSeries, population);
        algorithm.findBreakPoints();
    }

}
