package org.example

interface Matrix {
    val n: Int
    val m: Int

    operator fun get(row: Int, col: Int): Double

    operator fun set(row: Int, col: Int, value: Double)

    operator fun plus(other: Matrix): Matrix

    operator fun minus(other: Matrix): Matrix

    fun subMatrix(startRow: Int, startCol: Int, subSize: Int): Matrix

    fun multiply(other: Matrix): Matrix?

    fun strassenMultiply(other: Matrix): Matrix?
}