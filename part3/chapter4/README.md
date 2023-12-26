# 🌃 이미지 추출 앱
<img src="https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/c1ebba70-4a3a-4f77-9550-685dc20fc835" width="324" height="702" />  

- `Retrofit2`
- `Coil` 
- `MVC`
- `MVP`
- `MVVM` `RxJava`
- `MVI` `Coroutine` `Flow` `Channel`
<br>

## 구현 기능
- 랜덤 이미지 추출
- 불러온 이미지 개수 계산
<br>

## MVC
![image](https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/daaf1549-9031-417a-8520-0aee0af7b7c0)
### Model

- 데이터를 관리
- 비즈니스 로직 수행

### View

- 유저에게 보일 화면을 표현
- 어떠한 데이터나 로직이 있으면 안됨

### Controller

- **Model** 과 **View** 를 연결
- 유저의 입력을 받고 처리

| 장점 | 단점 |
| --- | --- |
| 가장 구현하기 쉽고 단순함 | Controller 에 많은 코드가 생김 |
| 개발기간이 짧아짐 | 유지보수의 어려움 |
| Model 과 View 를 분리 | View 와 Model 의 결합도 상승 |
| Model 의 비종속성으로 재사용 가능 | 테스트코드의 작성의 어려움 |
<br>

## MVP
![image](https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/f349418e-5707-442f-954d-d69f56310d17)
### Model

- 데이터를 관리
- 비즈니스 로직 수행

### View

- 유저에게 보일 화면을 표현
- Presenter 에 의존적

### Presenter

- **Model** 과 **View** 를 연결
- **View** 에 **Interface** 로 연결

| 장점 | 단점 |
| --- | --- |
| View 와 Model 간의 의존성이 없음 | View 와 Presenter 가 1:1 관계 |
| UI 와 비즈니스 로직 분리 | View 가 많아지면 Presenter 도 많아짐 |
| Unit Test 수월  | 기능이 추가 될수록 Presenter 가 비대해짐 |
<br>

## MVVM
![image](https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/842f579e-548f-4acd-ae5f-715b42440a08)
### Model

- 데이터를 관리
- 비즈니스 로직 수행

### View

- 유저에게 보일 화면을 표현
- ViewModel 의 데이터를 관찰

### ViewModel

- **Model** 과 **View** 를 연결
- `DataBinding` 과 `LiveData` 를 통해 **View** 에 데이터 전달

| 장점 | 단점 |
| --- | --- |
| View 가 데이터를 실시간으로 관찰 | 다른 디자인 패턴에 비해 복잡 |
| 생명주기로 부터 안전 | DataBinding LiveData 등 다른 라이브러리를 필수적으로 학습 |
| View 와 ViewModel 의 결합도가 느슨 |  |
| 모듈별로 분리하여 개발 가능 |  |
| Unit Test 수월 |  |
<br>

## MVI
![image](https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/64f55826-f357-44f2-ae83-a21b4d3f4326)
### Model

- 모든 상태를 나타냄

### View

- 유저에게 보여지는 화면
- 상태를 받아 화면에 표시

### Intent

- 앱 내에서 발생하는 **Action**

### SideEffects

- 상태 변경이 필요없는 API 나 DB 접근 등의 이벤트

| 장점 | 단점 |
| --- | --- |
| 하나의 상태만 관리하기 때문에 상태 충돌이 없다 | 다른 패턴에 비해 러닝커브가 높다 |
| 선순환 구조라 흐름을 이해하기 쉬움 | 작은 변경에도 Intent 를 거쳐야함 |
| 불변 객체이기 때문에 스레드에 안전 | 보일러플레이트 코드가 발생한다 |
