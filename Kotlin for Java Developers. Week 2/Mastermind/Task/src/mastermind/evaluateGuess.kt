package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var right = 0
    var wrong = 0
    var index = 0
    var used = booleanArrayOf(false, false, false, false)
    var hasChecked = booleanArrayOf(false, false, false, false)

    // check right letter and right position
    for (c in guess) {
        if (secret[index] == c) {
            right++
            used[index] = true
            hasChecked[index] = true
        }
        index++
    }

    index = 0
    // check right letter and wrong position
    for (c in guess) {
        if (!hasChecked[index] && c in secret) {
            var secondIndex = 0
            for (secondGuess in secret) {
                if (!used[secondIndex] && secondGuess == c) {
                    wrong++
                    used[secondIndex]= true
                    break
                }
                secondIndex++
            }
        }
        index++
    }

    return Evaluation(right, wrong)
}

