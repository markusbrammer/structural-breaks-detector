package ga;

import fitness.BreakPoint;
import fitness.FitnessModel;
import fitness.FitnessNode;

import java.util.List;

public class Individual {

    private Genome genome = new Genome();

    private double fitness;
    private List<FitnessNode> fitnessNodes;

    public Individual(int startIndex, int endIndex, FitnessModel fitnessModel) {
        // Add break point at start and end
        addBreakPoint(startIndex, fitnessModel.newBreakPoint());
        addBreakPoint(endIndex, fitnessModel.newBreakPoint());
    }

    public Individual(Allele startAllele, Allele endAllele) {
        addBreakPoint(startAllele);
        addBreakPoint(endAllele);
    }

    public Individual(Individual copyOf) {
        Genome copyGenome = copyOf.getGenome();
        for (int g = 0; g < copyGenome.size(); g++)
            this.genome.add(copyGenome.get(g));
    }

    public void addBreakPoint(int breakPointindex, BreakPoint breakPoint) {
        Allele allele = new Allele(breakPointindex, breakPoint);
        genome.add(allele);
    }

    public void addBreakPoint(Allele allele) {
        genome.add(allele);
    }

    public boolean breakPointAtIndex(int breakPointIndex) {
        // geneOf returns > 0 if an allele a has the break point index
        return genome.geneOf(a -> a.getIndex() == breakPointIndex) != -1;
    }

    public int getNoOfBreakPoints() {
        return genome.size() - 2;
    }

    public Genome getGenome() {
        return genome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return genome.toString();
    }

    public void removeBreakPoint(int index) {
        genome.remove(index);
    }

    public void setFitnessNodes(List<FitnessNode> fitnessNodes) {
        this.fitnessNodes = fitnessNodes;
    }

    public Allele getStartAllele() {
        return genome.get(0);
    }

    public Allele getEndAllele() {
        return genome.get(genome.size() - 1);
    }

    public List<FitnessNode> getFitnessNodes() {
        return fitnessNodes;
    }
}


