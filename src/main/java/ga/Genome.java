package ga;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Genome {

    private List<Allele> alleleList = new ArrayList<>();

    public Genome() {}

    public void add(Allele newAllele) {

        // Get gene to place the the new allele
        int gene = geneOf(allele -> allele.getIndex() > newAllele.getIndex());

        // Add the allele at determined gene. ArrayList moves all proceeding
        // genes one to the right. If no gene found, the allele must be at
        // the end.
        if (gene != -1) {
            alleleList.add(gene, newAllele);
        } else {
            alleleList.add(newAllele);
        }
    }

    /**
     * Remove the first element in the genome with a given break point index
     * @param breakPointIndex Index for a given break point to be removed
     */
    public void remove(int breakPointIndex) {
        // Find first gene having allele with the break point index
        int gene = geneOf(allele -> allele.getIndex() == breakPointIndex);
        if (gene != -1)
            alleleList.remove(gene);
    }

    public int size() {
        return alleleList.size();
    }

    public Allele get(int gene) {
        return alleleList.get(gene);
    }

    public int geneOf(Predicate<? super Allele> predicate) {
        int gene = -1;
        boolean searching = true;
        for (int g = 0; g < alleleList.size() && searching; g++) {
            Allele allele = alleleList.get(g);
            if (predicate.test(allele)) {
                gene = g;
                searching = false;
            }
        }
        return gene;
    }

}
