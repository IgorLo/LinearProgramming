package lab3

object GoldenSech {

    /**
     * Ниже записать ваши значения
     * **/

    //Тут ваши коэффициенты в функции
    private val functionParams: IntArray = intArrayOf(-16, -34, 24, 6, 288)
    //Это ваш вектор X на текущем шаге
    private val startPoints: DoubleArray = doubleArrayOf(7.2091, 6.0695)
    //Это направление (градиент-вектор)
    private val directionVector: DoubleArray = doubleArrayOf(-79.0244, 48.2927)
    //Границы для поиска шага
    private val startStepEdges: DoubleArray = doubleArrayOf(0.0, 1.0)
    //Это не трогать
    private val GOLDEN = 0.6180339887498948482
    //Это максимальное кол-во шагов
    private val numberOfSteps = 100
    //Это точность с которой находится шаг
    private val percision = 0.001

    //ЗАПУСТИТЬ ЭТО!
    @JvmStatic
    fun main(args: Array<String>) {
        var a = startStepEdges[0]
        var b = startStepEdges[1]
        for (i in 1..numberOfSteps) {
            val rightGold = a + (b - a) * GOLDEN
            val leftGold = a + (b - a) * (1.0 - GOLDEN)
            val rightFun = calculateFunFromStep(rightGold)
            val leftFun = calculateFunFromStep(leftGold)
            if (rightFun < leftFun){
                b = rightGold
            } else {
                a = leftGold
            }
            printStep(leftGold, rightGold, leftFun, rightFun, i)
            if ((rightGold - leftGold) <= percision){
                val actualStep = (rightGold+leftGold)/2
                println("-------------------\nУсловие Останова выполнено, t2 - t1 <= ${percision}")
                println("Значение t = ")
                println("новый вектор X = [${startPoints[0] + directionVector[0] * actualStep}]")
                println("                 [${startPoints[1] + directionVector[1] * actualStep}]")
                println("F(${actualStep}) = ${calculateFunFromStep(actualStep)}")
                break
            }
        }

    }

    private fun printStep(t1 : Double, t2 : Double, f1 : Double, f2 : Double, step : Int){
        println("------- ${step} Шаг -------")
        println("t1 = ${t1}")
        println("t2 = ${t2}")
        println("F(t1) = ${f1}")
        println("F(t2) = ${f2}")
    }

    private fun calculateFunFromStep(step: Double): Double {
        val x1 = startPoints[0] + directionVector[0] * step
        val x2 = startPoints[1] + directionVector[1] * step
        return calculateFunFromArgs(x1, x2)
    }

    private fun calculateFunFromArgs(x1: Double, x2: Double): Double {
        return functionParams[0] * x1 * x1 + functionParams[1] * x2 * x2 + functionParams[2] * x1 * x2 + functionParams[3] * x1 + functionParams[4] * x2
    }

}