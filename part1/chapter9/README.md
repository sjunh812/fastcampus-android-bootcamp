# 🎵 음악 재생 앱
<img src="https://user-images.githubusercontent.com/79048895/222658184-f02e1c58-d424-499c-ba7b-37c4dffbb741.gif" width="324" height="702" />
<br>

- `MediaPlayer`를 이용하여 음원 재생
- `Service`를 이용하여 음원 재생
- `Notification`을 이용하여 음원 컨트롤러 제공
- `BroadcastReceiver`를 이용하여 `LOW_BATTERY` 이벤트 수신
<br>

## 구현 기능
1. 로컬에 저장된 음원 재생
    - `MediaPlayer`
    - `Serive`를 이용해 다른 앱이 켜져있어도 재생 유지
    - `Notification`(알림창)으로 음원 컨트롤 → `PendingIntent`
2. 배터리가 부족해지면 메세지 출력
    - `BroadcastReceiver`를 이용해 이벤트 수신
<br> 
