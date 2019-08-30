# Kotlin Learning

## 1. General on Kotlin
* Statically typed like Java, unlike dynamically typed language such as JRuby
 
	Pros:    
	    1. type is known at compile time, complier can do validation on methods and fields
      2. mispelled names can be detected at compile time instead of run time

* Type inference: `val a = 1`
* Support `nullable` types - reliable code, detecting possible null at compile time
* Support `function` types

* Functional programming
  1. Function as **value**
  2. Immutable objects - state can’t change after creation
  3. More power of abstraction - avoid duplication
  4. **Safe multithreading** - avoid modification of same data from multiple threads
  5. Easier testing - without setting up a lot of env


## 2. Kotlin Basics
* Data classes - a concise syntax for creating **immutable** value objects
* NullPointerException
```Kotlin
   val s: String? = null
```
* ClassCastException
```Kotlin
  If (value is String)
    println(value.toUpperCase())
```

* `If` is expression with value instead of statement
```Kotlin
  if (a > b) a else b
```
* Type inference
```Kotlin
   fun max(a: Int?, b: Int?): Int? = if(a != null && b != null && a > b) a else b
```

* `val` (value) - immutable
* `var` (variable) - mutable
* `reference` itself is immutable, but we can change the object it points to
* `public` is default visibility - omit it
* Different package usage need to import
* Can import function by name

* Enum class - **only place need semicolon in Kotlin**
   Define any methods in the enum class, the semicolon separates the enum constant list from the method definitions

* `when` expression - like `switch` in Java, but with no argument (must have `else` defined within `when` block)
```Kotlin
  fun whenFunction(c1: Color, c2: Color) =
    when {
      (c1 == Color.RED && c2 == Color.YELLOW) -> Color.ORANGE
      (c1 == Color.YELLOW && c2 == Color.BLUE) -> Color.GREEN
      else -> throw Exception("Bad Color")
    }
```
    - If a block is used in `when` condition check, **the last expression is the result**

* Use `:` to mark class *implements* an interface
```Kotlin
  interface Expr
  class Num(val value: Int): Expr
  class Sum(val left: Expr, val right: Expr): Expr
```

* Smart cast: check whether a variable is certain type using `is`, and can directly use this variable without additional casting
```Kotlin
  fun eval(e: Expr): Int {
    if (e is Num) {
      resturn e.value
    }
    if (e is Sum) {
      return eval(e.left) + eval(e.right)
    }
      throw IllegalArgumentException("Unknown Exception")

  }
```

* `while` and `do-while` is the same as Java
```Kotlin
  while(condition) {
    ...
  }
  do {
    ...
  } while (condition)
```

* `ranges` concept is used to do iteration - `..` is used and is *inclusive* which include second value
```Kotlin
  val oneToTen = 1..10
```
```Kotlin
  for (i in 1..1000) {
    ...
  }
  for (i in 100 downTo 1 step 2) {
    ...
  }
  for (i in 0 until 100) { // this will exclusive 100
    ...
  }
```

* Use `for ((key, value) in map)` to iterate through map
* `map[key]` and `map[key] = value` can be used to read and update the map
* Use `in` to check whether a value is in a range, `!in` to check the opposite, both also works in `when` expression
* `throw` is also expression
```Kotlin
  val percentage = 
     if (number in 0..100)
         number
     else
         throw IllegalArgumentException("Number must be between 0 and 100")
```
* `try` keyword is also an expression, and exception doesn't need to be explicitly specified thrown by a function


## 3. Defining and calling functions
* Create new set: `setOf()` (`hashSetOf()` -> default mutable set), same for `arrayListOf()` and `hashMapOf()`
* More methods than that in Java: `last()`, `max()`...
* Specify names of some arguments to **increase readability**, and you can omit some middle arguments (with default value set)
```Kotlin
  jointToString(collection, separator=" ", prefix="(", postfix=")")
```
* Specify default values for parameters in a function declaration to **avoid creating overloads**
* Functions can be declared outside of class -> at the top level of a source file (compiled to `static method`)
* Properties can be placed at the top level of a file as well, will be stored in a `static field` -> can be used to defined constants
```Kotin
  const val UNIX_LINE_SEPARATOR = "\n"
```
same as 
```Kotlin
  public static final String UNIX_LINE_SEPARATOR = "\n";
```
* Extension functions - allow us to add our own method to the existing class, without the need to rewrite the existing code
```Kotlin
  package strings

  fun String.lastChar(): Char = this[this.length - 1]
  fun String.lastTwoLetters(): String = substring(this.length - 2)  // 'this' can be omitted

  println("Kotlin".lastChar())
  println("Kotlin".lastTwoLetters())
```
If using extension function outside of its package, need to import it
```Kotlin
  import strings.lastChar as last. // can rename for easy use
```
* The `static` nature of extensions -> 
1. can extend from a more specific type
2. cannot be overridden in subclasses
```Kotlin
  open class View {
    open fun click() = println("View click")
  }
  class Button: View() {
    override fun click() = println("Button click")
  }
  fun View.showOff() = println("I'm view")
  fun Button.showOff() = println("I'm button")

  val button: View = Button()
```
`button.click()` will give out `Button click`, but `button.showOff()` will still be `I'm view`

* When extending the API of classes: if adding a member function with the same signature as an extension function, **it will always take precedence**

* Extension property - cannot be defined locally (cannot be in a func body)
* Collections in Kotlin are instances of Java library classes, but have much more extended API, because they use *extension functions and properties*

## 4. Classes, objects and interfaces
* Interfaces can contain definitions of **abstract** methods and implementations of **non-abstract** methods, but cannot contain any `state`
* Interface method can have default implementation, it can be overridden, but also can be omitted in implementation
```Kotlin
  interface Clickable {
    fun showOff() = println("I'm clickable")
  }
```
* If a class implement multiple interfaces, and both interfaces has same signature for defalt method, **we must override the method with custom implementation**
* Need to add `open` before class or functions, if you want the class to be inherited and function to be overridden
* If you override an `open` function, it is by default `open`, but can add `final` to make it not being overridden
* **Abstract** members are always open by default, can need to be to be overridden in subclasses
* Default visibility is `public`
1. `public` - visible everywhere
2. `internal` - within module
3. `protected` - within subclasses
4. `private` - within class
* Extension functions cannot get access to `private` or `protectetd` members
* Nested class by default is `static` nested class. We can use `inner` to make it contain a reference to outer class
```Kotlin
  class Outer {
    inner class Inner {
      fun getOuterReference(): Outer = this@Outer
    }
  }
```
* `Sealed` class cannot have inheritors outside of the class, which restrict the possibility of creating subclasses. -> if `when` expression handles all subclasses of a sealed class, we don't need `else`
 branch any more
 * When inheriting a class without providing any constructors, we have to explicitly invoke the constructor of the superclass, **even with no parameters**
 ```Kotlin
  class RadioButton: Button()
 ```
* Interface has **no constructor**, so when implementing an interface, we never put parentheses after its name 
* Multiple secondary constructors can be defined with the `constructor` keyword 





