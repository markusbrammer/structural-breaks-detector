package ga;

public class Allele implements Comparable<Allele> {

    private Integer index;
    private BreakPoint methodBreakPoint;

    public Allele(int index, BreakPoint breakPoint) {
        this.index = index;
        this.methodBreakPoint = breakPoint;
    }

    @Override
    public int compareTo(Allele allele) {
        return index.compareTo(allele.getIndex());
    }

    @Override
    public String toString() {
        return "[" + index + "," + methodBreakPoint.toString() + "]";
    }

    public Integer getIndex() {
        return index;
    }
}