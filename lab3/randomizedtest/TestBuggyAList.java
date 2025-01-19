package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void SimpleComparison() {
        AListNoResizing<Integer> list1 = new AListNoResizing<>();
        BuggyAList<Integer> list2 = new BuggyAList<>();

        list1.addLast(4);
        list2.addLast(4);

        list1.addLast(5);
        list2.addLast(5);

        list1.addLast(6);
        list2.addLast(6);

        assertEquals(list1.removeLast(),list2.removeLast());
        assertEquals(list1.removeLast(),list2.removeLast());
        assertEquals(list1.removeLast(),list2.removeLast());
    }

    @Test
    public void Randomized() {
        AListNoResizing<Integer> L = new AListNoResizing<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            }
        }
    }

    @Test
    public void MoreRandomized() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                int correctSize = correct.size();
                int brokenSize = broken.size();
                assertEquals(correctSize, brokenSize);
                System.out.println("size: " + correctSize);
            } else if (operationNumber == 2 && correct.size() > 0) {
                int correctLast = correct.getLast();
                int brokenLast = broken.getLast();
                assertEquals(correctLast, brokenLast);
                System.out.println("getLast: " + correctLast);
            } else if (operationNumber == 3 && correct.size() > 0) {
                int correctRemoved = correct.removeLast();
                int brokenRemoved = broken.removeLast();
                assertEquals(correctRemoved, brokenRemoved);
                System.out.println("removeLast: " + correctRemoved);
            }
        }

    }
}
