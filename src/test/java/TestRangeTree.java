import data.MinMax;
import data.RangeTree;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestRangeTree {

    @Test
    public void simpleMaxMinTest() {
        double[] values = {10, 1, 2, 3, 4, 5, 6, 7};
        RangeTree tree = new RangeTree(values);
        MinMax minMax = tree.getMinMax(0, 5);
        assertEquals(minMax.getMin(), 1.);
        assertEquals(minMax.getMax(), 10.);
    }

    @Test
    public void simpleMaxMinTest1() {
        double[] values = {10, 1, 2, 3, 4, 5, 6, 7};
        RangeTree tree = new RangeTree(values);
        MinMax minMax = tree.getMinMax(2, 5);
        assertEquals(minMax.getMin(), 2.);
        assertEquals(minMax.getMax(), 5.);
    }

}
