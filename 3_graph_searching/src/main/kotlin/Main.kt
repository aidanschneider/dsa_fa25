package org.example

import sun.awt.Mutex
import sun.security.provider.certpath.Vertex
import java.util.Queue
import javax.print.attribute.standard.Destination
import kotlin.math.min
import java.io.File

/**
 *
 * An implementation of an adjacency map containing vertices and weighted edges.
 *
 * @param VertexType a generic type parameter
 * @property vertices a mutable set holding the vertices of the map
 * @property adjacencyMap a mutable map that has vertices of the graph as keys and its edge connections to other
 * vertices and their weights as values
 */
class AdjacencyMap<VertexType> : Graph<VertexType> {
    private val vertices = mutableSetOf<VertexType>()
    private val adjacencyMap = mutableMapOf<VertexType, MutableMap<VertexType, Double>>()

    /**
     * Gets the vertices of the graph.
     * @return the set of vertices in the group.
     */
    override fun getVertices(): Set<VertexType> {
        return vertices
    }

    /**
     * Adds an edge to the graph.
     *
     * @param from the vertex the new edge will begin at.
     * @param to the vertex the new edge will end at.
     * @param cost the cost (or weight) the new edge will be assigned.
     */
    override fun addEdge(from: VertexType, to: VertexType, cost: Double) {
        // add vertices to set
        // sets do not accept duplicates so will not duplicate if already existing
        vertices.add(from)
        vertices.add(to)

        // gets existing map of neighbors of from
        val neighbors = adjacencyMap[from]

        // if from doesn't have neighbors, meaning it doesn't exist in the map
        // add it to the map, along with vertex to and its cost
        if (neighbors == null) {
            adjacencyMap[from] = mutableMapOf(to to cost)
        } else {
            // otherwise add the cost and to vertex to the map
            neighbors[to] = cost
        }
    }

    /**
     * Gets the edges of the graph.
     *
     * @param from the vertex for which the user wants to retrieve edges.
     * @return a map with keys representing from's neighbors (aka what vertices its connected to) and the costs
     * (or weights) for each of those edges.
     */
    override fun getEdges(from: VertexType): Map<VertexType, Double> {
        return adjacencyMap[from] ?: emptyMap()
    }

    /**
     * Clears the map of all edges and vertices and clears the vertices set.
     *
     */
    override fun clear() {
        vertices.clear()
        adjacencyMap.clear()
    }
}

/**
 *
 * A queue implementation of a min priority queue where each element in the queue has a priority weighting.
 *
 * @param T takes any type
 * @property Node a private class initialized to store the element and priority of a Node in the priority queue
 * @property queue the queue of elements with priority.
 */
class QueueMinPriorityQueue<T>: MinPriorityQueue<T> {
    private data class Node<T>(val element: T, val priority: Double)
    private val queue = mutableListOf<Node<T>>()

    /**
     * Checks if the queue is empty.
     *
     * @return if the queue is empty, returns true, and false otherwise.
     */
    override fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    /**
     * Adds an element to the priority queue with its corresponding priority.
     *
     * @param elem the element to be added to the priority queue.
     * @param priority the priority value of the element being added to the priority queue.
     */
    override fun addWithPriority(elem: T, priority: Double) {
        val newNode = Node(elem, priority)
        queue.add(newNode)
    }

    /**
     * Gets the next element to be processed in the priority queue.
     *
     * @return the next element in the queue to be processed, or null if the queue is empty.
     */
    override fun next(): T? {
        if (queue.isEmpty()) {
            return null
        }
        val minPriority = queue.minByOrNull { it.priority }!!
        queue.remove(minPriority)
        return minPriority.element
    }

    /**
     * Adjusts the priority weighting of an existing element in the queue.
     *
     * @param elem the existing element in the queue.
     * @param newPriority the new priority weighting to be assigned to the existing element.
     */
    override fun adjustPriority(elem: T, newPriority: Double) {
        val elemToChange = queue.find { it.element == elem }

        if (elemToChange != null) {
            queue.remove(element = elemToChange)
            queue.add(Node(elem, newPriority))
        }
    }
}


/**
 *
 * Implementation of a MinHeap for use in the priority queue.
 *
 * @param T takes any type
 * @property Node a private class initialized to store the element and priority of a Node in the priority queue
 * @property minHeap a mutable list that will store the nodes of the minHeap
 */
class minHeap<T> {
    private data class Node<T>(val element: T, val priority: Double)
    private val minHeap = mutableListOf<Node<T>>()

    /**
     * Checks if the minHeap is empty.
     *
     * @return true if minHeap is empty, and false otherwise
     */
    fun isEmpty(): Boolean {
        //checks if minHeap is empty
        return minHeap.isEmpty()
    }

    /**
     * Adds a node to the minHeap, maintaining the heap invariant.
     *
     * @param elem the element of the node to be added
     * @param priority the priority of the element being added
     */
    fun add(elem: T, priority: Double) {
        // add the node to the end of the list (bottom of heap)
        minHeap.add(Node(elem, priority))

        //sift the element up to maintain heap invariant
        siftUp(minHeap.size - 1)
    }

    /**
     * Moves a node up (sifts it up) in the layers of minHeap until the heap invariant is satisfied.
     *
     * @param index the index of the Node to be sifted up
     */
    private fun siftUp(index: Int) {
        // get the current index of the newly added node
        var currentIndex = minHeap.size - 1

        // while loop to sift the node up
        // while the added node's index is not the root index and the priority of the added node is greater than its parents',
        while (currentIndex > 0 && minHeap[currentIndex].priority < minHeap[(currentIndex - 1) / 2].priority) {
            // store the parent node in a temp value
            val temp = minHeap[(currentIndex - 1) / 2]
            // replace the parent node with the new node
            minHeap[(currentIndex - 1) / 2] = minHeap[currentIndex]
            // place the parent node where the added node originally was
            minHeap[currentIndex] = temp
            // update the index of the added node for next loop iteration
            currentIndex = (currentIndex - 1) / 2
        }
    }

    /**
     * Moves a node down (sifts it down) in the layers of minHeap until the heap invariant is satisfied.
     *
     * @param index the index of the node to be sifted down
     */
    private fun siftDown(index: Int) {
        // set current index of node to be sifted down
        var currentIndex = index

        while (true) {
            // get left and right children indices
            val leftChildIndex = (2*currentIndex)+1
            val rightChildIndex = (2*currentIndex)+2

            // initialize a smaller child index
            var smallerChildIndex = 0

            // check if the left child index exists, if it does set smallerChildIndex to it tentatively
            if (leftChildIndex < minHeap.size) {
                smallerChildIndex = leftChildIndex
            } else {
                break
            }

            // switches the index of the smaller child to the right child's index if it exists in the heap and has a smaller priority
            if (rightChildIndex < minHeap.size) {
                if (minHeap[rightChildIndex].priority < minHeap[leftChildIndex].priority) {
                    smallerChildIndex = rightChildIndex
                }
            }

            // if the new parent is in the right spot priority-wise, break the while loop
            if (minHeap[currentIndex].priority <= minHeap[smallerChildIndex].priority) {
                break
            }

            // sift down by swapping the smallest child node with the parent node
            val temp = minHeap[smallerChildIndex]
            minHeap[smallerChildIndex] = minHeap[currentIndex]
            minHeap[currentIndex] = temp

            // update the index of the current node
            currentIndex = smallerChildIndex

        }
    }
    /**
     * Returns the element of the root of the minHeap, which is the first element to be processed.
     *
     * @return rootElement, the element of the root
     */
    fun next(): T? {
        // checks if the heap is empty before performing operation
        if (minHeap.isEmpty()) {
            return null
        }

        // get value of root element for returning
        val rootElement = minHeap[0].element

        // store the last node for replacing as root
        val lastNode = minHeap.removeLast()

        if (minHeap.isNotEmpty()) {
            // replace empty root with last node in minHeap
            minHeap[0] = lastNode
            // use siftDown method to sift node down in the heap
            siftDown(0)

        }
        return rootElement
    }

    /**
     * Searches for the index of an element.
     *
     * @param elem the element of the node whose index we are searching for.
     */
    fun findIndex(elem: T): Int {
        // searches for the index of the requested element
        // can use indexOfFirst since
        return minHeap.indexOfFirst { it.element == elem }
    }

    /**
     * Updates the priority of a node.
     *
     * @param index the index of the node whose priority we're changing
     * @param newPriority the new priority of the node.
     */
    fun updatePriority(index: Int, newPriority: Double) {
        // temporarily store the original element of the node to be changed
        val oldPriority = minHeap[index].priority
        var tempElement = minHeap[index].element

        // update the node at the index with the original element and new priority
        minHeap[index] = Node(tempElement, newPriority)

        if (oldPriority > newPriority) {
            siftUp(index)
        } else {
            siftDown(index)
        }
    }
}

/**
 *
 * A minHeap implementation of a min priority queue where each element in the queue has a priority weighting.
 *
 * @param T takes any type
 * @property heap a instance of the minHeap class defined earlier
 */
class HeapMinPriorityQueue<T> : MinPriorityQueue<T> {
    // instance of minHeap class defined above
    private val heap = minHeap<T>()

    /**
     * Checks if the priority queue heap is empty.
     *
     * @return true if the heap is empty and false otherwise
     */
    override fun isEmpty(): Boolean {
        return heap.isEmpty()
    }

    /**
     * Adds a node to the priority queue, accounting for its priority.
     *
     * @param elem the element of the node being added
     * @param priority the priority of the element being added
     */
    override fun addWithPriority(elem: T, priority: Double) {
        return heap.add(elem, priority)
    }

    /**
     * Gets the next node in the heap to be processed.
     *
     * @return the next node in the heap to be processed
     */
    override fun next(): T? {
        // How would you get the next element from the heap?
        return heap.next()
    }

    /**
     * Adjusts the priority of an existing node.
     *
     * @param elem the element of the node
     * @param newPriority the new priority of the element
     */
    override fun adjustPriority(elem: T, newPriority: Double) {
        // get the index of the node that needs to be adjusted
        val index = heap.findIndex(elem)

        // if the node exists, update its priority
        if (index != -1) {
            heap.updatePriority(index, newPriority)
        }
    }
}


// Searching

/**
 * Function implementation of Dijkstra's Algorithm.
 *
 * @param AdjacencyMap the map of neighbors and costs associated with their connecting edges
 * @param start the start node
 * @param destination the target node
 *
 * @return path the path to get from start to destination
 */
fun <VertexType> dijkstra(graph: AdjacencyMap<VertexType>, start: VertexType, destination: VertexType?): List<VertexType>? {

    // initialize instance of MinHeapPriorityQueue
    val priorityQueue = HeapMinPriorityQueue<VertexType>()

    // initialize a map to store costs for each vertex and previous map for backtracking
    var costs = mutableMapOf<VertexType, Double>()
    var previous = mutableMapOf<VertexType, VertexType?>()

    // set all costs for each vertex to infinity as an initial state
    for (vertex in graph.getVertices()) {
        costs[vertex] = Double.POSITIVE_INFINITY
        previous[vertex] = null
    }

    //set the start element cost to 0, and add the start element to the queue
    costs[start] = 0.0
    priorityQueue.addWithPriority(start, 0.0)

    // while the priority queue is not empty, keep checking for the destination
    while (!priorityQueue.isEmpty()) {
        var current = priorityQueue.next()
        // if current is null or the current is the destination, stop the loop
        if (current == null || current == destination) {
            break
        } else {
            // for each neighbor, check which neighbor is closest (is least costly to get to)
            for ((neighbor,edgeCost) in graph.getEdges(current)) {
                val newCost = costs[current]!! + edgeCost

                if (newCost < costs[neighbor]!!) {
                    // add the closest (cheapest) neighbor to the priority queue to be checked
                    costs[neighbor] = newCost
                    previous[neighbor] = current
                    priorityQueue.addWithPriority(neighbor, newCost)
                }
            }
        }
    }

    // if the path behind is null, return null (no path found)
    if (previous[destination] == null) {
        return null
    }

    //backtracking to develop path
    val path = mutableListOf<VertexType>()
    var current: VertexType? = destination
    while (current != null) {
        path.add(current)
        current = previous[current]
    }

    // reverse the path to correct order
    path.reverse()
    return path
}


// Solving Problems with Dijkstra's Algorithm: Project Euler Problem 81

fun main() {
    // creating instance of graph
    val graph = AdjacencyMap<Pair<Int, Int>>()

    // read matrix from .txt file
    val matrix = File("src/main/kotlin/0081_matrix.txt").readLines().map { line -> line.split(',').map { it.toInt() } }
    for (row in matrix.indices) {
        for (col in matrix[row].indices) {
            val from = Pair(row, col)

            //add right edge
            if (col + 1 < matrix[row].size) {
                val to = Pair(row, col + 1)
                val cost = matrix[to.first][to.second].toDouble()
                graph.addEdge(from, to, cost)
            }

            //add bottom edge
            if (row + 1 < matrix.size) { // Makes sure we don't go off the grid
                val to = Pair(row + 1, col)
                val cost = matrix[to.first][to.second].toDouble()
                graph.addEdge(from, to, cost)
            }

        }
    }

    // defining starting and ending nodes
    val startNode = Pair(0, 0)
    val endNode = Pair(79, 79)

    // run Dijkstra's algorithm
    val path = dijkstra(graph, startNode, endNode)

    // print most cost-efficient path
    println("Shortest path found: $path")

    // calculate total cost
    var totalCost = 0
    if (path != null) {
        for (pair in path) {
            totalCost = matrix[pair.first][pair.second] + totalCost
        }
    }

    // print total cost
    println("total cost: $totalCost")
}