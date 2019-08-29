import java.lang.IllegalArgumentException
import java.lang.StringBuilder

fun main(args: Array<String>) {
    // Collections
    val set = hashSetOf(1,2,3)
    val list = arrayListOf(1,3,5)
    val map = hashMapOf(1 to "one", 5 to "five", 10 to "ten")

    println("Last element in the list is ${list.last()}")
    println("Max in the set is: ${set.max()}")

    // call extension function
    println("Last letter of 'Kotlin' is ${"Kotlin".lastChar()}")
    println("Last two letters of 'Kotlin' is ${"Kotlin".lastTwoLetters()}")

    println(list.joinToString(separator = "-", prefix = "(", postfix = ")"))

    // no overriding on extension func
    val button: View = Button()
    button.click()
    button.showOff()

    // extension properties
    val sb = StringBuilder("Kotlin?")
    println("Old last char is: ${sb.lastChar}")
    sb.lastChar = '!'
    println("New last char is: ${sb.lastChar}")

    // arbitrary number of arguments
    arbitraryList(1,2,3,4)
    val combineList = listOf("args: ", *args)
    println("Combined list is: $combineList")

    // split string
    val originalString = "12.345-6.A"
    println(originalString.split(".", "-"))
    println(originalString.split("\\.|-".toRegex()))   // can also pass in a regular expression

    // regex
    val bookPath = "/Users/yole/kotlin-book/chapter.adoc"
    parsePath(bookPath)

    // local functions and extensions
    val user = User(12, "Alice", "123 ST")
    saveUser(user)
    val invalidUser = User(15, "", "")
    saveUser(invalidUser)   // expect exception



}

// Extension function
fun String.lastChar(): Char = this[this.length - 1]
fun String.lastTwoLetters(): String = substring(this.length - 2)  // 'this' can be omitted


fun <T> Collection<T>.joinToString(
    separator: String = ", ",
    prefix: String = "",
    postfix: String = ""
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

// Overriding and subclasses
open class View {  // by default, a class is "closed", which means it cannot be inherited
    open fun click() = println("View clicked!")
}
class Button: View() {
    override fun click() = println("Button clicked!")
}
fun View.showOff() = println("I'm a View")
fun Button.showOff() = println("I'm a Button")


// extension properties
var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }


// arbitrary number of arguments
fun arbitraryList(vararg num: Int): List<Int> {
    println("There are ${num.size} items in the arbitrary list")
    return num.toCollection(ArrayList())
}

// regular expression
// "/Users/yole/kotlin-book/chapter.adoc"
fun parsePath(path: String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        println("Dictionary: $directory, name: $filename, ext: $extension")
    }
}

// local functions and extensions
class User(val id: Int, val name: String, val address: String)

fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        require(value.isNotEmpty()) { "Can't save user $id" }
    }
    validate(name, "Name")
    validate(address, "Address")
}

fun saveUser(user: User) {
    user.validateBeforeSave()
}

