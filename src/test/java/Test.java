import ga.Individual;

import java.util.Random;

public class Test {

    public static void main(String[] args) {

        Individual i1 = new Individual(10);
        Individual i2 = new Individual(10);

        for (int i = 0; i < 10; i++) {
            i1.setAllele(i, '*');
            i2.setAllele(i, '*');
        }

        i2.setAllele(0, '-');

        System.out.println(i1.equals(i2));

    }

}
