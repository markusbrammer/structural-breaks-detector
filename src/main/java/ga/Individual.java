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
        String s = "";
        int maxCharsInString = 20;
        int limit = (genes.length < maxCharsInString ? genes.length : maxCharsInString);
        for (int i = 0; i < limit; i++) {
            s += (genes[i] + " ");
        }
        return s;
    }

    public char[] getGenes() {
        return genes;
    }


    //    public void fillActiveGenes(int[] activeGenes, char activeAllele) {
//        /**
//         * For an array of genes give them an active allele. (Use this for adding break points where activeAllele is
//         * the symbol for a break point).
//         */
//        this.activeAllele = activeAllele;
//        int genesMaxIndex = genes.length;
//        for (int gene : activeGenes) {
//            if (gene <= genesMaxIndex)
//                genes[gene] = activeAllele;
//        }
//    }

}
