package org.example
import genomeSnippet
import testAgainst


/**
 * Creates the scoring matrix for the global alignment of the two genome sequences.
 *
 * @param seq1 the first genome sequence being compared.
 * @param seq2 the second genome sequence being compared.
 * @param match the score value of a match between the two sequences.
 * @param mismatch the score value of a mismatch between two sequences.
 * @param gap the score value of a gap between two sequences.
 * @return alignMatrix the matrix containing the scoring values for the two genome sequences..
 * @return The value in the matrix cell.
 */
fun createScoreMatrix(seq1: String, seq2: String, match: Int, mismatch: Int, gap: Int): ArrayMatrix {

    // initializing alignment matrix
    var alignMatrix = ArrayMatrix(seq1.length + 1, seq2.length + 1)

    // setting first entry to 0
    alignMatrix[0,0] = 0.0

    // adding gap to first column and row
    for (i in 1 until alignMatrix.n) {
        alignMatrix[i,0] = alignMatrix[i-1, 0] + gap
    }
    for (j in 1 until alignMatrix.m) {
        alignMatrix[0,j] = alignMatrix[0, j-1] + gap
    }


    for (i in 1 until alignMatrix.n) {
        for (j in 1 until alignMatrix.m) {
            val top = alignMatrix[i - 1, j] + gap.toDouble()
            val left = alignMatrix[i, j - 1] + gap.toDouble()
            var  diag = 0.toDouble()
            if (seq1[i-1] == seq2[j-1]) {
                diag = alignMatrix[i - 1, j - 1] + match
            } else {
                diag = alignMatrix[i - 1, j - 1] + mismatch
            }
            alignMatrix[i,j] = maxOf(top, left, diag)
        }
    }
    return alignMatrix
}

/**
 * Function that backtracks through the alignment matrix to retrieve the alignment between the two genome sequences.
 *
 * @param seq1 the first genome sequence being compared.
 * @param seq2 the second genome sequence being compared.
 * @param match the score value of a match between the two sequences.
 * @param mismatch the score value of a mismatch between two sequences.
 * @param gap the score value of a gap between two sequences.
 * @param alignMatrix the alignment matrix to be backtracked through.
 * @return alignMatrix the matrix containing the scoring values for the two genome sequences..
 * @return The value in the matrix cell.
 */
fun findAlignment(seq1: String, seq2: String, match: Int, mismatch: Int, gap: Int, alignMatrix: ArrayMatrix): Pair<String, String> {

    // initializing lists to store the alignment for each genome sequence
    var backtrackSeq1 = mutableListOf<Char>()
    var backtrackSeq2 = mutableListOf<Char>()

    // counters to keep track of matrix location
    var i = alignMatrix.n-1;
    var j = alignMatrix.m-1;

    // iterates until top left corner reached
    while (i != 0 && j != 0) {

        // gets all relevant values for managing backtrack.
        var currentVal = alignMatrix[i, j]
        var top = alignMatrix[i-1, j]
        var left = alignMatrix[i, j-1]
        var diag = alignMatrix[i-1, j-1]
        if (currentVal == top + gap) {
            // add gap to second sequence (top indicates gap)
            backtrackSeq1.add(seq1[i-1])
            backtrackSeq2.add('-')
            i -= 1
        } else if (currentVal == left + gap) {
            // add gap to first sequence (left indicates gap)
            backtrackSeq1.add('-')
            backtrackSeq2.add(seq2[j-1])
            j -= 1
        } else {
            // add characters from both (diagonal indicates match)
            backtrackSeq1.add(seq1[i-1])
            backtrackSeq2.add(seq2[j-1])
            i -= 1
            j -= 1
        }
    }

    // lists in reverse order, reverse them to get correct order
    val backtrackSeq1Ordered = backtrackSeq1.reversed()
    val backtrackSeq2Ordered = backtrackSeq2.reversed()

    // convert to strings
    val backtrackSeq1String = backtrackSeq1Ordered.joinToString("")
    val backtrackSeq2String = backtrackSeq2Ordered.joinToString("")

    // return string pair
    return Pair(backtrackSeq1String, backtrackSeq2String)
}

/**
 * Needleman-Wunsch Implementation in action.
 *
 */
fun main() {
    val match = 1
    val mismatch = -1
    val gap = -2

    val alignmentMatrix = createScoreMatrix(genomeSnippet, testAgainst, match, mismatch, gap)

    val alignmentPair = findAlignment(genomeSnippet, testAgainst, match, mismatch, gap, alignmentMatrix)

    println("Aligned Sequence 1: ${alignmentPair.first}")
    println("Aligned Sequence 2: ${alignmentPair.second}")

    println("Alignment Score: ${alignmentMatrix[alignmentMatrix.n-1,alignmentMatrix.m-1]}")
}