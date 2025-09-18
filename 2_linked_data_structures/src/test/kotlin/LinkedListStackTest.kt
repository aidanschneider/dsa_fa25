import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.example.LinkedListStack

/**
 * Unit tests for LinkedListStack class.
 *
 */
class LinkedListStackTest {
    @Test
    fun push() {
        /**
         * Tests push method of LinkedListStack by checking peek return value when element added to stack.
         *
         */
        val stack = LinkedListStack<Int>()

        stack.push(10)

        assertFalse(stack.isEmpty())
        assertEquals(10, stack.peek())
    }
    @Test
    fun pop() {
        /**
         * Tests pop method of LinkedListStack by checking peek and pop return value when element added to stack.
         *
         */
        val stack = LinkedListStack<Int>()
        assertNull(stack.pop())

        stack.push(10)
        stack.push(20)

        val poppedValue = stack.pop()

        assertEquals(20, poppedValue)
        assertEquals(10, stack.peek())
    }
    @Test
    fun peek() {
        /**
         * Tests peek method of LinkedListStack by checking peek return value when element added to stack
         * and when element empty.
         *
         */
        val stack = LinkedListStack<Int>()
        assertNull(stack.peek())
        stack.push(10)
        assertEquals(10, stack.peek())

    }
    @Test
    fun isEmpty() {
        /**
         * Tests isEmpty method of LinkedListStack by checking its return values with empty stacks and stacks with
         * values in them.
         *
         */
        val stack = LinkedListStack<Int>()
       assertTrue(stack.isEmpty())
    }

}