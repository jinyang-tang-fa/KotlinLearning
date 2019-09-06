package rationals

import java.math.BigInteger

data class Rational(val numerator: BigInteger, val denominator: BigInteger): Comparable<Rational> {
    operator fun plus(other: Rational): Rational = (numerator.times(other.denominator).plus(denominator.times(other.numerator))).divBy(denominator.times(other.denominator))

    operator fun minus(other: Rational): Rational = (numerator.times(other.denominator).minus(denominator.times(other.numerator))).divBy(denominator.times(other.denominator))

    operator fun times(other: Rational): Rational = (numerator.times(other.numerator)).divBy(denominator.times(other.denominator))

    operator fun div(other: Rational): Rational = (numerator.times(other.denominator)).divBy(denominator.times(other.numerator))

    operator fun unaryMinus(): Rational = Rational(numerator.negate(), denominator)

    override operator fun compareTo(other: Rational): Int = (numerator.times(other.denominator)).compareTo(denominator.times(other.numerator))

    operator fun rangeTo(end: Rational): ClosedRange<Rational> {
        return object : ClosedRange<Rational> {
            override val endInclusive: Rational = end
            override val start: Rational = this@Rational
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        val simplifiedOne = simplify(this)
        val simplifiedTwo = simplify(other as Rational)

        return simplifiedOne.numerator == simplifiedTwo.numerator && simplifiedOne.denominator == simplifiedTwo.denominator

    }

    override fun toString(): String {
        return when {
            denominator == 1.toBigInteger() || numerator.rem(denominator) == 0.toBigInteger() -> numerator.div(denominator).toString()
            else -> {
                val simplified = simplify(this)

                if ((simplified.numerator < 0.toBigInteger() && simplified.denominator < 0.toBigInteger()) || simplified.denominator < 0.toBigInteger()) {
                    simplified.numerator.negate().toString() + "/" + simplified.denominator.negate().toString()
                }else {
                    simplified.numerator.toString() + "/" + simplified.denominator.toString()
                }
            }
        }
    }





}

fun simplify(r: Rational): Rational {
    val maxCommonFactor = maxCommonFactor(r.numerator, r.denominator)
    return Rational(r.numerator.div(maxCommonFactor), r.denominator.div(maxCommonFactor))
}

fun maxCommonFactor(a: BigInteger, b: BigInteger): BigInteger = if (b != 0.toBigInteger()) maxCommonFactor(b, a % b) else a

infix fun Int.divBy(other: Int) = Rational(this.toBigInteger(), other.toBigInteger())

infix fun Long.divBy(other: Long) = Rational(this.toBigInteger(), other.toBigInteger())

infix fun BigInteger.divBy(other: BigInteger) = Rational(this, other)

fun String.toRational(): Rational {
    val nums = split("/")
    return when {
        nums.size == 1 -> Rational(nums[0].toBigInteger(), 1.toBigInteger())
        else -> Rational(nums[0].toBigInteger(), nums[1].toBigInteger())

    }
}



fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}