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

    /*
    Solutions


    val noBadString = setOf("ba", "be", "bu").none{this.contains(it)}
    val hasThreeVowels = count {it in setOf('a', 'e', 'i', 'o', 'u')} >= 3
    val hasDouble = zipWithNext().any{ it.first == it.second}
    val hasDouble2 = windowed(2).any{it[0] == it[1]}  // another way to check hasDouble

    return listOf(noBadString, hasThreeVowels, hasDouble).filter { it == true }.size >=2

     */

}