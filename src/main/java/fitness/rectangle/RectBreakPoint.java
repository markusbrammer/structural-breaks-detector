package fitness.rectangle;

import fitness.FitnessModel;
import ga.BreakPoint;

public class RectBreakPoint extends BreakPoint {
    char symbol = '!';

    @Override
    public String toString() {
        return symbol + "";
    }

    @Override
    public FitnessModel getFitnessModel() {
        return null;
    }
}