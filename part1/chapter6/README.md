# ⏱ 스톱워치 앱
<img src="https://user-images.githubusercontent.com/79048895/221150491-8bcf3f5e-3bb4-4724-8dcb-c8b4efed9fb1.gif" width="324" height="702" />
<br>

- `runOnUiThread`을 이용하여 WorkerThread 에서 UI 처리
- `ConstraintLayout-Group`을 이용하여 여러개의 뷰를 일괄처리 (`visibility`)
- `AlertDialog` 활용
- 코틀린 코드로 동적으로 View 생성
<br>

## 구현 기능
1. 스톱워치 기능
    - 0.1초 마다 숫자 업데이트
    - 시작, 일시정지, 정지
2. 정지 전 `AlertDialog` 알림
3. 시작 전 카운트다운 추가 
    - `AlertDialog` + `NumberPicker` 를 이용하여 카운트다운 시간 설정
4. 카운트다운 3초 전 알림음 (`ToneGenerator`)
5. 랩타임 기록
<br>  

## Thread
- 작업 공간
- 메인 스레드 (UI Thread) : 애플리케이션이 실행되면서 안드로이드 시스템이 생성하는 스레드로, UI 를 그리는 역할
- 작업자 스레드 (Worker Thread) : 메인스레드 이외의 스레드
### 규칙
- UI Thread 를 `blocking` 해서는 절대 안됨.
  - 앱이 일정시간 동안 반응이 없을 경우 `ANR`(Application Not Responding) 발생
- Worker Thread 에서 UI 처리를 해서는 안된다. (오직 메인 스레드에서만 작업)

![image](https://user-images.githubusercontent.com/79048895/221151431-eb13d893-37b6-4567-8ab0-7ff7dea0ca17.png)  
### 해결방법
- `Activity.runOnUiThread(Runnable)`
- `View.post(Runnable)`
- `View.postDelayed(Runnable, long)`
- `Handler`
