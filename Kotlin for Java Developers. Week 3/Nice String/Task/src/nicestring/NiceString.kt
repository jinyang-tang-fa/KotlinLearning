package nicestring

fun String.isNice(): Boolean {
    var satisfied = 0
    if (!this.contains("bu") && !this.contains("ba") && !this.contains("be")) {
        satisfied += 1
    }
    if (this.filter{it in listOf('a','e','i','o','u')}.length >= 3) {
        satisfied += 1
    }
    if (this.zipWithNext().filter { it.first == it.second }.isNotEmpty()) {
        satisfied += 1
    }


    return satisfied >= 2


}