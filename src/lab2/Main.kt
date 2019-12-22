package lab2

import lab2.Utilities.printDoubleMatrix as printMatrix
import lab2.Utilities.printText as printText

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = arrayOf(
            doubleArrayOf(-7.0, -12.0, 0.0, 0.0, 0.0, 0.0, 84.0),
            doubleArrayOf(1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 2.0),
            doubleArrayOf(-32.0, 24.0, 1.0, 0.0, -7.0, -12.0, 6.0),
            doubleArrayOf(4.0, -48.0, 0.0, 1.0, 1.0, -1.0, 288.0),
            doubleArrayOf(28.0, 24.00, -1.0, -1.0, 6.0, 13.0, -294.0)
        )
        printText("Исходная таблица:")
        printMatrix(input)

        var matrix = input
        while (!SimplexTables.matrixIsOptimal(matrix)){
            matrix = SimplexTables.toNextStep(matrix)
            printMatrix(matrix)
        }
    }

}