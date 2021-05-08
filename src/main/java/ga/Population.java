package ga;

public class Population {

    private Individual[] individuals;

    /**
     * Initialize a population of null-individuals
     */
    public Population(int noOfIndividuals) {
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
