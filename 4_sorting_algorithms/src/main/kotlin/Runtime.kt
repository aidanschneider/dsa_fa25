package org.example
import kotlin.time.measureTime
import kotlin.time.DurationUnit
import kotlin.random.Random

/**
 * A function that calculates the runtime of a given sorting algorithm for lists of varying sizes.
 *
 * @param sortAlgorithm the sorting algorithm being analyzed
 * @return a list of doubles representing the runtimes for each size of list.
 */
fun runtimeCalculator(sortAlgorithm: (List<Int>) -> List<Int>): List<Double> {
    val runTimes = mutableListOf<Double>()
    for (size in listOf(10, 100, 1000, 10000, 100000)) {
        val x = (1 until size).map { Random.nextInt(100000) }
        val runTime = measureTime {
            sortAlgorithm(x)
        }
        runTimes.add(runTime.toDouble(DurationUnit.SECONDS))
    }
    return runTimes
}