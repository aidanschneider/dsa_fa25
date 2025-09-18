import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.example.DoublyLinkedList

/**
 * Unit tests for DoublyLinkedList class.
 *
 */
class DoublyLinkedListTest {
    @Test
    fun pushFront() {
        /**
         * Tests pushFront method of DoublyLinkedList by checking if peekFront and peekBack return expected values.
         *
         */
        val linkedList = DoublyLinkedList<Int>()

        linkedList.pushFront(10)

        assertEquals(10, linkedList.peekFront())
        assertEquals(10, linkedList.peekBack())

        linkedList.pushFront(20)

        assertEquals(20, linkedList.peekFront())
        assertEquals(10, linkedList.peekBack())
    }

    @Test
    fun pushBack() {
        /**
         * Tests pushBack method of DoublyLinkedList by checking if peekFront and peekBack return expected values.
         *
         */
        val linkedList = DoublyLinkedList<Int>()
        linkedList.pushBack(10)
        assertEquals(10, linkedList.peekFront())
        assertEquals(10, linkedList.peekBack())

        linkedList.pushBack(20)

        assertEquals(10, linkedList.peekFront())
        assertEquals(20, linkedList.peekBack())

    }

    @Test
    fun popFront() {
        /**
         * Tests popFront method of DoublyLinkedList by checking its return values with empty, length-one, and 2-length
         * linked lists.
         */
        val linkedList = DoublyLinkedList<Int>()

        assertNull(linkedList.popBack())

        linkedList.pushFront(10)
        linkedList.pushFront(20)

        val poppedVal = linkedList.popFront()
        assertEquals(20, poppedVal)

        val poppedSecondVal = linkedList.popFront()
        assertEquals(10, poppedSecondVal)

        assertTrue(linkedList.isEmpty())

    }

    @Test
    fun popBack() {
        /**
         * Tests popFront method of DoublyLinkedList by checking its return values with empty, length-one, and 2-length
         * linked lists.
         */
        val linkedList = DoublyLinkedList<Int>()

        assertNull(linkedList.popBack())

        linkedList.pushBack(10)
        linkedList.pushBack(20)

        val poppedVal = linkedList.popBack()
        assertEquals(20, poppedVal)

        val poppedSecondVal = linkedList.popBack()
        assertEquals(10, poppedSecondVal)

        assertTrue(linkedList.isEmpty())
    }

    @Test
    fun peekFront() {
        /**
         * Tests peekFront method of DoublyLinkedList by checking its return values with empty, length-one, and 2-length
         * linked lists.
         *
         */
        val linkedList = DoublyLinkedList<Int>()

        assertNull(linkedList.peekFront())

        linkedList.pushFront(10)
        linkedList.pushFront(20)

        assertEquals(20, linkedList.peekFront())

        linkedList.popFront()

        assertEquals(10, linkedList.peekFront())

        linkedList.popFront()

        assertTrue(linkedList.isEmpty())
    }

    @Test
    fun peekBack() {
        /**
         * Tests peekBack method of DoublyLinkedList by checking its return values with empty, length-one, and 2-length
         * linked lists.
         *
         */
        val linkedList = DoublyLinkedList<Int>()

        assertNull(linkedList.peekBack())

        linkedList.pushFront(10)
        linkedList.pushFront(20)

        assertEquals(10, linkedList.peekBack())

        linkedList.popFront()

        assertEquals(10, linkedList.peekBack())

        linkedList.popFront()

        assertTrue(linkedList.isEmpty())
    }

    @Test
    fun isEmpty() {
        /**
         * Tests isEmpty method of DoublyLinkedList by checking its return values with empty lists and lists with
         * values in them.
         *
         */
        val linkedList = DoublyLinkedList<Int>()

        assertTrue(linkedList.isEmpty())

        linkedList.pushFront(20)

        assertFalse(linkedList.isEmpty())

    }

}