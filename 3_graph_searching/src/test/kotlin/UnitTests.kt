import org.junit.jupiter.api.Assertions.*
import org.example.*
import kotlin.test.Test

class AdjacencyMapTests {
    @Test
    fun `addEdge creating vertices and edges`() {
        val graph = AdjacencyMap<String>()

        graph.addEdge("A", "B", 10.0)

        assertEquals(setOf("A", "B"), graph.getVertices())
        assertEquals(mapOf("B" to 10.0), graph.getEdges("A"))
        assertTrue(graph.getEdges("B").isEmpty())
    }

    @Test
    fun `clear should remove all vertices and edges`() {
        // Arrange
        val graph = AdjacencyMap<String>()
        graph.addEdge("A", "B", 10.0)
        graph.addEdge("B", "C", 5.0)

        // Act
        graph.clear()

        // Assert
        assertTrue(graph.getVertices().isEmpty())
        assertTrue(graph.getEdges("A").isEmpty())
    }

}

class HeapMinPriorityQueueTest {

    @Test
    fun `next should return element with lowest priority`() {

        val pq = HeapMinPriorityQueue<String>()
        pq.addWithPriority("C", 3.0)
        pq.addWithPriority("A", 1.0)
        pq.addWithPriority("B", 2.0)

        // should return A, then B, then C
        assertEquals("A", pq.next())
        assertEquals("B", pq.next())
        assertEquals("C", pq.next())
        assertTrue(pq.isEmpty())
    }

    @Test
    fun `adjustPriority should change the next element to be returned`() {

        val pq = HeapMinPriorityQueue<String>()
        pq.addWithPriority("High Priority", 100.0)
        pq.addWithPriority("Medium Priority", 50.0)


        pq.adjustPriority("High Priority", 1.0)

        // should change next element to be returned by .next() to High Priority
        assertEquals("High Priority", pq.next())
    }
}

class DijkstraTest {

    @Test
    fun `dijkstra should find shortest path in a simple graph`() {

        val graph = AdjacencyMap<String>()

        // shortest path is A->B->C
        graph.addEdge("A", "B", 1.0)
        graph.addEdge("B", "C", 1.0)
        graph.addEdge("A", "C", 10.0)

        // establishing expected path
        val expectedPath = listOf("A", "B", "C")


        val actualPath = dijkstra(graph, "A", "C")

        // should return A, B, C
        assertEquals(expectedPath, actualPath)
    }

    @Test
    fun `dijkstra should return null for unreachable node`() {

        val graph = AdjacencyMap<String>()
        graph.addEdge("A", "B", 1.0)
        graph.addEdge("C", "D", 1.0)


        val path = dijkstra(graph, "A", "D")

        // path should be null, since no connections between A and D
        assertNull(path)
    }
}