package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */

    @Test
    public void testSquarePrimesComplex() {
        IntList lst = IntList.of(3, 5, 14, 17, 20);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("9 -> 25 -> 14 -> 289 -> 20", lst.toString());
    }
}
