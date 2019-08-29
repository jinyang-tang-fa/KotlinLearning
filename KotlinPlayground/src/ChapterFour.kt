import java.io.Serializable

fun main(args: Array<String>) {
    val button = ButtonInChap4()
    button.click()
    button.setFocus(false)
    button.showOff()
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

interface ViewInChapter4 {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

class ButtonInChapter4: ViewInChapter4{
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) { }
    class ButtonState: State{  // static nested class
    }
}