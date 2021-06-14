package genetic;

import fitness.BreakPoint;

public class Allele {

    private int index;
    private BreakPoint methodBreakPoint;

    public Allele(int index, BreakPoint breakPoint) {
        this.index = index;
        this.methodBreakPoint = breakPoint;
    }

    @Override
    public String toString() {
        return "[" + index + "," + methodBreakPoint.toString() + "]";
    }

    public Integer getIndex() {
        return index;
    }
}