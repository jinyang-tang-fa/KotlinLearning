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

    println("Color rgb for RED is: ${Color.RED.rgb()}")



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
}


