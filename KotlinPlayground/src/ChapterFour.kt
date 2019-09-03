import java.io.Serializable

fun main(args: Array<String>) {
    val button = ButtonInChap4()
    button.click()
    button.setFocus(false)
    button.showOff()

    // sealed classes
    println(evalInChap4(ExprInChap4.Sum(ExprInChap4.Num(1), ExprInChap4.Num(5))))

    // implement properties
    println(PrivateUserInChap4("abc", "abc@blackrock.com").nickname)
    println("Subscribing User is: ${SubscribingUser("abc@blackrock.com").nickname}")

    // accessing backing field
    val user = UserAccessBackingField("Alice")
    user.address = "ABC DEF"

    // private setter
    val lengthCounter: LengthCounter? = null
    lengthCounter?.addWord("abcdef")
    // lengthCounter.counter = 5 has compiling error
    println("Word length is: ${lengthCounter?.counter}")

    // universal object methods
    val client1 = Client("Alice", 123456)
    println(client1)
    val client2 = Client("Alice", 123456)
    println("client1 and client2 are equal: ${client1==client2}")
}

interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable")
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus")
    fun showOff() = println("I'm focusable")
}

class ButtonInChap4: Clickable, Focusable {
    override fun click() = println("I was clicked")
    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

interface State: Serializable

interface ViewInChap4 {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

class ButtonInChap4New: ViewInChap4{
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) { }
    class ButtonState: State{  // static nested class
    }
}

// sealed class
sealed class ExprInChap4 {
    class Num(val value: Int): ExprInChap4()
    class Sum(val left: ExprInChap4, val right: ExprInChap4): ExprInChap4()
}

fun evalInChap4(e: ExprInChap4): Int =
    when (e) {
        is ExprInChap4.Num -> e.value
        is ExprInChap4.Sum -> evalInChap4(e.left) + evalInChap4(e.right)
    }

// implement properties declared in interfaces
interface UserInChap4 {
    val email: String
    val nickname: String
        get() = email.substringBefore('@')
}

class PrivateUserInChap4(override val nickname: String, override val email: String): UserInChap4

class SubscribingUser(inputEmail: String): UserInChap4 {
    override val nickname: String
       get() = email.substringBefore('@')
    override val email: String = inputEmail
}

// Accessing a backing field from getter and setter
class UserAccessBackingField(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println("""
                Address was changed for $name:
                "$field" -> "$value".
            """.trimIndent())
            field = value
        }
}

class LengthCounter {
    var counter: Int = 0
        private set // this property cannot be changed outside of the class
    fun addWord(word: String) {
        counter += word.length
    }
}

class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client(name = $name, postalCode = $postalCode)"
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client) {
            return false
        }
        return name == other.name && postalCode == other.postalCode
    }
}





