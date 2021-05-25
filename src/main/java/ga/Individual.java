package ga;

public class Individual {

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

}
