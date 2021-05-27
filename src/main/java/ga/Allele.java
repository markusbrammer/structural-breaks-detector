package ga;

public class Allele implements Comparable<Allele> {

    private Integer index;
    private char methodBreakPoint;

    public Allele(int index, char breakPoint) {
        this.index = index;
        this.methodBreakPoint = breakPoint;
    }

    @Override
    public int compareTo(Allele allele) {
        return index.compareTo(allele.getIndex());
    }

    public Integer getIndex() {
        return index;
    }
}