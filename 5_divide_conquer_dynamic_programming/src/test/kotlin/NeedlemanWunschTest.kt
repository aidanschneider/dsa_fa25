import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.example.Matrix
import org.example.ArrayMatrix
import org.example.createScoreMatrix
import org.example.findAlignment

class NeedlemanWunschTest {
    /**
     * Private function that checks the equivalency of two matrices by iterating through the rows-columns.
     *
     * @param expected expected matrix
     * @param actual the actual resulting matrix from an ArrayMatrix function operation
     * @param message the error message returned by this function under different circumstances
     */
    private fun assertMatricesEqual(expected: Matrix, actual: Matrix, message: String? = null) {
        assertEquals(expected.n, actual.n, "Matrix row counts (n) do not match.")
        assertEquals(expected.m, actual.m, "Matrix column counts (m) do not match.")
        for (i in 0 until expected.n) {
            for (j in 0 until expected.m) {
                assertEquals(expected[i, j], actual[i, j], 1e-9, "Mismatch at ($i, $j): $message")
            }
        }
    }

    @Test
    fun `createScoreMatrix correct table`() {
        val seq1 = "AG"
        val seq2 = "A"
        val match = 1
        val mismatch = -1
        val gap = -2

        // expected matrix manually calculated
        val expectedMatrix = ArrayMatrix(3, 2).apply {
            this[0, 0] = 0.0;  this[0, 1] = -2.0
            this[1, 0] = -2.0; this[1, 1] = 1.0
            this[2, 0] = -4.0; this[2, 1] = -1.0
        }


        val resultMatrix = createScoreMatrix(seq1, seq2, match, mismatch, gap)


        assertMatricesEqual(expectedMatrix, resultMatrix)
    }

    @Test
    fun `findAlignment correct backtracking`() {
        val seq1 = "AG"
        val seq2 = "A"
        val match = 1
        val mismatch = -1
        val gap = -2

        // correct score matrix done manually
        val scoreMatrix = ArrayMatrix(3, 2).apply {
            this[0, 0] = 0.0;  this[0, 1] = -2.0
            this[1, 0] = -2.0; this[1, 1] = 1.0
            this[2, 0] = -4.0; this[2, 1] = -1.0
        }

        // expected alignment
        val expectedAlignment = Pair("AG", "A-")

        val resultAlignment = findAlignment(seq1, seq2, match, mismatch, gap, scoreMatrix)

        assertEquals(expectedAlignment, resultAlignment)
    }
}