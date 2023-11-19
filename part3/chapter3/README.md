# 👛 월렛 서비스
<img src="https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/d29475f7-4613-4aaf-ae23-3efea6aa244e" width="324" height="702" /> 

- `MotionLayout`
- `DataBinding` 
- `BindingAdapter`
- `Shared Elements Transition`
<br>

## 구현 기능
- 카드 선택 화면 
- 카드 상세 화면 
<br>

## Shared Elements Transition
[Shared Elements Transition 소개](https://android-developers.googleblog.com/2018/02/continuous-shared-element-transitions.html)  
### 사용 예시
#### XML
```xml
<com.google.android.material.card.MaterialCardView
    android:id="@+id/card"
    android:layout_width="@dimen/width_card"
    android:layout_height="@dimen/height_card"
    android:layout_marginTop="16dp"
    android:transitionName="card"
    app:cardCornerRadius="8dp"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    tools:backgroundTint="@color/first_card">
```
#### Start
```kotlin
private fun openDetail(view: View, cardName: CharSequence) {
      view.transitionName = "card"
      val optionCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(view, view.transitionName))
      DetailActivity.start(
          context = this,
          cardName = cardName.toString(),
          cardColor = view.backgroundTintList,
          optionCompat = optionCompat
      )
  }
```
#### End
```kotlin
companion object {
    private const val CARD_NAME = "card_name"
    private const val CARD_COLOR = "card_color"

    fun start(
        context: Context,
        cardName: String,
        cardColor: ColorStateList?,
        optionCompat: ActivityOptionsCompat
    ) {
        Intent(context, DetailActivity::class.java).apply {
            putExtra(CARD_NAME, cardName)
            putExtra(CARD_COLOR, cardColor)
        }.run {
            context.startActivity(this, optionCompat.toBundle())
        }
    }
}
```
<br>
