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