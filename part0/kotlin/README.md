# Part0
Part0 강의에서는 코틀린 언어의 기초 문법 및 안드로이드의 기본에 대해 담고 있다.  
아래 내용은 강의를 들으면서 **Remind** 해야 할 내용을 정리한 것이다.
## 목차
1. Kotlin
   - [함수](#함수)
   - [변수](#변수)
   - [조건식](#조건식)
   - [반복문](#반복문)
   - [컬렉션](#컬렉션)
   - [Null](#null)
   - [타입체크와 캐스팅](#타입체크와-캐스팅)
   - [람다](#람다)
   - [Scope Function](#scope-function)
   - [초기화 지연](#초기화-지연)
   - [data class, sealed class](#data-sealed-class)
   - [object, companion object](#object-companion-object)
2. Android
   - [앱 구성요소](#앱-구성요소)
   - [Activity Lifecycle](#activity-lifecycle)
   - [View 그려지는 과정](#view-그려지는-과정)
3. Android 개발 환경설정
   - [ktlint](#ktlint)
   - [detekt](#detekt)
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
### Scope Function
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
   ex) `lateinit var name: String`
2. `lazy`, `val`
   - 선언과 동시에 초기화를 해야한다.
   - 호출 시점에 초기화가 이뤄진다. → **메모리를 효율적으로 사용할 수 있다.**  
   ex) `val age: Int by lazy { 10 }`
### data, sealed class
- `data class` 
   - 데이터를 담기 위한 클래스
   - `toString()`, `hashCode()`, `equals()`, `copy()` 함수를 자동으로 생성
      - **override** 하여 직접 구현한 코드를 사용할 수 있음
   - 1개 이상 **Property** 가 있어야함.
   - 데이터 클래스는 `abstract`, `open`, `inner`를 붙일 수 없음.
   - 상속이 불가능
- `sealed class` 
   - 추상클래스로, 상속받은 자식 클래스의 종류를 제한
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
### object, companion object
- `object` 
   - 클래스를 정의함과 동시에 객체를 생성
   - `Singleton`을 쉽게 만들 수 있는 키워드
   - 생성자 사용불가
   - 프로퍼티, 메소드, 초기화 블록은 사용가능
   - 다른 클래스나, 인터페이스를 상속 받을 수 있음.
- `companion object`
   - 자바의 `static`과 동일한 역할
   - 클래스 내에 하나만 생성할 수 있음.
## Android
### 앱 구성요소
- Android 앱의 필수적인 구성요소로, 각각은 시스템이나 사용자가 앱에 들어올 수 있는 진입점
1. Activity
   - 앱과 사용자가 상호작용을 하기 위한 진입점
      - 앱을 실행할 때는 앱을 전체적으로 호출하는 것이 아니라 앱의 액티비티를 호출
   - 모든 앱에 반드시 1개 이상 존재
   - Activity 는 사용자와 상호작용을 위한 `UI`가 있음.
      - 앱이 실행되면, 화면이 표시됨.
      - 사용자의 입력값을 받음(화면 클릭, 더블 클릭, 롱 클릭, 스와이프, 드래그 앤 드랍 등)
      - 사용자에게 제공하고자 하는 내용을 화면에 표시함.
   - [**Lifecycle**](#activity-lifecycle)이 있음. 
2. Service
   - 백그라운드에서 오래 실행되는 작업 수행을 위한 컴포넌트
   - 사용자가 다른 앱으로 전환하더라도 백그라운드에서 계속 실행
   - `UI` 없음
   - 포그라운드 서비스 : 사용자에게 잘보이는 작업. 포그라운드 서비스의 경우, 반드시 **알림**을 표시해야 하며, 사용자가 앱과 상호작용하지 않을 때도 계속 실행됨.
      - ex) 음악 재생
   - 백그라운드 서비스 :  사용자에게 직접 보이지 않는 작업
      - ex) 사용자에게 보이지 않는 작업(저장소 압축, 게임 업데이트, 파일 압축 등)
      - 앱이 API 레벨 `26`이상을 대상으로 할 경우
         - 즉시 실행해야 하는 작업 : `Work Manager`
         - 지연 작업 : `Alarm Manager`
   - 바인드 서비스 : 앱 컴포넌트가 `bindService`를 호출해 서비스를 호출하면 서비스가 바인딩 됨. 바인딩된 서비스는 **클라이언트-서버** 인터페이스를 제공해 서비스와 상호 작용함.  
   여러개가 한꺼번에 바인딩될 수 있고, 바인딩된 컴포넌트가 모두 종료되면, 서비스도 종료됨.  
   ![image](https://user-images.githubusercontent.com/79048895/218927152-0111ce7b-721a-416f-b5b3-cb257b290f93.png)  
3. BroadCastReceiver
   - 안드로이드 OS에서 발생하는 이벤트와 정보를 앱에서 수신할 수 있도록 하는 구성요소
   - `UI` 없음
   - 예시
      - 화면이 꺼졌거나
      - 배터리가 부족하거나
      - 사진을 캡쳐했거나  
4. ContentProvider   
   - 파일 시스템, SQLite 데이터베이스, 웹상이나 앱이 엑세스할 수 있는 다른 모든 영구 저장위치에 저장 가능한 앱 데이터의 공유형 집합을 관리
   - 다른 앱은 `ContentProvider` 를 통해 해당 데이터를 질의하거나, 수정할 수 있음.
   - 예시
      - 연락처 정보
      - 갤러리 이미지/비디오
* `Manifest`
   - 앱의 필수적인 정보를 담고 있는 파일
      - 앱의 패키지 이름
      - 앱의 구성요소
      - 권한
      - 필요한 기능
* `Intent`
   - 구성요소간의 통신을 할 수 있게 하는 역할
   - 앱에 포함된 구성요소 이외에, 다른 앱의 구성요소와도 통신할 수 있음.
      - 명시적 인텐트 : 특정 컴포넌트, 액티비티를 명확히 특정해 실행할 경우
         - ex) `A Actvitiy`에서, `B Activity` 실행을 호출할 경우
      - 암시적 인텐트 : 동작을 특정하긴 했지만, 실행될 대상이 달라질 수 있는 경우
         - ex) 특정 **URL**을 실행이라는 액션을 요청한 경우, 웹 브라우저 기능을 가진 다수의 앱이 호출될 수 있는 경우
### Activity Lifecycle
- 앱의 **완성도**와 **안정성**을 높이기 위해 반드시 알아야함.
   - 다른 앱으로 전환 시, 비정상 종료되는 문제
   - 사용자가 앱을 사용하지 않는데, 시스템 리소스가 소비되는 문제
   - 사용자가 앱을 나갔다가 돌아왔을 때, 진행상태가 저장되지 않는 문제
   - 화면이 가로↔세로 전환될 때, 비정상 종료되거나, 진행상태가 저장되지 않는 문제
- 콜백
   - `onCreate`
      - **필수적으로 구현해야함.**
      - Activity의 생명주기 중 한 번만 발생해야하는 로직을 실행
         - 멤버 변수 정의
         - `UI` 구성 (`setContentView`, xml 레이아웃 파일 정의)
      - `saveInstanceState` 매개변수 수신 → Actviity 이전 저장상태가 포함된 `Bundle` 객체
   - `onStart`
      - Activity가 사용자에게 표시
      - 앱은 Activity를 포그라운드로 보내 상호작용할 수 있도록 준비
   - `onResume`
      - Activity가 포그라운드에 표시되어, 사용자와 상호작용할 수 있는 상태
      - 앱에서 포커스가 떠날 때까지 `onResume` 상태에 머무름.
   - `onPasue`
      - 사용자가 활동을 떠나는 첫 번째 신호
      - 매우 짧음
      - 활동이 포그라운드에 있지 않지만, 잠시 후 다시 시작할 작업을 일시 중지하거나 조정
      - `onPause`를 통해서, 실행중이지 않을 때 필요하지 않은 리소스를 해지할 수 있음.
      - `onPause`에서는, 데이터를 저장하거나, 네트워크 호출, DB의 IO작업을 하면 안됨.
         - 매우 짧은 시간이기 때문에 데이터 저장과 같은 작업완료 보장할 수 없음.
      - 예시
         - 반투명 Activity가 띄워져 포커스는 없지만 화면에 보이는 상태
   - `onStop`
      - Activity가 사용자에게 더 이상 표시되지 않는 상태
      - CPU를 비교적 많이 소모하는 종료 작업을 실행해야함. (`onPasue`❌)
         - DB 저장
      - Activity가 중단되면, Android OS에서 리소스 관리를 위해, 해당 Activity가 포함된 프로세스를 소멸시킬 수 있음.
   - `onDestroy`
      - Activity가 완전히 종료되기 전에 실행
      - 예시
         - `finish` 호출되어, Activity가 종료될 때
         - `configurationChange` (ex 기기회전, 멀티 윈도우) 로 인해, 시스템이 Activity를 일시적으로 소멸시킬 때  

![image](https://user-images.githubusercontent.com/79048895/218963670-7f073c20-ed71-4438-b74d-c9e75c414008.png)
### View 그려지는 과정
- `UI`를 그리는 기본 구성요소
- `CustomView`를 만들기 위함
- **전위순회** 방식을 쓰기 때문에, 부모부터 자식 뷰 순서로 그려짐.  

![image](https://user-images.githubusercontent.com/79048895/218963743-d9e4ec67-fc9b-4063-a8bb-c3094aba611b.png)  
1. meause
   - 뷰의 **크기**를 계산
   - 모든 뷰는 각각 자신의 `width`, `height`를 계산
   - meause 과정에서 `부모-자식`간의 크기 정보 전달을 위해 2가지 클래스 사용
      1. `ViewGroup.LayoutParams` : 자식 뷰가 부모 뷰에게 자신이 어떻게 측정되고 위치를 정할지 요청할 때 사용.
         - `dp`, `px` : 자식 뷰가 원하는 크기
         - `MATCH_PARENT` : 부모 뷰의 크기와 똑같이 자식 뷰 크기 지정
         - `WRAP_CONTENT` : 부모 뷰안에서, content를 표현할 수 있는 **fit**한 크기 지정
      2. `ViewGroup.MeauseSpecs` : 부모 뷰가 자식 뷰에게 요구사항을 전달할 때 사용
         - `UNSPECIFIED` : 부모 뷰는 자식 뷰가 원하는 사이즈로 결정
         - `EXACTLY` : 부모 뷰가 자식 뷰의 크기를 정확히 지정할 때
         - `AT_MOST` : 부모 뷰가 자식 뷰의 최대 크기를 지정할 때
2. layout
   - 뷰의 **위치**를 할당
   - 부모 기준의 상대적인 위치 (`left`, `top`, `right`, `bottom`)을 계산
3. draw
   - 뷰를 그리는 단계
      - `Canvas` : 뷰의 모양을 그리는 객체
      - `Paint` : 뷰의 색상을 칠하는 객체
   - `meause`과 `layout`에서 측정한 크기, 계산한 위치에 뷰를 그림.
   - 언제든지 다시 호출될 수 있음.
      - scroll이나 swipe를 하게되면 뷰는 `onDraw()` 다시 호출
      - **객체 할당과 같이 리소스가 많이 소모되는 로직은 추가하지 말 것**
4. View Update (런타임에 뷰를 다시 그리게 하는 함수)
   - `invalidate` : view에 변화가 생겨, 다시 그려야할 때
      - color 변화 (`draw`)
   - `requestLayout` : view를 처음부터 그려야할 때 
      - 크기에 변화가 생겨 `measure`부터 다시 해야할 때
## Android 개발 환경설정
### ktlint
- lint : 코드를 분석하여, 프로그램 오류, 버그, 스타일 오류, 구조적 문제점을 확인하는 도구
   - 코딩 컨벤션에 따라 코드를 작성했는지 확인해주는 도구
   - `ktlint` : Kotlin 개발 환경에서 사용되는 lint로, 공식 코틀린 코딩 컨벤션과 안드로이드 코틀린 스타일 가이드에 따라 만들어짐
   - `Android lint` : 안드로이드 스튜디오 내, 폴더 선택 > 마우스 오른쪽 버튼 > Analyaze > Inspect
### detekt
- 정적 프로그램 분석(static program analysis) : 프로그램을 실행하지 않고, 소프트웨어를 분석하는 것
- `ktlint` VS `detekt`
   - `ktlint`는 **코딩 컨벤션**을 중점적으로 보고, `detekt`는 **코드의 전체적인 퀄리티를 높이기 위한 분석**을 수행  
   예를 들면, 메소드 길이가 너무 길다거나, 메소드의 depth가 너무 깊다거나 등의 분석을 수행
- https://detekt.dev/

