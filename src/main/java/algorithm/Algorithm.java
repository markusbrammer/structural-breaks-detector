package algorithm;

import data.TimeSeries;

public class Algorithm {

    private TimeSeries timeSeries;
    private Population population;

    // TODO All values are corrently final - change to make them non-final
    private final int NO_OF_INDIVIDUALS = 10;
    private final int NO_OF_BREAK_POINTS = 1;

    public Algorithm(TimeSeries timeSeries, Population population) {
        this.timeSeries = timeSeries;
        this.population = population;
    }

    public Individual findBreakPoints() {

        if (population == null) {
            population = new Population(NO_OF_INDIVIDUALS, NO_OF_BREAK_POINTS,
                    timeSeries.getT());
        }


        // TODO Implement proper stop criterium
        int i = 0;
        while (i < 10) {

        }

    }

}
