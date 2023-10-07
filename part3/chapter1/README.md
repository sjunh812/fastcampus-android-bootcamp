# 🙃 얼굴 인식 서비스
- `Module`
- `CameraX`
- `Permission`  
- `Google Vision`
- `Paint`
- `Bezier Curves`
- `PathMeasure`
<br>

## 구현 기능
- 카메라 권한
- 얼굴 인식
- 얼굴 변화 감지(윙크, 웃기)
- Face Overlay 커스텀 뷰
<br>

## Bazier Curves
```
프랑스의 공학자 피에르 베지에(Pierre Bézier)의 이름을 딴 곡선. 영어식으로 베지어 곡선으로 읽기도 한다. 
2개 이상의 점으로 정의되는 매개변수 곡선이며, 점 몇개로 곡선을 특정할 수 있는 성질 때문에 CAD, 컴퓨터 그래픽 등 컴퓨터 환경, 
특히 벡터 그래픽에서 곡선을 표현하는 데 널리 쓰인다.
(참고:나무위키)
```

![image](https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/86284f44-8346-4829-afcb-b0befe34f194)

### 3차 베지에 곡선을 이용한 Path 그리기 예제
```kotlin
fun setSize(rectF: RectF, sizeF: SizeF, pointF: PointF) {
    val topOffset = sizeF.width / 2
    val bottomOffset = sizeF.width / 5

    with(facePath) {
        reset()
        moveTo(pointF.x, rectF.top) // 얼굴 윤곽선을 그릴 시작위치.
        cubicTo(    // 오른쪽 반원 그리기.
            rectF.right + topOffset,
            rectF.top,
            rectF.right + bottomOffset,
            rectF.bottom,
            pointF.x,
            rectF.bottom
        )
        cubicTo(    // 왼쪽 반원 그리기.
            rectF.left - bottomOffset,
            rectF.bottom,
            rectF.left - topOffset,
            rectF.top,
            pointF.x,
            rectF.top
        )
        close()
    }
    postInvalidate()
}
```

<br>
