package org.example

// Implementing 4 Sorting Algorithms

// 1) Insertion Sort
/**
 * A function that implements Insertion Sort on a given list.
 *
 * @param unsortList the unsorted list to be sorted.
 * @return sortedList the sorted version of the list.
 */
fun insertionSort(unsortList: List<Int>): List<Int> {
    val sortedList = unsortList.toMutableList()

    for (i in 1 until sortedList.size) {
        var key = sortedList[i]
        var j = i-1

        while (j >= 0 && key < sortedList[j]) {
            sortedList[j+1] = sortedList[j]
            j -= 1
            sortedList[j+1] = key
        }
    }
    return sortedList
}

// 2) Merge Sort
// a) main mergeSort function that splits list recursively
/**
 * A function that implements Merge Sort on a given list.
 *
 * @param unsortList the unsorted list to be sorted.
 * @return the sorted version of the list.
 */
fun mergeSort(unsortList: List<Int>): List<Int> {
    if (unsortList.size <= 1) {
        return unsortList
    }

    val middleIndex = unsortList.size / 2

    val left = unsortList.slice(0 until middleIndex)
    val right = unsortList.slice(middleIndex until unsortList.size)

    val sortedLeft = mergeSort(left)
    val sortedRight = mergeSort(right)

    return merge(sortedLeft, sortedRight)
}

// b) function that handles merging left and right sorted lists
/**
 * A helper function for mergeSort that merges two subarrays of the original unsorted list together in sorted order.
 *
 * @param leftList the subarray on the left side of the split.
 * @param rightList the subarray on the right side of the split.
 * @return sortedList the sorted version of the list once rejoined.
 */
fun merge(leftList: List<Int>, rightList: List<Int>): List<Int> {
    var leftPointer = 0
    var rightPointer = 0

    var sortedList = mutableListOf<Int>()

    while (leftPointer <= leftList.size-1 && rightPointer <= rightList.size-1) {
        if (leftList[leftPointer] <= rightList[rightPointer]) {
            sortedList.add(leftList[leftPointer])
            leftPointer++
        } else {
            sortedList.add(rightList[rightPointer])
            rightPointer++
        }
    }

    while (leftPointer < leftList.size) {
        sortedList.add(leftList[leftPointer])
        leftPointer++
    }
    while (rightPointer < rightList.size) {
        sortedList.add(rightList[rightPointer])
        rightPointer++
    }

    return sortedList
}

// 3) Bubble Sort
/**
 * A function that implements Bubble Sort on a given list.
 *
 * @param unsortList the unsorted list to be sorted.
 * @return sortedList the sorted version of the list.
 */
fun bubbleSort(unsortList: List<Int>): List<Int> {
    val sortedList = unsortList.toMutableList()
    // iterating through entire list
    for (i in 0 until sortedList.size) {

        // iterating through unsorted elements (last i elements are already sorted)
        for (j in 0 until (sortedList.size - i - 1)) {
            if (sortedList[j] > sortedList[j+1]) {
                // swapping elements in list
                var temp = sortedList[j]
                sortedList[j] = sortedList[j + 1]
                sortedList[j + 1] = temp
            }
        }
    }
    return sortedList
}

// 4) Selection Sort
/**
 * A function that implements Selection Sort on a given list.
 *
 * @param unsortList the unsorted list to be sorted.
 * @return sortedList the sorted version of the list.
 */
fun selectionSort(unsortList: List<Int>): List<Int> {
    val sortedList = unsortList.toMutableList()
    for (i in 0 until sortedList.size) {
        var idMin = i

        for (j in i+1 until sortedList.size) {
            if (sortedList[j] < sortedList[idMin]) {
                idMin = j
            }
        }
        val temp = sortedList[i]
        sortedList[i] = sortedList[idMin]
        sortedList[idMin] = temp
    }
    return sortedList
}