package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var index: Int = 0, right: Int = 0, wrong: Int = 0

    for (c in secret) {
        if (c == guess[index]) {
            right++;
        }else {
            if (c in guess) {
                wrong++;
            }
        }
        index++;
    }
    return Evaluation(rightPosition = right, wrongPosition = wrong)
}
