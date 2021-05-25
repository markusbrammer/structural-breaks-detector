package sample;

import bp.BreakPointAlgorithm;
import bp.Statics;
import data.InvalidDimensionException;
import data.TimeSeries;
import ga.Individual;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AlgorithmObserver implements PropertyChangeListener {

    private BreakPointAlgorithm algorithm;
    private TimeSeries timeSeries;

    public AlgorithmObserver(BreakPointAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

        // Values get updates from controller
        Controller controller = (Controller) event.getSource();
        String propertyName = event.getPropertyName();
        if (propertyName.equals("runAlgorithm")) {
            try {
                updateValues(controller);
            } catch (InvalidDimensionException e) {
                e.printStackTrace();
            }
            Individual test = algorithm.findBreakPoints();
            printBreakPointLocations(test);
            controller.showFitness(test, timeSeries);
        }
    }

    /**
     * Update all algorithm values/parameters.
     * This class assumes that no value is null, thus a check must be implemented beforehand. (In the GUI, no
     * fields can be left empty when pressing "Run")
     */
    private void updateValues(Controller controller) throws InvalidDimensionException {


        algorithm.setNoOfIndividuals(controller.getPopulationSize());
        algorithm.setMaxNoOfBreakPoints(controller.getMaxNoOfBreakPoints());

        algorithm.setAlpha(controller.getAlphaParameter());

        algorithm.setUniformCrossoverProb(controller.getUniformCrossoverProb());
        algorithm.setOnePointCrossoverProb(controller.getOnePointCrossoverProb());
        algorithm.setMutateProb(controller.getMutationProb());

        timeSeries = new TimeSeries(controller.getDataFilePath());
        algorithm.setTimeSeries(timeSeries);

    }

    /**
     * A temporary class to print break points. This will be obsolete when the GUI is able to display the
     * rectangles by itself.
     */
    private static void printBreakPointLocations(Individual individual) {

        String s = "";
        for (int i = 0; i < individual.getNoOfGenes(); i++) {
            if (individual.getAllele(i) == Statics.breakPointAllele)
                s += (i + " ");
        }
        System.out.println(s);
    }

}


