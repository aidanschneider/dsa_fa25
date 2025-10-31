package org.example
import kotlin.system.measureTimeMillis
import java.util.Random

/**
 * Function for timing clustering algorithm runtimes and calculating SSE to determine accuracy.
 *
 * @param k number of clusters
 * @param numPointsPerCluster number of points per cluster
 * @param stdDev standard deviation of clusters (how spread out from the true centroid the points are)
 * @param kmeans the implementation class of K-Means from KMeansImplement.kt
 */
fun testTimeAndSSE(k: Int, numPointsPerCluster: Int, stdDev: Double, kmeans: KMeansImplement) { // It returns the time and SSE

    // use random as javaRandom inside the function
    val javaRandom = Random()

    // defining a bounding box for the centroids
    val minCoordinate = 0.0
    val maxCoordinate = 10.0

    val coordinateRange = maxCoordinate - minCoordinate

    val trueCentroids = mutableListOf<List<Double>>()

    // loop to create k random centroids
    repeat(k) {
        // javaRandom.nextDouble gives a value between 0.0 and 1.0, so we scale it up
        val x = javaRandom.nextDouble() * coordinateRange + minCoordinate
        val y = javaRandom.nextDouble() * coordinateRange + minCoordinate

        trueCentroids.add(listOf(x, y))
    }
    // generate synthetic data
    val (data, labels) = generateSyntheticData(
        trueCentroids,
        numPointsPerCluster,
        stdDev
    )

    // running and timing K-means/K-means++
    var finalCentroids: List<List<Double>> = emptyList()
    var assignments: List<Int> = emptyList()

    println("Test Run (k=$k, stdDev=$stdDev) [K-means++]:")
    var ppCentroids: List<List<Double>> = emptyList()
    var ppAssignments: List<Int> = emptyList()

    // test k-means++
    val ppTime = measureTimeMillis {
        val (centroids, assigns) = kmeans.cluster(data, k, kmeans::initializationKMeansPP)
        ppCentroids = centroids
        ppAssignments = assigns
    }
    val ppSSE = if (ppCentroids.isNotEmpty()) {
        kmeans.calculateSSE(ppCentroids, data, ppAssignments)
    } else {
        Double.POSITIVE_INFINITY
    }
    println("  -> Time=$ppTime ms, SSE=$ppSSE")


    // test regular k-means with random initialization
    println("Test Run (k=$k, stdDev=$stdDev) [Random Init]:")
    var randCentroids: List<List<Double>> = emptyList()
    var randAssignments: List<Int> = emptyList()

    val randTime = measureTimeMillis {
        val (centroids, assigns) = kmeans.cluster(data, k, kmeans::initializeRandom)
        randCentroids = centroids
        randAssignments = assigns
    }
    val randSSE = if (randCentroids.isNotEmpty()) {
        kmeans.calculateSSE(randCentroids, data, randAssignments)
    } else {
        Double.POSITIVE_INFINITY
    }
    println("  -> Time=$randTime ms, SSE=$randSSE")
}

fun main() {
    val kmeans = KMeansImplement()
    for (k in 1 until 3) {
        println("--- Starting Round $k ---")
        for (i in 2 until 5) {
            for (j in 1 until 8) {
                println("--- Starting Test: k=$i, stdDev=$j ---")
                testTimeAndSSE(k = i, numPointsPerCluster = 10000, stdDev = j.toDouble(), kmeans = kmeans)
                println("--------------------------------------------------\n")
            }
        }
        println("--- Ending Round $k ---")
    }

}