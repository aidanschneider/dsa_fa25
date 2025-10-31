package org.example
import kotlin.math.*
import kotlin.random.Random

/**
 * Class implementation of K-Means and K-Means++ clustering algorithms.
 *
 */
class KMeansImplement(): KMeans {
    /**
     * Calculates Sum of Squared Error (SSE) to benchmark clustering performance.
     *
     * @param centroids the list of centroids determined by K-Means/++
     * @param data the data being clustered
     * @param assignments labels for clustering of data
     *
     * @return totalSSE, the sum of the squared errors
     */
    override fun calculateSSE(centroids: List<List<Double>>, data: List<List<Double>>, assignments: List<Int>): Double {
        var totalSSE = 0.0

        for (i in 0 until data.size) {
            val point = data[i]

            val clusterIndex = assignments[i]

            val assignedCentroid = centroids[clusterIndex]

            val dist = euclideanDistance(point, assignedCentroid)

            totalSSE += dist.pow(2)
        }
        return totalSSE
    }

    /**
     * Calculates the Euclidean Distance between points (useful for calculating next centroid).
     *
     * @param point1 first point in distance measurement
     * @param point2 second point in distance measurement
     *
     * @return the Euclidean distance between point 1 and 2
     */
    override fun euclideanDistance(point1: List<Double>, point2: List<Double>): Double {
        var eucDist = 0.0

        for (i in 0 until point1.size) {
            eucDist += (point2[i]-point1[i]).pow(2)
        }
        return sqrt(eucDist)
    }

    /**
     * Divides data into each cluster based off nearest centroid by Euclidean distance.
     *
     * @param centroids current centroids determined by K-Means/++
     * @param data data being clustered
     *
     * @return the new assignments of each point to a cluster
     */
    override fun divideData(centroids: List<List<Double>>, data: List<List<Double>>): List<Int> {

        val assignments = mutableListOf<Int>()

        for (i in 0 until data.size) {
            val distances = mutableListOf<Double>()
            for (j in 0 until centroids.size) {
                distances.add(euclideanDistance(data[i], centroids[j]))
            }
           assignments.add(distances.indexOf(distances.minOrNull()))
        }
        return assignments
    }

    /**
     * Updates centroids based off clustering.
     *
     * @param data data being clustered
     * @param assignments list indicating which cluster each point belongs to
     *
     * @return the new centroids
     */
    override fun updateCentroids(data: List<List<Double>>, assignments: List<Int>): List<List<Double>> {
        val numClusters = assignments.toSet().size
        val dataClustered = MutableList(numClusters) { mutableListOf<List<Double>>() }

        // primes dataClustered 2d matrix for point insertion
        for (i in 0 until numClusters) {
            dataClustered.add(mutableListOf())
        }

        // assigns points to each column relative to which cluster they belong to
        for (i in 0 until assignments.size) {
            val clusterIndex = assignments[i]
            dataClustered[clusterIndex].add(data[i])
        }

        //finding mathematical center of clusters for new centroid creation
        val newCentroids = mutableListOf<List<Double>>()

        // getting number of dimensions, implementing safety check if no dimensions
        val numDimensions = data.firstOrNull()?.size ?: return emptyList()

        // loop through each cluster
        for (pointsInCluster in dataClustered) {

            // if cluster has no points, skip it
            if (pointsInCluster.isEmpty()) {
                continue
            }

            // create list to hold sum of each dimension for averaging
            val dimensionSums = MutableList(numDimensions) { 0.0 }

            // loop through each point in each cluster
            for (point in pointsInCluster) {
                // loop through each dimension of each point
                for (k in 0 until numDimensions) {
                    // add the kth dimension to the kth sum value
                    dimensionSums[k] += point[k]
                }
            }

            // take average of each dimension for each cluster
            val newCentroid = dimensionSums.map { sum ->
                sum / pointsInCluster.size
            }

            // add new centroid to final list
            newCentroids.add(newCentroid)
        }
        return newCentroids
    }

    /**
     * Regular K-Means initialization (Random or Forgy Method)
     *
     * @param data data to be clustered
     * @param k number of clusters
     *
     * @return initial centroids
     */
    override fun initializeRandom(data: List<List<Double>>, k: Int): List<List<Double>> {

        // generates random initial centroids
        var randCentroids = mutableSetOf<List<Double>>()
        while (randCentroids.size < k) {
            val randCent = data.random()
            randCentroids.add(randCent)
        }
        return randCentroids.toList()
    }

    /**
     * Initialization using K-Means++ method
     *
     * @param data the data to be clustered
     * @param k the number of clusters
     *
     * @return the initial centroids
     */
    override fun initializationKMeansPP(data: List<List<Double>>, k: Int): List<List<Double>> {
        var randCentroids = mutableSetOf<List<Double>>()
        randCentroids.add(data.random())
        while (randCentroids.size < k) {
            var dataDistance = mutableListOf<Double>()
            for (i in 0 until data.size) {
                dataDistance.add(randCentroids.minOf { centroid -> euclideanDistance(centroid, data[i])})
            }
            val weightedDist = dataDistance.map{it * it}
            val totalWeight = weightedDist.sum()
            val randomVal = Random.nextDouble() * totalWeight
            var cumulativeSum = 0.0
            var toAdd = 0
            while (cumulativeSum <= randomVal) {
                cumulativeSum += weightedDist[toAdd]
                toAdd += 1
            }

            randCentroids.add(data[toAdd-1])
        }

        return randCentroids.toList()
    }

    /**
     * Runs K-Means/++ clustering algorithms, calling all preceding functions.
     *
     * @param data the data to be clustered
     * @param k number of clusters to be created
     * @param initialize the initialization method to be used for the centroids (either K-Means or K-Means++)
     *
     * @return the final (current at last iteration) centroids and the assignments list of each point to each cluster
     */
    override fun cluster(data: List<List<Double>>, k: Int, initialize: (List<List<Double>>, Int) -> List<List<Double>>): Pair<List<List<Double>>, List<Int>> {

        var currCentroids = initialize(data, k)
        var assignments = emptyList<Int>()
        var prevCentroids = emptyList<List<Double>>()

        while (prevCentroids != currCentroids) {
            assignments = divideData(currCentroids, data)

            prevCentroids = currCentroids

            currCentroids = updateCentroids(data, assignments)

            if (currCentroids.isEmpty()) {
                break
            }
        }

        return Pair(currCentroids, assignments)
    }

}