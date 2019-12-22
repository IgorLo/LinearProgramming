package lab2

import lab2.Constants.ROUND_DECIMALS
import lab2.Utilities.printText as printText
import lab2.Utilities.printVector as printVector
import lab2.Utilities.roundDouble as round

object SimplexTables {

    fun toNextStep(matrix: Array<DoubleArray>): Array<DoubleArray> {
        printText("Пересчитаем симплекс таблицу")

        printText("Проверим на допустимость:")
        val isAllowable = matrixIsAllowable(matrix)
        printText(isAllowable.toString())

        printText("Проверим на оптимальность:")
        val isOptimal = matrixIsOptimal(matrix)
        printText(isOptimal.toString())

        printText("Выберем столбец:")
        val verticalIndex = chooseVerticalIndex(matrix)
        printText(verticalIndex.toString())

        printText("Выберем строку:")
        val horizontalIndex = chooseHorizontalIndex(matrix, verticalIndex)
        printText(horizontalIndex.toString())

        printText("Пересчитаем матрицу")
        val recalculatedMatrix = recalculateStep(matrix, verticalIndex, horizontalIndex)
        return recalculatedMatrix
    }

    fun recalculateStep(matrix: Array<DoubleArray>, verticalIndex: Int, horizontalIndex: Int): Array<DoubleArray> {
        val theChoosenOne = matrix[horizontalIndex][verticalIndex]
        val newMatrix = Array(matrix.size){DoubleArray(matrix[0].size) {0.0}}
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[0].size) {
                if (i == horizontalIndex && j == verticalIndex){
                    newMatrix[i][j] = round(1.0 / theChoosenOne, ROUND_DECIMALS)
                    continue
                }
                if (i == horizontalIndex){
                    newMatrix[i][j] = round(- matrix[i][j] / theChoosenOne, ROUND_DECIMALS)
                    continue
                }
                if (j == verticalIndex){
                    newMatrix[i][j] = round(matrix[i][j] / theChoosenOne, ROUND_DECIMALS)
                    continue
                }
                val mainDiag = theChoosenOne * matrix[i][j]
                val crossDiag = matrix[horizontalIndex][j] * matrix[i][verticalIndex]
                val tempNewElement = mainDiag - crossDiag
                newMatrix[i][j] = round(tempNewElement / theChoosenOne, ROUND_DECIMALS)
            }
        }
        return newMatrix
    }


    private fun chooseHorizontalIndex(matrix: Array<DoubleArray>, verticalIndex: Int): Int {
        val vector = DoubleArray(matrix.size - 1) { Double.MAX_VALUE }
        var indexOfMin = 0
        var min = Double.MAX_VALUE
        val rightVertical = matrix[0].size - 1
        for (i in 0 until matrix.size - 1) {
            vector[i] = -matrix[i][rightVertical] / matrix[i][verticalIndex]
//            if (vector[i] <= vector.min()!! && matrix[i][verticalIndex] < 0) {
//                indexOfMin = i
//            }
            if (vector[i] <= min && vector[i] > 0){
                indexOfMin = i
                min = vector[i]
            }
        }
        println("b/xi")
        printVector(vector)
        return indexOfMin
    }

    private fun chooseVerticalIndex(matrix: Array<DoubleArray>): Int {
        var indexOfMax = 3
        var value = Double.MIN_VALUE
        val lastLineIndex = matrix.size - 1
        for (j in 0 until matrix[0].size - 1) {
            if (matrix[lastLineIndex][j] > value) {
                value = matrix[lastLineIndex][j]
                indexOfMax = j
            }
        }
        return indexOfMax
    }

    fun matrixIsAllowable(matrix: Array<DoubleArray>): Boolean {
        var isAllowable = true
        val j = matrix[0].size - 1
        for (i in 0 until matrix.size - 1) {
            if (matrix[i][j] < 0.0) {
                isAllowable = false
            }
        }
        return isAllowable
    }

    fun matrixIsOptimal(matrix: Array<DoubleArray>): Boolean {
        var isOptimal = true
        val i = matrix.size - 1
        for (j in 0 until matrix[0].size - 1) {
            if (matrix[i][j] > 0.0) {
                isOptimal = false
            }
        }
        return isOptimal
    }

}


