package org.example

interface KMeans {

    fun calculateSSE(centroids: List<List<Double>>, data: List<List<Double>>, assignments: List<Int>): Double

    fun euclideanDistance(point1: List<Double>, point2: List<Double>): Double

    fun divideData(centroids: List<List<Double>>, data: List<List<Double>>): List<Int>

    fun updateCentroids(data: List<List<Double>>, assignments: List<Int>): List<List<Double>>

    fun initializeRandom(data: List<List<Double>>, k: Int): List<List<Double>>

    fun initializationKMeansPP(data: List<List<Double>>, k: Int): List<List<Double>>

    fun cluster(data: List<List<Double>>, k: Int, initialize: (List<List<Double>>, Int) -> List<List<Double>>): Pair<List<List<Double>>, List<Int>>

}
