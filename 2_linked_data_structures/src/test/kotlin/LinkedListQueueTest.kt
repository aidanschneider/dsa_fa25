import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.example.LinkedListQueue
import kotlin.test.assertEquals

/**
 * Unit tests for LinkedListQueue class.
 *
 */
class LinkedListQueueTest {
    @Test
    fun enqueue() {
        /**
         * Tests enqueue method of LinkedListQueue by checking peek return value when element added to queue.
         *
         */
        val queue = LinkedListQueue<Int>()
        queue.enqueue(10)
        assertFalse(queue.isEmpty())
        assertEquals(10, queue.peek())
    }

    @Test
    fun dequeue() {
        /**
         * Tests dequeue method of LinkedListQueue by checking peek return value when element added to queue.
         *
         */
        val queue = LinkedListQueue<Int>()
        queue.enqueue(10)
        queue.enqueue(20)

        val dequeuedVal = queue.dequeue()

        assertEquals(10, dequeuedVal)
        assertEquals(20, queue.peek())

    }

    @Test
    fun peek() {
        /**
         * Tests peek method of LinkedListQueue by checking peek return value when element added to queue.
         *
         */
        val queue = LinkedListQueue<Int>()
        assertNull(queue.peek())

        queue.enqueue(10)
        val peekedVal = queue.peek()
        assertEquals(10, peekedVal)

    }

    @Test
    fun isEmpty() {
        /**
         * Tests isEmpty method of LinkedListQueue by checking its return values with empty queues and queues with
         * values in them.
         *
         */
        val queue = LinkedListQueue<Int>()
        assertTrue(queue.isEmpty())
    }

}

fun main() {

}