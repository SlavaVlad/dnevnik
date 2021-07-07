package well.keepitsimple.functions

import kotlin.math.sqrt

fun main() {

    val solutionSum = solveEquation(1, 9, 9)

    println(solutionSum) // сумма корней в консоль

}

fun solveEquation(a: Int, b: Int, c: Int): Double {

    var discriminant = (b * b) - (4 * a * c) // вычисление дискриминанта

        if (discriminant >= 0) {

            var x1: Double = (-b + sqrt(discriminant.toDouble())) / (2 * a) // первый корень

            var x2: Double = (-b - sqrt(discriminant.toDouble())) / (2 * a) // второй корень

                println("""Дискриминант: $discriminant
    x1 = $x1
    x2 = $x2""")
    //для отладки, ибо пункты останова для слабаков

            return x1 + x2

        } else {

            println("Решений нет") // если решений нет

            return 101.0

        }

}
