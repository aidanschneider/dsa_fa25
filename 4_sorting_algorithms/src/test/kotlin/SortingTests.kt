import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.example.*

/**
 * Unit testing class for Insertion, Merge, Bubble, and Selection Sort.
 *
 */
class SortingTests {
    /**
     * Insertion Sort unit testing function.
     *
     */
    @Test
    fun testInsertionSort() {
        // test 1
        val unsortedList1 = listOf(5, 1, 4, 2, 8)
        val sortedList1 = insertionSort(unsortedList1)
        val expectedList1 = listOf(1, 2, 4, 5, 8)
        assertEquals(expectedList1, sortedList1)

        // test 2
        val unsortedList2 = listOf(9, 8, 7, 6)
        val sortedList2 = insertionSort(unsortedList2)
        val expectedList2 = listOf(6, 7, 8, 9)
        assertEquals(expectedList2, sortedList2)
    }

    /**
     * Merge Sort unit testing function.
     *
     */
    @Test
    fun testMergeSort() {
        // test 1
        val unsortedList1 = listOf(5, 1, 4, 2, 8)
        val sortedList1 = mergeSort(unsortedList1)
        val expectedList1 = listOf(1, 2, 4, 5, 8)
        assertEquals(expectedList1, sortedList1)

        // test 2
        val unsortedList2 = listOf(9, 8, 7, 6)
        val sortedList2 = mergeSort(unsortedList2)
        val expectedList2 = listOf(6, 7, 8, 9)
        assertEquals(expectedList2, sortedList2)
    }

    /**
     * Bubble Sort unit testing function.
     *
     */
    @Test
    fun testBubbleSort() {
        // test 1
        val unsortedList1 = listOf(5, 1, 4, 2, 8)
        val sortedList1 = bubbleSort(unsortedList1)
        val expectedList1 = listOf(1, 2, 4, 5, 8)
        assertEquals(expectedList1, sortedList1)

        // test 2
        val unsortedList2 = listOf(9, 8, 7, 6)
        val sortedList2 = bubbleSort(unsortedList2)
        val expectedList2 = listOf(6, 7, 8, 9)
        assertEquals(expectedList2, sortedList2)
    }

    /**
     * Selection Sort unit testing function.
     *
     */
    @Test
    fun testSelectionSort() {
        // test 1
        val unsortedList1 = listOf(5, 1, 4, 2, 8)
        val sortedList1 = selectionSort(unsortedList1)
        val expectedList1 = listOf(1, 2, 4, 5, 8)
        assertEquals(expectedList1, sortedList1)

        // test 2
        val unsortedList2 = listOf(9, 8, 7, 6)
        val sortedList2 = selectionSort(unsortedList2)
        val expectedList2 = listOf(6, 7, 8, 9)
        assertEquals(expectedList2, sortedList2)
    }

}