# 📺 뉴스 앱 
<img src="https://user-images.githubusercontent.com/79048895/226814175-3f4d6c44-3744-4600-90d8-2d7a68908806.gif" width="324" height="702" /> 
<img src="https://user-images.githubusercontent.com/79048895/226814165-9fdb1b0d-da62-4c21-8e24-0c83a7a05caa.gif" width="324" height="702" />

- `Retrofit`을 통해 네트워크 통신 구현
- `Material Design` 활용 (`Chip`, `TextInputLayout`)
- `Jsoup`을 이용해 크롤링 기능 구현
- `Glide`를 이용해 이미지 로딩
- `Lottie`를 이용해 애니메이션 처리
<br>

## 구현 기능

1. Google News 에서 뉴스 정보 가져오기 (RSS 데이터)
    - 이미지, 타이틀로 구성된 `CardView`
3. 정치, 경제, 사회, 정보기술, 스포츠 주제별 뉴스 가져오기
    - `Chip`을 이용하여 태그 UI 구성
5. 검색한 키워드에 관련된 뉴스 가져오기
6. 뉴스 상세보기 → `WebView`
<br>

## 유의사항
Google News 에서 `og:image` 를 일괄로 Google News 로고로 변경하여,  
뉴스의 thumbnail 데이터가 노출되지 않는 문제가 있다.  
<br>

## Tikxml
https://github.com/Tickaroo/tikxml  

`Retrofit` 과 함께 사용할 수 있는 **XML Parser**  

0.8.15 버전이 최신 버전이지만, 안정적이지 못해 0.8.13 사용을 권장  
이외에도 `Retrofit` 에서 지원하는 XML Parser가 아래 두개가 있다.
```
Simple XML: com.squareup.retrofit2:converter-simplexml
JAXB: com.squareup.retrofit2:converter-jaxb
```
하지만 `Simple XML` 은 **Deprecated** 되었고,  
`JAXB` 는 Android를 지원하지 않는다.  

따라서 XML Parsing 이 필요한 상황이라면 위 `Tikxml` 을 차선책으로 사용하는게 좋지만,  
가급적이면 XML 데이터 포멧 대신 JSON 형식을 사용하는 것이 좋다.  
<br>

## Jsoup
https://jsoup.org/  

Java **HTML Parser**  

HTML을 Parsing 해야할 때 주로 사용한다.  
**크롤링과 관련한 프로젝트를 수행해야 할 경우 유용하다.**  
다만, HTML 데이터를 직접 크롤링하기보다는 서버 통신을 통한 JSON 형식의 데이터를 받는게 더 일반적이므로 자주 사용하지는 않는다.  
<br>

## Glide
https://github.com/bumptech/glide  

안드로이드 이미지 로딩 라이브러리  

매우 쉽게 네트워크를 통해 이미지를 받아올 수 있도록 도와주는 라이브러리이다.  
**이미지 캐싱 처리**가 가능하기 때문에 네트워크 이미지를 불러올 때 뿐만 아니라, 로컬 이미지를 불러올 때 상황에서도 사용이 용의하다.  
gif 이미지를 불러올 때도 유용하게 사용할 수 있다.  
<br>

## Lottie
https://airbnb.io/lottie/#/  

Airbnb 에서 만든 애니메이션 처리 라이브러리  

JSON 파일 형식으로 데이터를 불러와 간편하게 개발자가 디자이너에게 전달받은 애니메이션을 그려줄 수 있다.  
<br>

## EditText 포커스 해제 및 키보드 내리기
`EditText` 범위 밖에 터치 이벤트가 발생했을 때
```Kotlin
override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    val view = currentFocus

    view?.takeIf { v -> v is TextInputEditText && ev.action == MotionEvent.ACTION_UP }
        ?.let { et ->
            val x = ev.rawX     // 터치 이벤트 발생 위치(x)
            val y = ev.rawY     // 터치 이벤트 발생 위치(y)
            val outLocation = IntArray(2)   // EditText의 위치를 저장할 배열

            et.getLocationOnScreen(outLocation) // EditText 위치 정보 가져오기

            if (x < outLocation[0] || x > outLocation[0] + et.width || y < outLocation[1] || y > outLocation[1] + et.height) {
                et.clearFocus()     // 포커스 해제
                imm.hideSoftInputFromWindow(et.windowToken, 0)      // 키보드 내리기
            }
        }

    return super.dispatchTouchEvent(ev)
}
```


