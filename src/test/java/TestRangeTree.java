import data.MinMax;
import data_structures.RangeTree;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestRangeTree {

    RunTimeTimer timer;

    @Before
    public void init() {
        timer = new RunTimeTimer();
    }

    @Test
    public void minMaxTest() {
        MinMax a = new MinMax(0, 10);
        MinMax b = new MinMax(5, 15);
        MinMax c = MinMax.combine(a, b);
        assertEquals(c.getMin(), 0.);
        assertEquals(c.getMax(), 15.);
    }

    @Test
    public void simpleMaxMinTest() throws Exception {
        double[] values = {10, 1, 2, 3, 4, 5, 6, 7};
        RangeTree tree = new RangeTree(values);
        MinMax minMax = tree.getMinMax(0, values.length - 1);
        assertEquals(minMax.getMin(), 1.);
        assertEquals(minMax.getMax(), 10.);
    }

    @Test
    public void simpleMaxMinTest1() throws Exception {
        Pair<Double, Double> test1 = new Pair<>(1., 2.);
        Pair<Double, Double> test2 = new Pair<>(1., 2.);
        assertEquals(test1, test2);
    }

    @Test
    public void signesTest() throws Exception {
        double[] signesValues = {8, 32, 1723, 858, 2372, 1217.4, 497.3, 12537, 2, -1520.9, -73};
        RangeTree tree = new RangeTree(signesValues);
        timer.start();
        MinMax minMax = tree.getMinMax(0, signesValues.length - 1);
        timer.stop();
        assertEquals(minMax.getMin(), -1520.9);
        assertEquals(minMax.getMax(), 12537.);
    }

    @Test
    public void bigArrayTest() throws Exception {
        timer.start();
        double[] values = new double[5000000];
        for (int i = 0; i < values.length; i++)
            values[i] = i;
        timer.stop();

        // RunTimeTimer timer = new RunTimeTimer();
        RangeTree tree = new RangeTree(values);
        timer.start();
        MinMax minMax = tree.getMinMax(0, 500) ;
        timer.stop();
        assertEquals(minMax.getMin(), 0.);
        assertEquals(minMax.getMax(), (double) 500);

    }



}
