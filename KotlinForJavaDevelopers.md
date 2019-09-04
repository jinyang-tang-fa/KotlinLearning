# Kotlin for Java Developers

* `Unit` is like `void` function return type in Java
```Kotlin
	fun displayMax(a: Int, b: Int): Unit {
		println(max(a, b))
	}
```
* `"Kotlin" in "Java".."Scala"` is **true**, because it's actually doing
```Kotlin
	"Scala".comparedTo("Kotlin") <= 0 && "Kotlin".comparedTo("Scala") <=0
```
	where String is being compared by alphabetical order one bit by one

* If we want to catch the exception in Java from Kotlin function, we need to explicitly add `@Throws` before the function
```Kotlin
	@Throws(IOException::class)
	fun bar() { throw IOException() }
``` 
```Java
	//Java
	try {
		DemoKt.bar();
	}catch (IOException e) {
		...
	}
```
* Some Kotlin handy extension functions: `String.jointToString()`, `Array<String>.getOrNull()`, `List.withIndex()`, `Int.until()`, `A.to()`(can be used to create Pair(A,B))， `Char.isLetter()`, `Char.isLetterOrDigit()`, `String.trimMargin()`, `String.trimIndent()`, `String.toRegex()`, `String.toIntOrNull()`, `T.eq()`
* `Nullable types` does not equal to `Optional` -> `Optional` is a wrapper, an extra object is created; while `nullable types` don't create any wrappers 
* Smart cast: 
```Kotlin
	val s: String? = any as? String
```
* Function types and nullability
```Kotlin
	() -> Int?
```
	return type is 	`nullable`
```Kotlin
	(() -> Int)?
```	
	the variable is `nullable`
* Store lambda in a variable:
```Kotlin
	val isEven: (Int) -> Boolean = 
		{ i: Int -> i % 2 == 0}
```
* How to call a nullable function type:
```Kotlin
	val f: (() -> Int)? = null
	// option 1
	if (f != null) { f() }
	// option 2
	f?.invoke()
```