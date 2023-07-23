# ☕️ 별다방커피
<img src="https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/48e1cd2a-4a94-4dd4-9bea-814c86a997d6" width="324" height="702" /> <img src="https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/28103fbe-31b7-4462-84fa-34dc50ce8aed" width="324" height="702" />  

- `AppBarLayout`
- `MotionLayout` 
<br>

## 구현 기능
- 스타벅스 앱 클론 (홈, 주문 화면)
- 애니메이션 구현
- JSON 파일을 직접 생성하여 dummy 데이터 사용
<br>

## MotionLayout
[MotionLayout Documentation](https://developer.android.com/training/constraint-layout/motionlayout?hl=ko)  

`ConstraintLayout` 의 서브클래스  
다양한 모션 애니메이션을 쉽게 작성할 수 있도록 도와줌.  
<br>

## AppBarLayout
[AppBarLayout Documentation](https://developer.android.com/reference/com/google/android/material/appbar/AppBarLayout)  

Material Design 의 Appbar 컨셉  
스크롤 또는 제스쳐와 함께 사용해야할 때 사용할 수 있음.  
<br>

## reified 키워드
일반적인 제네릭 함수 body 에서 타입 T 는 컴파일 타임에는 존재하지만 런타임에는 **Type erasure** 때문에 접근할 수 없다.  
따라서 일반적인 클래스에 작성된 함수 body 에서 제네릭 타입에 접근하고 싶다면 genericFunc 처럼 명시적으로 타입을 파라미터로 전달해주어야 한다.  

하지만 **reified type parameter** 와 함께 **inline** 함수를 만들면,  
추가적으로 `Class<T>` 를 파라미터로 넘겨줄 필요 없이 런타임에 타입 T 에 접근할 수 있다.  

```kotlin
inline fun <reified T> Context.readData(fileName: String): T? {
    return try {
        val inputStream = resources.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())

        inputStream.read(buffer)

        Gson().fromJson(String(buffer), T::class.java)
    } catch (e: IOException) {
        null
    }
}
```

`reified` 는 inline 함수와 조합해서만 사용할 수 있다.  
이런 함수는 **컴파일러가 함수의 바이트코드를 함수가 사용되는 모든 곳에 복사**하도록 만든다.  
reified type 과 함께 인라인 함수가 호출되면 컴파일러는 type argument로 사용된 실제 타입을 알고 만들어진 바이트코드를 직접 클래스에 대응되도록 바꿔준다.  
따라서 타입 T 는 런타임 타임에서 복사된 바이트코드에 의해 실제 타입으로 대응될 수 있다.
