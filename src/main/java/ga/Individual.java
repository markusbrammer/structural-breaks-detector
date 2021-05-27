package ga;

import java.util.ArrayList;
import java.util.List;

public class Individual {



//    private SortedDoublyLinkedList<Allele> genome;
//    private Fitness fitness;
//
//    public Individual() {}
//
//    public void addBreakPoint(int index, char breakPoint) {
//        Allele allele = new Allele(index, breakPoint);
//        genome.add(allele);
//    }



    private char[] genes;
    public Individual(int length) {
        /**
         * Initialize an individual with a given length and only null allele at all positions.
         */
        genes = new char[length];
    }

    public int getNoOfGenes() {
        return genes.length;
    }

    public void setAllele(int gene, char allele) {
        genes[gene] = allele;
    }

    public char getAllele(int gene) {
        return genes[gene];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int maxCharsInString = 20;
        int limit = (Math.min(genes.length, maxCharsInString));
        for (int i = 0; i < limit; i++) {
            s.append(genes[i]).append(" ");
        }
        return s.toString();
    }

    public List<Integer> allIndexesOf(char c) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < getNoOfGenes() - 1; i++) {
            if (genes[i] == c)
                indexes.add(i);
        }
        return indexes;
    }

}


