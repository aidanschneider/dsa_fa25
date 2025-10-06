package org.example
import kotlin.time.measureTime
import kotlin.time.DurationUnit
import kotlin.random.Random

// printing out runtimes
fun main() {
    // insertion sort runtime
    val insertionSortRuntimes = runtimeCalculator(::insertionSort)

    // merge sort runtime
    val mergeSortRuntimes = runtimeCalculator(::mergeSort)

    // bubble sort runtime
    val bubbleSortRuntimes = runtimeCalculator(::bubbleSort)


    // selection sort runtime
    val selectionSortRuntimes = runtimeCalculator(::selectionSort)

    val sizes = listOf(10, 100, 1000, 10000, 100000)
    val allRuntimes = listOf(
        insertionSortRuntimes,
        mergeSortRuntimes,
        bubbleSortRuntimes,
        selectionSortRuntimes
    )

    // formatting a table
    println("%-11s %-17s %s".format("Size", "Algorithm", "Runtime (s)"))
    println("-".repeat(45))

    val algorithmNames = listOf("Insertion Sort", "Merge Sort", "Bubble Sort", "Selection Sort")

    // iterate through each size index
    sizes.indices.forEach { sizeIndex ->
        // for each size, iterate through each algorithm
        algorithmNames.indices.forEach { algIndex ->
            val size = sizes[sizeIndex]
            val algorithm = algorithmNames[algIndex]
            // get the specific runtime for the given size and algorithm
            val runtime = allRuntimes[algIndex][sizeIndex]

            // print the formatted row
            println("%-11d %-17s %.8f".format(size, algorithm, runtime))
        }
        // blank line for readability
        if (sizeIndex < sizes.size - 1) {
            println()
        }
    }
}


