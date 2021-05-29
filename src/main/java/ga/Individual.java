package ga;

import data_structures.list.SortedDoublyLinkedList;
import fitness.FitnessNode;

import java.util.List;
import java.util.Optional;

public class Individual implements Comparable<Individual> {

    private final SortedDoublyLinkedList<Allele> genome =
            new SortedDoublyLinkedList<>();

    private double fitness;
    private List<FitnessNode> fitnessNodes;

    public Individual() {}

    public Individual(Individual copyOf) {
        for (Allele allele : copyOf.getGenome()) {
            this.addBreakPoint(allele);
        }
    }

    public void addBreakPoint(int index, BreakPoint breakPoint) {
        Allele allele = new Allele(index, breakPoint);
        genome.add(allele);
    }

    public void addBreakPoint(Allele allele) {
        genome.add(allele);
    }

    public boolean breakPointAtIndex(int index) {
        return genome.stream().anyMatch(allele -> allele.getIndex() == index);
    }

    public SortedDoublyLinkedList<Allele> getGenome() {
        return genome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual o) {
        return fitness >= o.getFitness() ? 1 : -1;
    }

    @Override
    public String toString() {
        return genome.toString();
    }

    public void removeBreakPoint(int index) {
        Optional<Allele> allele = genome.stream().
                filter(a -> a.getIndex() == index).findFirst();

        allele.ifPresent(genome::remove);
    }

    public void setFitnessNodes(List<FitnessNode> fitnessNodes) {
        this.fitnessNodes = fitnessNodes;
    }

    public List<FitnessNode> getFitnessNodes() {
        return fitnessNodes;
    }
}


