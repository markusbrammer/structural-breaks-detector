import data_structures.list.SortedDoublyLinkedList;
import org.junit.Test;

public class TestDoublyLinkedList {

    @Test
    public void addThreeIntegersTest() {
        SortedDoublyLinkedList<Integer> list = new SortedDoublyLinkedList<>();
        list.add(1);
        list.add(5);
        list.add(3);
        list.add(0);
        list.add(-1);
        System.out.println(list);
    }

}
