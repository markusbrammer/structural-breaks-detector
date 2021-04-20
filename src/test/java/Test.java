import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<Integer> array = new ArrayList<>();
        array.add(1);
        // addTwo(array);
        System.out.println(array.size());

    }

    public static void addTwo(List<Integer> array) {
        array.add(2);
    }

}
