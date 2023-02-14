# Part0
Part0 강의에서는 [**코틀린 언어의 기초 문법**](#kotlin) 및 **안드로이드의 기본**에 대해 담고 있다.  
아래 내용은 강의를 들으면서 **Remind** 해야 할 내용을 정리한 것이다.
## Kotlin
### 함수
1. `Unit` : 코틀린에서 리턴값이 없는 함수에서의 실제 리턴값으로, 자바의 `void`와 같은 역할로 볼 수 있다. (생략 가능)
2. 함수의 매개변수에 초기값을 지정하여, 자바의 **오버로딩**을 방지할 수 있다.  
   또한 함수 사용시, 매개변수명을 명시함으로써 가독성을 높히고, 매개변수 선언 순서에 따른 제약을 없앨 수 있다.
3. 한 줄로 함수를 정의하는 `Single Expression`을 지원한다.
4. `Trailing Comma`는 컴파일 에러를 발생시키지 않는다!
``` kotlin
fun main() {
  val result = test(c = 5, a = 1)
  println(result)
}  

// 매개변수의 초기값을 설정하여, 자바의 오버로딩을 방지할 수 있다!
fun test(a: Int, b: Int = 3, c: Int = 4) = a + b + c

// 아래 함수는 생략이 가능하다.
// trailing comma 는 컴파일 에러를 발생시키지 않는다.
fun test(a: Int, b: Int,): Int {
  val c: Int = 4
  return a + b + c
}
```
### 변수
1. `val` : value(값)을 뜻하며, 값을 변경할 수 없다.
2. `var` : variable(변경가능한)을 뜻하며, 값을 변경할 수 있다.
### 클래스
1. `open` : 코틀린은 기본적으로 **상속을 비권장하여 상속에 대해 닫혀있다.**   
그러므로 상속을 위해 부모 클래스를 선언할 때, `open` 키워드를 사용해야 한다.   
자식 클래스의 변수를 부모 클래스의 변수로 이용할 때에도 부모 클래스의 변수에 `open` 키워드를 사용해야 한다.  
(자식 클래스의 변수에는 `override` 키워드를 사용한다.)
2. `주 생성자` : **Property** 기능을 가지고 있다. → 자바의 `GET`, `SET`  
주 생성자에는 `constructor` 키워드가 생략되어 있는데,  
만약 **어노테이션**이나 **접근 제어자**(private 등)을 가지면 `constructor` 키워드를 생략할 수 없다!
3. `부 생성자` : 클래스 블록 내에 존재하는 생성자로 `constructor` 키워드를 사용한다. (생략 불가능)
4. `init` : 클래스 초기화에 사용되는 **블록**으로 부 생성자보다 먼저 호출되며, 주로 주 생성자와 함께 사용된다.
``` kotlin
open class User(open val name: String, open var age: Int = 100)

class Kid(override val name: String, override var age: Int) : User(name, age) {

    var gender: String = "male"

    // 클래스에서 제일 먼저 호출
    init {
        println("초기화")
    }

    // 부생성자
    constructor(name: String, age: Int, gender: String) : this(name, age) {
        this.gender = gender
        println("부생성자)
    }
}
```
### 조건식
1. `Expression`으로 사용 가능하다. 즉, 리턴값을 가질 수 있음을 뜻한다.
2. **삼항 연산자**는 지원하지 않는다.
3. `when` : 자바의 `switch`문과 같은 역할을 하며, 숫자 범위나 리스트가 들어갈 수 있다.  
마찬가지로 `Expression`으로 사용 가능하다.
``` kotlin
fun max(a: Int, b: Int) = if (a > b) a else b

fun isHoliday(dayOfWeek: Any) {
    val result = when (dayOfWeek) {
        "토", "일" -> "주말"
        in 2..4 -> "에러1"
        in listOf("월", "화", "수", "목", "금") -> "평일"
        else -> "에러2"
    }
    println(result)
}
```
### 반복문
- for문
   1. 코틀린에서는 for문안 범위를 다양한 방법으로 표현할 수 있다.  
   1부터 10까지 범위를 아래와 같이 나타낼 수 있다.
      - `1..10`
      - `IntRange(1, 10)` 
      - `1 until 11` → `until` 다음 선언되는 수는 포함하지 않는다.
   2. `step` : 증감 범위를 지정한다. (단, 값으로 양수만 넣을 수 있다.)   
   ex) `for (i in 10 downTo 1 step(2)) {...}`
   3. `downTo` : 감소하는 범위에서 사용된다.
```kotlin
// for 문
for(i in 1..10) {
   print("$i ")
}
println()
for (i in IntRange(1, 10)) {
   print("$i ")
}
println()
for (i in 1 until 11) {
   print("$i ")
}
println()
for (i in 10 downTo 1 step(1)) {
   print("$i ")
}
println()
```
- while문 
```kotlin
// while 문
var i = 1
while (i <= 10) {
   print("$i ")
   i++
}
```
### 컬렉션
1. `mutable` : 값의 변경(삽입, 삭제..)이 가능한 컬렉션  
ex) `mutableListOf()`, `mutableMapOf()`
2. `immutable` : 값의 변경이 불가능한 컬렉션  
ex) `listOf()`, `mapOf()`
3. `Indexing Operator`을 사용하여 표현할 수 있다.  
ex) `map.put(3, "c")` → `map[3] = "c"`
4. 자바와 다르게 다양한 타입의 값을 리스트에 넣을 수 있다.  
ex) `listOf(1, "a", 3.14, true)`
- 다양한 **확장 함수**를 지원한다.
   1. `joinToString` : 배열 데이터 출력 형식을 변경해준다. (`separator` = 구분자)
   2. `map` : 원소를 원하는 형태로 변환한다.
   3. `first` : 조건을 만족하는 원소중 첫번째 값 반환한다.
   4. `filter` : 조건을 만족하는 원소들만 필터링한다.
   5. `takeWhile` : 조건을 만족할 때까지 앞에서부터 인수를 취한다.
   6. `dropWhile` : 조건을 만족할 때까지 앞에서부터 인수를 버린다.
```kotlin
fun main() {
    val list = listOf(1, 2, 3)  // 변경 불가능
    val mutableList = mutableListOf(1, 2, 3)    // 변경 가능
    mutableList.add(4)
    println(mutableList[0]) // indexing operator

    val map = mapOf((1 to "a"), (2 to "b")) // 변경 불가능
    val mutableMap = mutableMapOf((1 to "a"), (2 to "b"))   // 변경
    mutableMap[3] = "c" // indexing operator

    val diverseList = listOf(1, "a", 3.14, true)

    list.joinToString(",")  // 배열 데이터 출력 형식을 변경(separator : 구분자)
    list.map { it * 10 }    // 원소를 원하는 형태로 변환
    list.first { it == 1 }  // 조건을 만족하는 원소중 첫번째 값 반환
    list.filter { it != 2 } // 조건을 만족하는 원소들만 필터링
    list.takeWhile { it == 2 }  // 조건을 만족할 때까지 앞에서부터 인수를 취한다.
    list.dropWhile { it == 2 }  // 조건을 만족할 때까지 앞에서부터 인수를 버린다.
}
```
### Null
1. `?` : 타입 뒤에 `?`을 붙이면 타입을 **nullable**하게 처리할 수 있다.   
ex) `var nickname: String? = null`  
**nullable** 변수의 함수를 사용할 때 **null-safe** 할 수 있도록 도와준다.   
ex) `val size = nickname?.length` ← `nickname`이 `null`이라면, `size`는 **length()** 함수 호출 없이 `null` 처리
2. `?:` : `Elvis Operation`으로 `?:` 왼쪽 객체가 **non-null**이라면 해당 객체를 리턴하고, 만약 **null**이라면 `?:` 오른쪽 값을 리턴한다.  
ex) `val result = nickname ?: "없음"`
### 타입체크와 캐스팅
1. `is` : 타입체크에 사용된다.  
ex) `if (nickname is String) { ... }`
2. `as` : 타입캐스팅에 사용된다. `as?`로 사용할 경우, 잘못된 캐스팅이 발생했을 때 `null`을 반환한다.  
(즉, `as?`를 이용하여 타입체크도 할 수 있다.)  
ex) `val result = (a as? String) ?: "fail"`
3. 코틀린에서는 한번 타입체크가 이뤄진 변수에 대하여 자동으로 **스마트 캐스팅**을 해준다. → 별도의 캐스팅이 필요없다!
### 람다
1. **익명 함수** 이다.
2. 변수처럼 사용되서, 함수의 **argument**, **return** 값으로 사용 가능하다.
3. 한번 사용되고, 재사용되지 않는 함수이다.
4. 람다식의 타입을 선언할 때, `(?) -> ?` 형식을 따른다.  
ex) `val lambda: (Int) -> Int = { it * 10 }`  
타입을 따로 명시하지 않고, 구현부에서 타입을 선언하여 사용할 수도 있다.  
ex) `val lambda = { i: Int, j: Int -> i * j }`
5. 람다식을 실행하기 위해서는 입력값을 반드시 입력해줘야 한다.  
ex) `val result = lambda(10)`
6. 구현해야 하는 추상메소드가 하나인(`SAM(Single Abstract Method`) 함수형 인터페이스는 코틀린에서 람다식으로 대체할 수 있다.  
ex) `view.setOnClickListener { ... }`
``` kotlin
override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.activity_main)

   val view = View(this)
   // SAM(Single Abstract Method)
   // Kotlin 에서는 단일 추상 메소드를 람다식으로 표현할 수 있다.
   // 1. java style
   view.setOnClickListener(object : View.OnClickListener {
      override fun onClick(v: View) {
         println("click!!")
      }
   })
   // 2. use lambda
   view.setOnClickListener { println("click!!") }
}
```
### 확장함수
- 기존에 정의되어 있는 클래스에 함수를 추가하는 기능이다. 자바의 경우 함수를 추가할 클래스를 상속 받아서 함수를 추가하였지만,  
코틀린에서는 `확장함수(Extension Function)`를 이용하여 간단히 구현할 수 있다.
``` kotlin
fun main() {
    val a = MyClass()
    a.fuc1()    
    a.fuc2()
    a.fuc3()    // MyClass 클래스에 대한 확장함수
}

class MyClass {

    fun fuc1() = println("1")
    fun fuc2() = println("2")
}

fun MyClass.fuc3() = println("3")
```
### Scope Function (범위 지정함수)
- 코틀린 표준 라이브러리에서 제공하는 **확장함수**
- 목적 : **간결성**, **명료성**, **유지보수 용이성**을 가진다.
- 정의 : 객체의 컨텍스트 내에서 실행 가능한 코드 블럭을 만드는 함수  
호출 시, 임시 범위가 생성되며, 이 범위 안에서는 이름 없이 객체에 접근이 가능하다.
   - `context` : 문맥, 맥락, 전후 사정
- 수신 객체 (receiver) : 확장함수에 호출되는 대상이 되는 값 (객체)
- 수신 객체 지정 람다 (lambda with receiver) : 수신 객체를 명시하지 않고, 람다 본문에서 해당 객체의 함수를 호출할 수 있게 하는 것
- 차이점 : 
   1. 수신 객체 접근 방법 : `this`, `it(local variable)`
   2. return 값 : **수신객체**, **마지막 행(lamda result)**  

| Scope Function     | 객체 참조 방법            | return             | 확장함수 여부       |
| ------------------ | ------------------ | ------------------ | ------------------ |
| `let`              | `it`   | 람다식의 마지막행   | 확장함수로 호출     |
| `run`              | `this`  | 람다식의 마지막행   | 확장함수로 호출     |
| `apply`            | `this`  | 수신객체           | 확장함수로 호출     |
| `also`             | `it`   | 수신객체           | 확장함수로 호출     |
| `with`             | `this`   | 람다식의 마지막행   | 함수의 인자        |   
```
* this : 생략이 가능하며, 바로 객체의 함수로 접근이 가능하다.
* it : Local Variable 로 생략이 불가능하며, 원하는 이름으로 바꿔 사용할 수 있다.
```
- `let` : **null** 체크를 해야할 때, 지역 변수를 명시적으로 표현해야할 때 주로 사용한다.
- `run` : 객체를 초기화 하고 리턴 값이 있을 때 주로 사용한다.
- `apply` : 객체 초기화에 주로 사용한다.
- `also` : 수신객체를 명시적으로 사용하고 싶을 때나 로그를 남길 때 주로 사용한다.
- `with` : 객체 초기화에 주로 사용한다.
### 초기화 지연
- 정의 : 변수를 선언할 때 값을 지정하지 않고, 나중에 지정할 수 있는 방법
- 목적 : 메모리를 효율적으로 사용하기 위해서, **null safe** 한 value를 사용하기 위해서
1. `lateinit`, `var` 
   - 변수 타입을 지정해줘야 한다.
   - **primitive** 타입을 사용할 수 없다 ex) `Int`
   - 선언 후, 나중에 초기화 해줘도 된다.  
   ex) `lateinit var name: String"`
2. `lazy`, `val`
   - 선언과 동시에 초기화를 해야한다.
   - 호출 시점에 초기화가 이뤄진다. → **메모리를 효율적으로 사용할 수 있다.**  
   ex) `val age: Int by lazy { 10 }`
### data, sealed class
- `data class` : 데이터를 담기 위한 클래스
   - `toString()`, `hashCode()`, `equals()`, `copy()` 함수를 자동으로 생성
      - **override** 하여 직접 구현한 코드를 사용할 수 있음
   - 1개 이상 **Property** 가 있어야함.
   - 데이터 클래스는 `abstract`, `open`, `inner`를 붙일 수 없음.
   - 상속이 불가능
- `sealed class` : 추상클래스로, 상속받은 자식 클래스의 종류를 제한
   - 컴파일러가 `sealed class`의 자식 클래스가 어떤 것인지 알고있음.
   - `when`과 함께 쓰일 때, 장점을 느낄 수 있음.
```kotlin
fun main() {
    val cat: Cat = BlueCat
    // 컴파일러가 Cat 의 자식을 알고있음. → else 가 필요없다.
    val result = when (cat) {
        is RedCat -> "red"
        is BlueCat -> "blue"
        is GreenCat -> "green"
        is WhiteCat -> "white"
    }
    println(result)
}

sealed class Cat
object RedCat : Cat()
object BlueCat : Cat()
object GreenCat : Cat()
object WhiteCat : Cat()
```
