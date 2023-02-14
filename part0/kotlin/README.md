# Part0
Part0 강의에서는 **코틀린 언어의 기초 문법** 및 **안드로이드의 기본**에 대해 담고 있다.  
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
만약 변수에서 사용한다면, **null-safe** 할 수 있도록 도와준다.   
ex) `val size = nickname?.length` ← `nickname`이 `null`이라면, `size`는 **length()** 함수 호출 없이 `null` 처리
2. `?:` : `Elvis Operation`으로 `?:` 왼쪽 객체가 **non-null**이라면 해당 객체를 리턴하고, 만약 **null**이라면 `?:` 오른쪽 값을 리턴한다.  
ex) `val result = nickname ?: "없음"`
