# 🎙 녹음기 앱
<img src="https://user-images.githubusercontent.com/79048895/224326405-e3c06caa-ab26-4229-a41b-0d33c6f7151e.jpg" width="324" height="702" /> <img src="https://user-images.githubusercontent.com/79048895/224326387-b032e166-21d3-4e3a-a09d-2aa916ea642b.jpg" width="324" height="702" />  

- `MediaRecorder`를 이용하여 녹음
- `MediaPlayer`를 이용하여 녹음 파일 재생
- `Canvas`를 이용하여 녹음 파형 그리기
<br>

## 구현 기능
1. 녹음 및 녹음 파일 재생
2. 녹음 파형 그리기(녹음 파일 재생시에도 파형 표시)
    - **CustomView**를 생성하여 `Canvas`에 그림
    - 녹음 파일 재생시에도 파형을 표시하기 위해 파형에 대한 데이터를 저장
3. `Handler`를 이용하여 녹음 및 파일 재생 중 타이머 표시
