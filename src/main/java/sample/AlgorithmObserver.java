package sample;

import bp.BreakPointAlgorithm;
import bp.Statics;
import data.TimeSeries;
import ga.Individual;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AlgorithmObserver implements PropertyChangeListener {

    private BreakPointAlgorithm algorithm;

    public AlgorithmObserver(BreakPointAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void propertyChange(PropertyChangeEvent event) {

        // Values get updates from controller
        Controller controller = (Controller) event.getSource();
        String propertyName = event.getPropertyName();
        if (!propertyName.equals("run")) {
            updateValues(controller, propertyName);
        } else {
            Individual test = algorithm.findBreakPoints();
            printBreakPointLocations(test);
        }

    }

    private void updateValues(Controller controller, String propertyName) {

        if (propertyName.equals("populationSize")) {
            algorithm.setNoOfIndividuals(controller.getPopulationSize());
        } else if (propertyName.equals("maxNoOfBreakPoints")) {
            algorithm.setMaxNoOfBreakPoints(controller.getMaxNoOfBreakPoints());
        } else if (propertyName.equals("alphaParameter")) {
            algorithm.setAlpha(controller.getAlphaParameter());
        } else if (propertyName.equals("uniformCrossoverProb")) {
            double newValue = controller.getUniformCrossoverProb();
            algorithm.setUniformCrossoverProb(newValue);
        } else if (propertyName.equals("onePointCrossOverProb")) {
            double newValue = controller.getOnePointCrossoverProb();
            algorithm.setOnePointCrossoverProb(newValue);
        } else if (propertyName.equals("mutationProb")) {
            double newValue = controller.getMutationProb();
            algorithm.setMutateProb(newValue);
        } else if (propertyName.equals("dataFile")) {
            TimeSeries timeSeries = new TimeSeries(controller.getDataFilePath());
            algorithm.setTimeSeries(timeSeries);
        }

    }

    private static void printBreakPointLocations(Individual individual) {
        String s = "";
        for (int i = 0; i < individual.getNoOfGenes(); i++) {
            if (individual.getAllele(i) == Statics.breakPointAllele)
                s += (i + " ");
        }
        System.out.println(s);
    }

}
