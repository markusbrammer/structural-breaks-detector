package ga;

import ga.Individual;

import java.util.ArrayList;

public class Population {

    private Individual[] individuals;

    public Population(int noOfIndividuals) {
        /**
         * Initialize a population of null-individuals
         */
        individuals = new Individual[noOfIndividuals];
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public void setIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }

    public int getNoOfIndividuals() {
        return individuals.length;
    }


}
