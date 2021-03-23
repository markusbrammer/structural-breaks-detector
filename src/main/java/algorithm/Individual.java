package algorithm;

import data.TimeSeries;

import java.util.ArrayList;
import java.util.Random;

public class Individual {

    int maxNoOfBreakPoints;
    char[] genes;
    double[] times;
    double[] values;
    private Random rand;

    final char NO_BREAK_POINT_CHAR = '0';

    public Individual(int length) {
        /*
         * Initialize an individual with no break points.
         */

        genes = new char[length];
        for (int i = 0; i < length; i++) {
            genes[i] = NO_BREAK_POINT_CHAR;
        }

    }

    public void addBreakPoint(int index) {
        // TODO make dependant on model
        genes[index] = '1';
    }

    public ArrayList<Integer> getBreakPointIndexes() {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] != NO_BREAK_POINT_CHAR)
                indexes.add(i);
        }
        return indexes;
    }



    public void removeBreakPoint(int i) {
        genes[i] = NO_BREAK_POINT_CHAR;
    }


}
