import java.lang.StringBuilder

fun main(args: Array<String>) {
    // Collections
    val set = hashSetOf(1,2,3)
    val list = arrayListOf(1,3,5)
    val map = hashMapOf(1 to "one", 5 to "five", 10 to "ten")

    println("Last element in the list is ${list.last()}")
    println("Max in the set is: ${set.max()}")

    // Extension function
    println("Last letter of 'Kotlin' is ${"Kotlin".lastChar()}")
    println("Last two letters of 'Kotlin' is ${"Kotlin".lastTwoLetters()}")

    println(list.joinToString(separator = "-", prefix = "(", postfix = ")"))



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