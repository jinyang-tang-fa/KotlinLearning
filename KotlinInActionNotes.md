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
```Java
   val s: String? = null
```
* ClassCastException
```Java
	If (value is String)
	 	println(value.toUpperCase())
```

* `If` is expression with value instead of statement
```Java
  if (a > b) a else b
```
* Type inference
```Java
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
```Java
  fun whenFunction(c1: Color, c2: Color) =
    when {
      (c1 == Color.RED && c2 == Color.YELLOW) -> Color.ORANGE
      (c1 == Color.YELLOW && c2 == Color.BLUE) -> Color.GREEN
      else -> throw Exception("Bad Color")
    }
```
    - If a block is used in `when` condition check, **the last expression is the result**

* Use `:` to mark class *implements* an interface
```Java
  interface Expr
  class Num(val value: Int): Expr
  class Sum(val left: Expr, val right: Expr): Expr
```

* Smart cast: check whether a variable is certain type using `is`, and can directly use this variable without additional casting
```Java
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
```Java
  while(condition) {
    ...
  }
  do {
    ...
  } while (condition)
```

* `ranges` concept is used to do iteration - `..` is used and is *inclusive* which include second value
```Java
  val oneToTen = 1..10
```
```Java
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
```Java
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
```Java
  jointToString(collection, separator=" ", prefix="(", postfix=")")
```
* Specify default values for parameters in a function declaration to **avoid creating overloads**
* Functions can be declared outside of class -> at the top level of a source file (compiled to `static method`)
* Properties can be placed at the top level of a file as well, will be stored in a `static field` -> can be used to defined constants
```Java
  const val UNIX_LINE_SEPARATOR = "\n"
```
same as 
```Java
  public static final String UNIX_LINE_SEPARATOR = "\n";
```
* Extension functions - allow us to add our own method to the existing class, without the need to rewrite the existing code
```Java
  package strings

  fun String.lastChar(): Char = this[this.length - 1]
  fun String.lastTwoLetters(): String = substring(this.length - 2)  // 'this' can be omitted

  println("Kotlin".lastChar())
  println("Kotlin".lastTwoLetters())
```
If using extension function outside of its package, need to import it
```Java
  import strings.lastChar
  import strings.lastTwoLetters as lastTwoLetters // can change the name for easy call
```







