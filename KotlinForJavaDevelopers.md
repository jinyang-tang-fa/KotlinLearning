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
* The value is stored:
```Kotlin
	val foo1 = run {
		println("Calculating the answer...")
		42
	}

	fun main(args: Array<String>) {
		println("$foo1 $foo1")
	}
```
`Calculating the answer` will be only printed out once, because it is being printed when we assign the value to `foo1`, then it's merely getting this property
* `getter` is calculating the value on the run everytime, without `get()` we store the value in the field
* Mutable extension properties:
```Kotlin
	var StringBuilder.lastChar: Char
		get() = get(length - 1)
		set(value: Char) {
			this.setCharAt(length - 1, value)
		}
```
* `Lazy` property Vs. `lateinit`
```Kotlin
	// Lazy property
	val lazyValue: String by lazy {
		println("computed!")
		"Hello"
	}
```
```Kotlin
	// lateinit
	class KotlinActivity: Activity() {
		lateinit var myData: MyData

		override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			myData = intent.getParcelableExtra("MY_DATA")
		}
		...myData.foo // no need to define myData as non-nullable property
	}
```
* `lateinit` variable cannot be `val`, cannot be `nullable`, cannot be `primitive` type (because only `reference` types might be initilized with `null`)
* `public` and `final` are used by default in Kotlin OOP
* In the constructor, with `var/val` it will become property automatically, else it will just be constructor parameters
* Changing the visibility of constructor or difining a second constructor both require `constructor` keyword
* When overriding a property, it's actually overriding the `getter` instead of `field`
```Kotlin
	open class Parent {
		open val foo = 1
		init {
			println(foo)
		}
	}

	class Child: Parent() {
		override val foo = 2
	}

	fun main() {
		Child()
	}
```
	The output is `0`, because `println()` is called inside `Parent` constructor, but `getFoo` is overriden in `Child` class where the field `foo` hasn't been initialized
* `data class` automatically generates `equals`, `hashCode`, `copy`, `toString`....
* In `data class`, if we want to exclude the property in the automatically generated functions, we can define it in the class body
```Kotlin
	data class User(val email: String) {
		var nickname: String? = null
	}

	val user1 = User("abc@gmail.com")
	user1.nickname = "abc"
	println(user1)  // User(email=abc@gmail.com)

	val user2 = User("abc@gmail.com")
	user2.nickname = "def"
	println(user2 == user1) // true
```
* `sealed` modifier restricts class hierarchy -> all subclasses must be located in the same file (in the `when`, if all subclasses have been covered, no need of `else`)
* `object` expression is used to replace Java's `anonymous classes`
```Kotlin
	window.addMouseListner(
		object: MouseAdapter() {
			override fun mouseClicked(e: MouseEvent) {
				...
			}
			override fun mouseEntered(e: MouseEvent) {
				...
			}
		}
	)
```
	`object expression` is not `singleton`, because a new instance is created for each call
* `inner` modifier cannot be used to `object`, because `object` is a singleton, so only one instance exists, but there might be potentially many instances of the outer class, isn't clear which exact reference should be stored then
* Generic functions:
```Kotlin
	fun <T> List<T>.filter(predicate: (T) -> Boolean): List<T>

	fun use1(ints: List<Int>) {
		ints.filter{it > 0}
	}

	fun use2(strings: List<String>) {
		strings.filter{ it.isNotEmpty()}
	}
```
* Defining upper bound for generic functions:
```Kotlin
	fun <T: Any> List<T>.filter...  // T is non-nullable
```
```Kotlin
	fun <T: Comparable<T>> max(first: T, second: T): T {
		return if (first > second) first else second
	}
```
```Kotlin
	// multiple constraints
	fun <T> ensureTrailingPeriod(seq: T) where T: CharSequence, T: Appendable {
		if (!se.endsWith('.')) {
			seq.append('.')
		}
	}
```
* Why `public` and `final` are by default?
	=> Because we want to make application developer as convenient as possible, but at the same time not hurting library developer as much as possible
* `operator` syntax works only when the private operator is visible
* `run` function -> Runs the block of code(`lambda`) and returns the last expression as the result
```Kotlin
	val foo = run {
		println("Calculating foo ...")
		"foo"
	}
```
* `let` function -> Allows to check the argument for being non-null, not only the receiver
```Kotlin
	// if (email != null) sendEmailTo(email)
	email?.let { e -> sendEmailTo(e) }
	getEmail()?.let { sendEmailTo(it) }
```
```Kotlin
	interface Session {
		val user: User
	}

	fun analyzeUserSession(session: Session) {
		(session.user as? FacebookUser)?.let{
			println(it.accountId)
		}
	}
```
* `takeIf` function -> Returns the receiver object if it satisfies the given predicate, otherwise returns null
```Kotlin
	issue.takeIf {it.status == FIXED}
```
```Kotlin
	person.patronymicName.takeIf(String::isNotEmpty)
```
```Kotlin
	issues.filter { it.status == OPEN }
		  .takeIf(List<Issue>::isNotEmpty)
		  ?.let { println("There're some open issues") }
```
* `repeat` function -> Repeats an action for a given number of times
```Kotlin
	repeat(10) {
		println("Welcome!")
	}
```
* All the four above functions are declared as `inline` functions, so that compiler substitues a body of the function instead of calling it, which can avoid performance overhead. Therefore, it's better only making small functions to be `inline`
* `withLock` function
```Kotlin
	val l: Lock = ...
	l.withLock {
		// access the resouce protected by this lock
	}
```
	How `inline` function `withLock` is defined:	
```Kotlin
	inline fun <T> Lock.withLock(action: () -> T): T {
		lock()
		try {
			return action()
		}finally {
			unlock()
		}
	}
```
* `Collections` eagerlly return the result, while `Sequences` postpone the actual computation (lazy evaluation)
```Kotlin
	val list = listOf(1, 2, -3)
	val maxOddSquare = list
						.asSequence()
						.map {it * it}
						.filter { it % 2 == 1 }
						.max()
```
* `yield` function
```Kotlin
	val numbers = sequence {
		var x = 0
		while (true) {
			yield(x++)
		}
	}
	numbers.take(5).toList() // [0,1,2,3,4]
```
* Some library functions
```Kotlin
	people.count {it.age < 21}
```
```Kotlin
	people.sortedByDescending {it.age}
```
```Kotlin
	people.mapNotNull {
		person -> person.takeIf{ it.isPublicProfile }?.name
	}
```
```Kotlin
	val group = map.getOrPut(person.age) { mutableListOf() }
```