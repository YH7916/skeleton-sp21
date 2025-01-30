package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testAddFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertTrue("Should be empty initially", deque.isEmpty());
        assertEquals(0, deque.size());

        deque.addFirst(1);
        assertFalse("Should not be empty after adding", deque.isEmpty());
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));

        deque.addFirst(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(2), deque.get(0));
        assertEquals(Integer.valueOf(1), deque.get(1));
    }

    @Test
    public void testAddLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));

        deque.addLast(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));
        assertEquals(Integer.valueOf(2), deque.get(1));
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertNull("Remove from empty deque should return null", deque.removeFirst());

        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(Integer.valueOf(2), deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertNull("Remove from empty deque should return null", deque.removeLast());

        deque.addLast(1);
        deque.addLast(2);
        assertEquals(Integer.valueOf(2), deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));
    }

    @Test
    public void testResize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        // Add 9 elements to trigger resize (initial size is 8)
        for (int i = 0; i < 9; i++) {
            deque.addLast(i);
        }
        assertEquals(9, deque.size());
        // Verify all elements are preserved after resize
        for (int i = 0; i < 9; i++) {
            assertEquals(Integer.valueOf(i), deque.get(i));
        }
    }

    @Test
    public void testShrink() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        // Add 17 elements to get array of size 32
        for (int i = 0; i < 17; i++) {
            deque.addLast(i);
        }
        // Remove elements until size is small enough to trigger shrink
        for (int i = 0; i < 13; i++) {
            deque.removeLast();
        }
        assertEquals(4, deque.size());
        // Verify remaining elements are correct
        for (int i = 0; i < 4; i++) {
            assertEquals(Integer.valueOf(i), deque.get(i));
        }
    }

    @Test
    public void testEquals() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        ArrayDeque<Integer> deque2 = new ArrayDeque<>();

        // Empty deques should be equal
        assertTrue(deque1.equals(deque2));

        deque1.addLast(1);
        deque1.addLast(2);
        deque2.addLast(1);
        deque2.addLast(2);

        // Same content deques should be equal
        assertTrue(deque1.equals(deque2));

        deque2.removeLast();
        // Different content deques should not be equal
        assertFalse(deque1.equals(deque2));

        // Test null case
        assertFalse(deque1.equals(null));
    }

    @Test
    public void testGet() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertNull("Get from empty deque should return null", deque.get(0));
        assertNull("Get with negative index should return null", deque.get(-1));

        deque.addLast(1);
        assertEquals(Integer.valueOf(1), deque.get(0));
        assertNull("Get with too large index should return null", deque.get(1));
    }

    @Test
    public void testCircularBehavior() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        // Add and remove elements to test circular behavior
        for (int i = 0; i < 16; i++) {
            deque.addFirst(i);
        }
        for (int i = 0; i < 8; i++) {
            deque.removeFirst();
        }
        for (int i = 0; i < 8; i++) {
            deque.addLast(i);
        }
        assertEquals(16, deque.size());
    }
}