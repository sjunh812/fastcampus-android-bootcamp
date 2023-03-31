# 💬 채팅 앱 
<img src="https://user-images.githubusercontent.com/79048895/229039082-ef023bb6-8ddc-46a3-9f2d-d2e4d7d45e8f.gif" width="324" height="702" /> 

- `Firebase`
  - Realtime Database
  - Authentication
  - Cloud Messaging (FCM)
- `OkHttp3` 를 이용해 FCM 서버로 데이터 전달
<br>

## 구현 기능
1. 회원가입 및 로그인 
    - **Firebase Authentication**
    - 이메일, 비밀번호 유효성 검사 (정규식)
2. 유저목록, 채팅, 마이페이지
    - **Firebase Realtime Database**를 이용해 DB 구현
    - 채팅간 알림에는 **Firebase Cloud Messaging (FCM)** 이용
    - 마이페이지에서는 닉네임과 상태메시지 수정 및 로그아웃 기능
<br>

## Firebase
https://firebase.google.com/?hl=ko

구글에서 만든 모바일 및 애플리케이션 개발 플랫폼, 다양한 플렛품을 지원  
(Android, iOS 및 Web, Flutter, C++, 게임, 서버, 기타 등등)  

파이어베이스는 **애플리케이션 개발자가 더 나은 애플리케이션을 개발할 수 있도록 도와주는 개발 플랫폼**이다.  
1. 빌드 : 개발과정, 더 빠르게 시장에 진출하고 사용자에게 가치를 전달 
    - 서버 관리 없이 백엔드 가동 (Serveless)
    - 일반적인 앱 개발 문제를 쉽게 해결
    - 손쉽게 확장하여 수백만명의 사용자를 지원
      - `Cloud Firestore` : 클라우드에 데이터 저장, 동기화, 명시적 쿼리 작성, (Serverless 앱 빌드)
      - `Realtime Database` : 실시간 동기화, Json 데이터를 저장하는 DB (Serverless 앱 빌드)
      - `Remote Config` : 동작 제어
      - `Cloud Functions` : 서버를 따로 관리 안할 수 있도록 서버 로직을 작성 및 실행
      - `Cloud Messaging` : 푸시 메시지 (일명 FCM)
      - `Firebase ML` : 머신러닝
      - 등등
2. 출시 및 모니터링 : 짧은 시간에 훨씬 수월하게 앱 품질을 상향
    - 테스트, 분류, 문제 해결 프로세스 간소화
    - 문제를 조기에 정확하게 파악하여, 우선순위를 정하고 안정성 및 성능 문제를 해결
      - `Google Analytics`
      - `Remote Config`
      - `Performance Monitoring`
      - `Test Lab`
      - `App Distribution`
3. 참여 : 앱 경험 최적화 및 고객 만족도 유지
    - 사용자를 파악하여 더 효과적으로 지원하고, 사용자층을 유지
    - 아이디어를 테스트하고 새롭고 유용한 정보를 파악
    - 다양한 세그먼트에 맞게 앱을 맞춤 설정
      - `Remote Config`
      - `Google Analytics`
      - `A/B Testing`
      - `Authentication`
      - `Cloud Messaging`
      - `Crashlytics`
      - `Dynamic Links`
      - `In-App Messaging` 
<br>

## 유효성 검사
`TextInputLayout` 및 `TextInputEditText`를 활용한 유효성 검사 로직 → **정규식 이용**
```Kotlin
import java.util.regex.Pattern

object Validation {

    fun validateEmail(email: String): Boolean? {
        val emailRegex =
            "^[_a-zA-Z\\d-]+(\\.[_a-zA-Z\\d]+)*@[a-zA-Z\\d]+(\\.[a-zA-Z\\d]+)*(\\.[a-zA-Z]{2,})$"
        val emailPattern = Pattern.compile(emailRegex)

        return when {
            email.isEmpty() -> null
            !emailPattern.matcher(email).matches() -> false
            else -> true
        }
    }

    fun validatePassword(password: String): Boolean? {
        val passwordRegex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"
        val passwordPattern = Pattern.compile(passwordRegex)

        return when {
            password.isEmpty() -> null
            !passwordPattern.matcher(password).matches() -> false
            else -> true
        }
    }

    fun validateNickname(nickname: String): Boolean? {
        val nicknameRegex = "^[a-zA-Z가-힣\\d]{2,12}$"
        val nicknamePattern = Pattern.compile(nicknameRegex)

        return when {
            nickname.isEmpty() -> null
            !nicknamePattern.matcher(nickname).matches() -> false
            else -> true
        }
    }
}
```
