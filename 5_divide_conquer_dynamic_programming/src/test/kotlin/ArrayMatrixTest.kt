import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.example.Matrix
import org.example.ArrayMatrix

class ArrayMatrixTest {

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
    fun `get and set operators`() {

        val matrix = ArrayMatrix(3, 3)

        //setting
        matrix[1, 2] = 99.5

        //getting
        val retrievedValue = matrix[1, 2]


        assertEquals(99.5, retrievedValue)
    }

    @Test
    fun `plus operator`() {
        // Arrange
        val matrixA = ArrayMatrix(2, 2).apply {
            this[0, 0] = 1.0; this[0, 1] = 2.0
            this[1, 0] = 3.0; this[1, 1] = 4.0
        }
        val matrixB = ArrayMatrix(2, 2).apply {
            this[0, 0] = 5.0; this[0, 1] = 6.0
            this[1, 0] = 7.0; this[1, 1] = 8.0
        }
        val expected = ArrayMatrix(2, 2).apply {
            this[0, 0] = 6.0; this[0, 1] = 8.0
            this[1, 0] = 10.0; this[1, 1] = 12.0
        }


        val result = matrixA + matrixB


        assertMatricesEqual(expected, result)
    }

    @Test
    fun `minus operator`() {

        val matrixA = ArrayMatrix(2, 2).apply {
            this[0, 0] = 10.0; this[0, 1] = 10.0
            this[1, 0] = 10.0; this[1, 1] = 10.0
        }
        val matrixB = ArrayMatrix(2, 2).apply {
            this[0, 0] = 1.0; this[0, 1] = 2.0
            this[1, 0] = 3.0; this[1, 1] = 4.0
        }
        val expected = ArrayMatrix(2, 2).apply {
            this[0, 0] = 9.0; this[0, 1] = 8.0
            this[1, 0] = 7.0; this[1, 1] = 6.0
        }


        val result = matrixA - matrixB


        assertMatricesEqual(expected, result)
    }

    @Test
    fun `subMatrix functionality`() {
        val largeMatrix = ArrayMatrix(4, 4).apply {
            for (i in 0 until 4) {
                for (j in 0 until 4) {
                    this[i, j] = (i * 4 + j).toDouble() // Fill with 0, 1, 2, ... 15
                }
            }
        }
        val expected = ArrayMatrix(2, 2).apply {
            this[0, 0] = 5.0; this[0, 1] = 6.0
            this[1, 0] = 9.0; this[1, 1] = 10.0
        }


        val result = largeMatrix.subMatrix(1, 1, 2)


        assertMatricesEqual(expected, result)
    }

    @Test
    fun `multiply functionality`() {

        val matrixA = ArrayMatrix(2, 3).apply {
            this[0,0]=1.0; this[0,1]=2.0; this[0,2]=3.0
            this[1,0]=4.0; this[1,1]=5.0; this[1,2]=6.0
        }
        val matrixB = ArrayMatrix(3, 2).apply {
            this[0,0]=7.0; this[0,1]=8.0
            this[1,0]=9.0; this[1,1]=10.0
            this[2,0]=11.0; this[2,1]=12.0
        }
        val expected = ArrayMatrix(2, 2).apply {
            this[0, 0] = 58.0; this[0, 1] = 64.0
            this[1, 0] = 139.0; this[1, 1] = 154.0
        }


        val result = matrixA.multiply(matrixB)


        assertNotNull(result)
        assertMatricesEqual(expected, result!!)
    }

    @Test
    fun `strassenMultiply functionality`() {

        val matrixA = ArrayMatrix(2, 2).apply {
            this[0, 0] = 1.0; this[0, 1] = 2.0
            this[1, 0] = 3.0; this[1, 1] = 4.0
        }
        val matrixB = ArrayMatrix(2, 2).apply {
            this[0, 0] = 5.0; this[0, 1] = 6.0
            this[1, 0] = 7.0; this[1, 1] = 8.0
        }
        val expected = ArrayMatrix(2, 2).apply {
            this[0, 0] = 19.0; this[0, 1] = 22.0
            this[1, 0] = 43.0; this[1, 1] = 50.0
        }


        val result = matrixA.strassenMultiply(matrixB)


        assertNotNull(result)
        assertMatricesEqual(expected, result!!)
    }

    @Test
    fun `toString functionality`() {
        val matrix = ArrayMatrix(2, 2).apply {
            this[0, 0] = 1.2
            this[1, 1] = 3.4
        }


        val stringRepresentation = matrix.toString()

        // checks if contains key values, more resilient and reliable check
        assertTrue(stringRepresentation.contains("1.2"))
        assertTrue(stringRepresentation.contains("3.4"))
        assertTrue(stringRepresentation.contains("\n"))
    }

    @Test
    fun `getN and getM correct dimensions`() {
        val matrix = ArrayMatrix(5, 8)

        val n = matrix.n
        val m = matrix.m

        assertEquals(5, n)
        assertEquals(8, m)
    }
}