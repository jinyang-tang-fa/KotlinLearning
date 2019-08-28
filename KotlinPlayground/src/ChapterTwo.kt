import java.io.BufferedReader
import java.io.StringReader
import java.lang.NumberFormatException
import java.util.*

data class Person(val name: String, val age: Int? = null)

fun main(args: Array<String>) {
    val persons = mutableListOf(Person("Alice"), Person("Bob", age = 29))
    val oldest = persons.maxBy { it.age ?: 0 }
    println("The oldest is $oldest")
    println("${max(persons[0].age, persons[1].age)} is larger")
    persons.add(Person(name = "Jack", age = 33))

    //string formatting: string templates
    val name = if (args.isNotEmpty()) args[0] else "Kotlin"
    println("Hello, $name")

    val rectangleOne = Rectangle(1,2)
    println("rectangleOne (height: ${rectangleOne.height}, width: ${rectangleOne.width}) is a square: ${rectangleOne.isSquare}")

    // Enums and when
    println("Color rgb for RED is: ${Color.RED.rgb()}")
    val colorRed = Color.RED
    val colorYellow = Color.YELLOW
    println("RED and YELLOW mixed is: ${colorRed.mixOptimized(colorRed, colorYellow)}")

    // Smart cast
    println("(1+2)+4 = ${evalWithLogging(Sum(Sum(Num(1), Num(2)), Num(4)))}")

    // Iteration
    for (i in 1..100) {
        print(fizzBuzz(i))
    }
    println()
    for (i in 100 downTo 1 step 2) {
        print(fizzBuzz(i))
    }
    println()

    // Iterate through map
    val binaryReps = TreeMap<Char, String>()
    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }
    for ((letter, binary) in binaryReps) {
        println("Binary map: $letter = $binary")
    }

    // Iterate through collection with Index
    val list = listOf("1", "2", "3")
    for ((index, element) in list.withIndex()) {
        println("list index $index: $element")
    }

    // check range
    fun isLetter(c: Char) = c in 'a'..'z' || c in 'A' .. 'Z'
    fun isNotDigit(c: Char) = c !in '0'..'9'
    println("b is Letter: ${isLetter('b')}")
    println("3 is not digit: ${isNotDigit('3')}")

    // Try - catch - finally
    val reader = BufferedReader(StringReader("123"))
    val invalidReader = BufferedReader(StringReader("abc"))
    println("Read Number: ${readNumber(reader)}")
    println("Read Invalid Number: ${readNumber(invalidReader)}")







}

//fun max(a: Int?, b: Int?): Int? {
//    return if (a != null && b != null && a>b) a else b
//}

// type inference
fun max(a: Int?, b: Int?): Int? = if(a != null && b != null && a > b) a else b


// class and variables
class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
    get() = height == width
}

// Enums and when
enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0), ORANGE(255, 165, 0), YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 0, 255);

    fun rgb() = (r * 256 + g) * 256 + b

    fun mixOptimized(c1: Color, c2: Color) =
        when { // no argument
            (c1 == RED && c2 == YELLOW) || (c1 == YELLOW && c2 == RED) -> ORANGE
            (c1 == YELLOW && c2 == BLUE) || (c1 == BLUE && c2 == YELLOW) -> GREEN
            else -> throw Exception("Bad Color")
        }

}

// Smart cast: Combining type checks and casts
interface Expr
class Num(val value: Int): Expr
class Sum(val left: Expr, val right: Expr): Expr


fun eval(e: Expr): Int {
    if (e is Num) {
        return e.value
    }
    if (e is Sum) {
        return eval(e.left) + eval(e.right)
    }
    throw IllegalArgumentException("Unknown Exception")
}

// Polishing eval function
fun evalRefactor(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.left) + eval(e.right)
        else -> throw IllegalArgumentException("Unknown Exception")
    }

// Blocks as branches in when
fun evalWithLogging(e: Expr): Int =
    when (e) {
        is Num -> {
            println("num: ${e.value}")
            e.value
        }
        is Sum -> {
            val left = evalWithLogging(e.left)
            val right = evalWithLogging(e.right)
            println("Sum: $left + $right")
            left + right
        }
        else -> throw IllegalArgumentException("Unknown Exception")
    }

// Iteration
fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}


// Try - catch - finally
fun readNumber(reader: BufferedReader): Int? {
    return try {
        val line = reader.readLine()
        Integer.parseInt(line)
    }
    catch (e: NumberFormatException) {
        null
    }
    finally {
        reader.close()
    }
}

