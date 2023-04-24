# 🍴 대동맛집도 
<img src="https://user-images.githubusercontent.com/79048895/233917333-560a780d-4003-4374-aca8-ce085eb7000c.gif" width="324" height="702" />

- Naver Map API
- Naver Open API
- `BottomSheetBehavior`
- `Moshi`
<br>

## 구현 기능
- 지역 검색을 통해 지도에서 맛집의 위치를 확인
  - Naver Map API
  - Naver Open API
- 검색한 맛집에 대한 간단한 정보를 제공
  - `BottomSheetBehavior`
<br>

## Naver Map
[네이버 지도 안드로이드 SDK](https://navermaps.github.io/android-map-sdk/guide-ko/1.html)

네이버 지도 SDK 는 네이버 지도 앱을 비롯한 네이버의 여러 서비스 사용 중인 지도 엔진으로,  
대규모 사용자가 이용하는 서비스에 다년간 적용되어 기능과 안정성이 보장된다.  
또한 개발자 친화적인 API 를 제공하여, SDK 제공하는 강력한 기능을 쉽게 사용할 수 있다.  
<br>  

## Naver Open API
[네이버 API 공통 가이드](https://developers.naver.com/docs/common/openapiguide/)

네이버 플랫폼의 정보를 외부 개발자가 쉽게 이용할 수 있도록 제공하는 Open API  
<br>

## Moshi
[Moshi Github](https://github.com/square/moshi)

`Gson` 과 같이 **직렬화 및 역직렬화** 를 안전하게 할 수 있도록 돕는 square 사에서 만든 라이브러리
- `Kotlin` 친화적
- `Gson` 의 리플렉션 외 **Code-gen** 방식을 지원
- 어뎁터를 커스텀하게 사용 가능 
- `Gson` 에 비해 빠르고 적은 메모리 사용 
- null 이나 알 수 없는 데이터 타입 처리의 편의성  
<br>

## BottomSheetBehavior
[Bottom Sheets(Material Design)](https://m2.material.io/develop/android/components/bottom-sheet-dialog-fragment)

- `CoordinatorLayout` 의 자식 뷰에 대한 플러그인 중 하나
- `app:layout_behavior` 옵션을 사용하여 설정 
- `BottomSheetDialog` 나 `BottomSheetDialogFragment` 도 같은 방식으로 동작

### Behavior 속성
- `behavior_hideable` : 아래로 드래그했을 때, 뷰를 숨길지 여부 (기본값은 false)  
- `behavior_skipCollapsed` : 뷰를 숨길 때, 접히는 상태를 무시할지 여부, `behavior_hideable` 이 `false` 라면 효과가 없음. (기본값은 false) 
- `behavior_draggable` : 드래그하여 뷰를 접거나 펼칠지 여부 
- `behavior_fitToContents` : 펼쳐진 뷰의 높이가 cotent 를 감쌀 것인지 여부, `false` 로 설정할 경우, 뷰가 펼쳐졌을 때 아래로 드래그하면 부모 컨테이너 높이의 절반으로 먼저 접혀짐. (기본값 true)
- `behavior_halfExpandedRatio` : 절반만 펼쳤을 경우, 뷰의 높이를 결정, `behavior_fitToContents` 가 `true` 라면 효과가 없음. (기본값 0.5)
- `behavior_expandedOffset` : 완전히 펼쳐진 경우, 뷰의 offset 을 결정, `behavior_fitToContents` 가 `true` 라면 효과가 없으며, 절반으로 접혔을 경우의 오프셋보다 커야함. (기본값 0dp)
- `behavior_peekHeight` : 뷰가 접힌 상태의 높이 (기본값 auto)

### CoordinatorLayout
- `FrameLayout` 기반의 강력한 상호작용 레이아웃
- 자식 **Behavior** 들과의 이동 및 애니메이션 작용 등을 다룰 때 사용
  - `AppBarLayout` 의 스크롤 시 크기 변경
  - 하단 **Floating Button** 의 스크롤 시 위치 변경 등
  - `BottomSheetBehavior` 사용
<br>

## BottomSheet Callback 구현
`BottomSheet` 내부 content 의 투명도를 스크롤에 따라 조절하는 예제 
```Kotlin
with(binding) {
    ..
    BottomSheetBehavior.from(layoutBottomSheet.root)
        .apply {
            state = BottomSheetBehavior.STATE_COLLAPSED // 접혀있는 상태
            
            addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {}

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // 0.0 접힘 ~ 1.0 펼쳐짐
                    layoutBottomSheet.rvRestaurant.alpha = (slideOffset * 2).coerceAtMost(1f)
                }
            })
        }
    ..
}
```
