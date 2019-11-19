package lab2

import kotlin.math.round

object Utilities {

    fun roundDouble(number : Double, decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(number * multiplier) / multiplier
    }

    fun printText(text: String) {
        println("".padEnd(text.length * 2, '-'))
        println(text.padStart(text.length/2, ' '))
        println("".padEnd(text.length * 2, '-'))
    }

    fun printDoubleMatrix(matrix: Array<DoubleArray>) {
        val length = getMaxLengthOfElement(matrix)
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[0].size) {
                print(matrix[i][j].toString().padStart(length = length).plus("\t"))
            }
            print("\n")
        }
    }

    private fun getMaxLengthOfElement(matrix: Array<DoubleArray>): Int {
        var maxLength = 0
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[0].size) {
                maxLength =
                    if (matrix[i][j].toString().length > maxLength) matrix[i][j].toString().length else maxLength
            }
        }
        return maxLength
    }

    fun printVector(vector: DoubleArray) {
        for (i in 0 until vector.size) {
            println(vector[i])
        }
    }

}