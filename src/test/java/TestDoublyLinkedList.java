import data_structures.list.SortedDoublyLinkedList;
import org.junit.Assert;
import org.junit.Test;

public class TestDoublyLinkedList {

    SortedDoublyLinkedList<Integer> list = new SortedDoublyLinkedList<>();

    @Test
    public void addThreeIntegersTest() {
        list.add(1);
        list.add(5);
        list.add(3);
        list.add(0);
        list.add(-1);
        System.out.println(list);
    }

    @Test
    public void getNextElementTest1() {
        list.add(3);
        list.add(5);
        Assert.assertEquals(5, (int) list.getNextElement(3));
    }

    @Test
    public void getNextElementTest2() {
        list.add(3);
        list.add(5);
        Assert.assertNull(list.getNextElement(5));
    }

    @Test
    public void removeTest() {
        list.add(10);
        list.add(10);
        list.add(9);
        list.remove(10);
        list.remove(10);
        list.remove(9);
        System.out.println(list);
    }

}
