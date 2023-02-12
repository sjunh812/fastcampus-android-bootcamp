# Part0
강의를 들으면서 Remind 해야 할 내용을 담고 있음.
## Kotlin
### 함수
1. `Unit` : 코틀린에서 리턴값이 없는 함수에서의 실제 리턴값으로, 자바의 `void`와 같은 역할로 볼 수 있다.(생략 가능)
2. 함수의 매개변수에 초기값을 지정하여, 자바의 **오버로딩**을 방지할 수 있다.  
   또한 함수 사용시, 매개변수명을 명시함으로써 가독성이 좋아지고, 매개변수 선언 순서에 따른 제약을 없앨 수 있다.
3. 한 줄로 함수를 정의하는 `Single Expression`을 지원한다.
4. `Trailing Comma`는 컴파일 에러를 발생시키지 않는다!
``` kotlin
// 선언한 함수 사용
fun main() {
  val result = test(c = 5, a = 1)
  println(result)
}  

// 매개변수의 초기값을 설정하여, 자바의 오버로딩을 방지할 수 있다!
fun test(a: Int, b: Int = 3, c: Int = 4) = a + b + c

// 아래 함수 생략 가능
// trailing comma 는 컴파일 에러를 발생시키지 않는다.
fun test(a: Int, b: Int,): Int {
  val c: Int = 4
  return a + b + c
}
```
