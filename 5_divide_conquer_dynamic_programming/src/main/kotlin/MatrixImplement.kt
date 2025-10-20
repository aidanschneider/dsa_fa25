@file:JvmName("MatrixKt")
package org.example
import kotlin.random.Random
import kotlin.system.measureTimeMillis

val crossover_point = 64

/**
 * Class implementation of Matrix interface with various matrix arithmetic functions (multiply, subtract, add), and
 * a function to implement Strassen's algorithm.
 *
 * @property n the size of the matrix to be created/operated on.
 *
 */
class ArrayMatrix(override val n: Int, override val m: Int): Matrix {
    // initializing a matrix of size nxn
    private val data: Array<DoubleArray> = Array(n) { DoubleArray(m) }

    /**
     * Returns the value at a given cell (row, column) in the Matrix.
     *
     * @param row the row of the value to return.
     * @param col the column of the value to return.
     * @return The value in the matrix cell.
     */
    override operator fun get(row: Int, col: Int): Double {
        return data[row][col]
    }

    /**
     * Returns the value at a given cell (row, column) in the Matrix.
     *
     * Uses operator function to simplify usage later on.
     *
     * @param row the row of the value to return.
     * @param col the column of the value to return.
     * @return The value in the matrix cell.
     */
    override operator fun set(row: Int, col: Int, value: Double) {
        data[row][col] = value
    }

    /**
     * Adds two matricies together.
     *
     * Uses operator function to simplify usage later on.
     *
     * @param other the other matrix to be added to the current matrix
     * @return newMatrix the sum of the two matrices.
     */
    override operator fun plus(other: Matrix): ArrayMatrix{

        if (this.n != other.n || this.m != other.m) {
            throw IllegalArgumentException("Matrix dimensions must match for addition.")
        }
        val newMatrix = ArrayMatrix(n, m)
        for (i in 0 until n) {
            for (j in 0 until m) {
                newMatrix[i,j] = this[i,j] + other[i,j]
            }
        }
        return newMatrix
    }

    /**
     * Returns the difference between two matrices.
     *
     * Uses operator function to simplify usage later on.
     *
     * @param other the other matrix that will be subtracted from the current matrix.
     * @return newMatrix the difference between the two matrices.
     */
    override operator fun minus(other: Matrix): ArrayMatrix{
        if (this.n != other.n || this.m != other.m) {
            throw IllegalArgumentException("Matrix dimensions must match for addition.")
        }

        val newMatrix = ArrayMatrix(n, m)
        for (i in 0 until n) {
            for (j in 0 until m) {
                newMatrix[i,j] = this[i,j] - other[i,j]
            }
        }
        return newMatrix
    }

    /**
     * Splits a matrix into equally-sized submatrices.
     *
     * @param startRow the starting row from which the submatrices will be made.
     * @param startCol the starting column from which the submatrices will be made.
     * @param subSize the size of the submatrix (will always be square).
     * @return newMatrix the submatrix created.
     */
    override fun subMatrix(startRow: Int, startCol: Int, subSize: Int): Matrix {
        val newMatrix = ArrayMatrix(subSize, subSize)

        for (i in 0 until subSize) {
            for (j in 0 until subSize) {
                newMatrix[i, j] = get(startRow+i, startCol+j)
            }
        }
        return newMatrix
    }

    /**
     * Multiplies two matrices together.
     *
     * @param other the other matrix by which the current matrix will be multiplied.
     * @return resultMatrix the product of the two matrices
     */
    override fun multiply(other: Matrix): Matrix? {
        if (this.m != other.n) {
            return null
        }

        //dot product of the rows and columns of each matrix
        var resultMatrix = ArrayMatrix(this.n, other.m)
        for (i in 0 until n){
            for (j in 0 until other.m) {
                var sum = 0.0
                for (k in 0 until m) {
                    sum += this[i, k] * other[k, j]
                }
                resultMatrix[i,j] = sum
            }
        }
        return resultMatrix
    }

    /**
     * Function implementation of Strassen's Algorithm.
     *
     * @param other the other matrix by which the current matrix will be multiplied using Strassen's Algorithm.
     * @return finalResult the product of the two matrices using Strassen's Algorithm.
     */
    override fun strassenMultiply(other: Matrix): Matrix? {
        if (this.n != other.n || this.m != other.m || this.n != this.m || other.n != other.m) return null

        // matrix must also be power of 2 rows and columns
        val isPowerOfTwo = n > 0 && (n and (n - 1)) == 0
        if (!isPowerOfTwo) {
            println("Warning: Matrix size is not a power of two. Falling back to standard multiplication.")
            return this.multiply(other)
        }

        // base case for recursion
        if (n== 1) {
            val resultMatrix = ArrayMatrix(1, 1)
            resultMatrix[0,0] = this[0,0] * other[0,0]
            return resultMatrix
        }

        // breaking down other and this matrix into sub-matricies
        val newSize = n/2

        val A11 = this.subMatrix(0, 0, newSize)
        val A12 = this.subMatrix(0, newSize, newSize)
        val A21 = this.subMatrix(newSize, 0, newSize)
        val A22 = this.subMatrix(newSize, newSize, newSize)

        val B11 = other.subMatrix(0, 0, newSize)
        val B12 = other.subMatrix(0, newSize, newSize)
        val B21 = other.subMatrix(newSize, 0, newSize)
        val B22 = other.subMatrix(newSize, newSize, newSize)

        // using Strassen's algorithm formulas to calculate the quadrants of the solution matrix
        // we use "!!" to assure the operator that P1-P7 will never be null (since they passed the
        // base case above)
        val P1 = A11.strassenMultiply(B12.minus(B22))!!
        val P2 = (A11.plus(A12)).strassenMultiply(B22)!!
        val P3 = (A21.plus(A22)).strassenMultiply(B11)!!
        val P4 = A22.strassenMultiply(B21.minus(B11))!!
        val P5 = (A11.plus(A22)).strassenMultiply(B11.plus(B22))!!
        val P6 = (A12.minus(A22)).strassenMultiply(B21.plus(B22))!!
        val P7 = (A11.minus(A21)).strassenMultiply(B11.plus(B12))!!

        // combining each formula into the quadrants of the solution matrix
        val C11 = P5 + P4 - P2 + P6
        val C12 = P1 + P2
        val C21 = P3 + P4
        val C22 = P5 + P1 - P3 - P7

        // inserting solution quadrants into final solution matrix
        // note the offsets used to get the correct indicies from the C matricies while using
        // i and j to iterate through the finalResult matrix
        val finalResult = ArrayMatrix(n, m)

        // top left
        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                finalResult[i,j] = C11[i,j]
            }
        }

        // top right
        for (i in 0 until newSize) {
            for (j in newSize until n) {
                finalResult[i,j] = C12[i,j-newSize]
            }
        }

        // bottom left
        for (i in newSize until n) {
            for (j in 0 until newSize) {
                finalResult[i,j] = C21[i-newSize,j]
            }
        }

        // bottom right
        for (i in newSize until n) {
            for (j in newSize until n) {
                finalResult[i,j] = C22[i-newSize,j-newSize]
            }
        }

        return finalResult
    }

    /**
     *
     * Function used to convert matrix to string for easier display in the console.
     *
     *
    */
    override fun toString(): String {
        return data.joinToString(separator = "\n") { row ->
            row.joinToString(separator = "\t", prefix = "[ ", postfix = " ]")
        }
    }

    /**
     *
     * Hybrid version of Strassen matrix multiplication that switches to conventional multiplication for small matricies.
     *
     * @param other the other matrix being multiplied.
     * @return finalResult the resulting matrix after hybrid method
     */
    fun hybridStrassenMultiply(other: Matrix): Matrix? {
        // Basic checks remain the same
        if (this.n != other.n || this.m != other.m || this.n != this.m) return null

        // crossover point, this defines the hybrid moment
        if (n <= crossover_point) {
            return this.multiply(other)
        }

        // must be power of 2
        val isPowerOfTwo = n > 0 && (n and (n - 1)) == 0
        if (!isPowerOfTwo) {
            println("Warning: Matrix size is not a power of two. Falling back to standard multiplication.")
            return this.multiply(other)
        }

        // The rest of the logic is identical to your original strassenMultiply
        val newSize = n / 2

        // using SubMatrix recursive function
        val A11 = this.subMatrix(0, 0, newSize)
        val A12 = this.subMatrix(0, newSize, newSize)
        val A21 = this.subMatrix(newSize, 0, newSize)
        val A22 = this.subMatrix(newSize, newSize, newSize)

        val B11 = other.subMatrix(0, 0, newSize)
        val B12 = other.subMatrix(0, newSize, newSize)
        val B21 = other.subMatrix(newSize, 0, newSize)
        val B22 = other.subMatrix(newSize, newSize, newSize)

        // using recursive call for Strassen multiplication
        val P1 = (A11 as ArrayMatrix).hybridStrassenMultiply(B12.minus(B22))!!
        val P2 = (A11.plus(A12) as ArrayMatrix).hybridStrassenMultiply(B22)!!
        val P3 = (A21.plus(A22) as ArrayMatrix).hybridStrassenMultiply(B11)!!
        val P4 = (A22 as ArrayMatrix).hybridStrassenMultiply(B21.minus(B11))!!
        val P5 = (A11.plus(A22) as ArrayMatrix).hybridStrassenMultiply(B11.plus(B22))!!
        val P6 = (A12.minus(A22) as ArrayMatrix).hybridStrassenMultiply(B21.plus(B22))!!
        val P7 = (A11.minus(A21) as ArrayMatrix).hybridStrassenMultiply(B11.plus(B12))!!

        // recombining
        val C11 = P5 + P4 - P2 + P6
        val C12 = P1 + P2
        val C21 = P3 + P4
        val C22 = P5 + P1 - P3 - P7

        // inserting into final result matrix
        val finalResult = ArrayMatrix(n, m)
        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                finalResult[i, j] = C11[i, j]
            }
        }
        for (i in 0 until newSize) {
            for (j in newSize until n) {
                finalResult[i, j] = C12[i, j - newSize]
            }
        }
        for (i in newSize until n) {
            for (j in 0 until newSize) {
                finalResult[i, j] = C21[i - newSize, j]
            }
        }
        for (i in newSize until n) {
            for (j in newSize until n) {
                finalResult[i, j] = C22[i - newSize, j - newSize]
            }
        }

        return finalResult
    }
}

/**
 * Runtime benchmarking function used to compare conventional and Strassen matrix multiplication.
 */
fun runBenchmarks() {
    // test for matrix sizes from 2x2 up to 1024x1024
    val sizes = (1..10).map { 1 shl it }

    println("--- Algorithm Benchmarks ---")
    println("Size   Conventional (ms)\tStrassen (ms)\tHybrid Strassen (ms)")
    println("------|---------------|-----------------|----------------")

    for (n in sizes) {
        // create two nxn matrices with random values
        val matrixA = ArrayMatrix(n, n)
        val matrixB = ArrayMatrix(n, n)
        val matrixC = ArrayMatrix(n,n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                matrixA[i, j] = Random.nextDouble()
                matrixB[i, j] = Random.nextDouble()
                matrixC[i, j] = Random.nextDouble()
            }
        }

        val conventionalTime = measureTimeMillis {matrixA.multiply(matrixB)}

        val strassenTime = measureTimeMillis {matrixA.strassenMultiply(matrixB)}

        val strassenHybridTime = measureTimeMillis {matrixA.hybridStrassenMultiply(matrixC)}

        println("$n\t\t\t$conventionalTime\t\t\t\t\t\t$strassenTime\t\t\t\t\t\t\t$strassenHybridTime")
    }
}

fun main() {
    runBenchmarks()
}