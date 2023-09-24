# ▶️ 요튜브
<img src="https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/473a5937-1ef5-479b-83e0-1bf22f061107" width="324" height="702" /> 
<img src="https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/3f05cdef-7781-4593-9a49-4171b24ef880" width="324" height="702" />  

- `ExoPlayer`
- `MotionLayout` 
<br>

## 구현 기능
- 유튜브 클론
- 비디오 재생
- 재생화면 확대, 축소 애니메이션
<br>

## ExoPlayer
[ExoPlayer GitHub](https://developer.android.com/training/constraint-layout/motionlayout?hl=ko)  
[ExoPlayer Documentation](https://developer.android.com/guide/topics/media/exoplayer?hl=ko)  

Android 의 하위 수준 미디어 API 를 토대로 개발된 앱 수준의 미디어 플레이어  
Android 프레임워크는 아니지만, Google 에서 별도로 배포하는 오픈소스 프로젝트  
`ExoPlayer` 는 맞춤설정이나 확장성이 매우 높아 다양한 사례를 지원하고,  
Youtube 및 Google Play Movie / TV 등의 Google 앱에서 사용되고 있음.  
<br>

## Touch Event
[Touch Event Documentation](https://developer.android.com/training/gestures/viewgroup?hl=ko)  

### Touch Event 처리과정
`Activity` → `ViewGroup` → `View` 순서로 터치가 전달,  
이후 `View` → `ViewGroup` → `Activity` 순서로 터치가 처리됨.  

`onInterceptTouchEvent()` 를 `true` 로 return 시 터치가 가로채어지고 더 아래로 터치가 내려가지 않음.    
`onTouch()` 를 `true` 로 return 시 터치 이벤트가 처리 되고, 더 위로 터치이벤트가 흘러가지 않음.  

### MotionEvent
[MotionEvent Documentation](https://developer.android.com/reference/android/view/MotionEvent)  

기본적인 구조: `ACTION_DOWN` → `ACTION_MOVE` → `ACTION_UP`
멀티 터치 시 : `ACTION_POINTER_DOWN`, `ACTION_POINTER_UP`
제스처 취소 시 : `ACTION_CANCEL`
터치 : 터치 한개
제스쳐 : 터치가 모여 해석된 동작. 줌인, 줌아웃, 스크롤 등등  

#### ex) GestureDetector 를 이용해 터치이벤트 가로채기
```kotlin
class ExoPlayerMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    var targetView: View? = null
    private val gestureDetector by lazy {
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                Log.e("sjh", "${targetView?.containTouchArea(e1.x.toInt(), e1.y.toInt())}")
                return targetView?.containTouchArea(e1.x.toInt(), e1.y.toInt()) ?: false
            }
        })
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return event?.let {
            gestureDetector.onTouchEvent(it)
        } ?: false
    }
}
```
