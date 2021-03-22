package algorithm;

public class Individual {

    int maxNoOfBreakPoints;
    char[] genes;
    double[] times;
    double[] values;

    public Individual(int length) {
        /*
         * Initialize an individual with 0 at all positions.
         */

        genes = new char[length];
        for (int i = 0; i < length; i++) {
            genes[i] = '0';
        }

    }

    public void addBreakPoint(int index) {
        // TODO make dependant on model
        genes[index] = 1;
    }

    public double squareFitness() {

    }




}
