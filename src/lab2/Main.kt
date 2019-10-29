package lab2

import lab2.Utilities.printDoubleMatrix as printMatrix
import lab2.Utilities.printText as printText


object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = arrayOf(
            doubleArrayOf(-1.0, -1.4, 0.0, 4.8),
            doubleArrayOf(-3.5, -1.0, 1.0, 8.7),
            doubleArrayOf(351.0, 102.0, -100.0, -870.0)
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