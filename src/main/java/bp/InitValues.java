package bp;

/**
 * A class to simply make sure that the initial values are the same in
 */
public class InitValues {

    public static final int POP_SIZE = 50;
    public static final int MAX_BP = 3;
    public static final int MIN_DIST = 450;
    public static final double ALPHA = 0.25;
    public static final double UNI_PROB = 0.30;
    public static final double ONE_POINT_PROB = 0.30;
    public static final double MUTATE_PROB =
            1 - UNI_PROB - ONE_POINT_PROB;
    public static final int ITERATIONS = 800;


}
