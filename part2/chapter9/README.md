# 📍 위치공유 앱
<img src="https://github.com/sjunh812/algorithm/assets/79048895/9e080530-564c-44b4-9e7d-1aca9bf7c56d" width="324" height="702" /> <img src="https://github.com/sjunh812/algorithm/assets/79048895/23cc5bb9-ce1a-4b96-a079-bf9624d44974" width="324" height="702" />
<img src="https://github.com/sjunh812/algorithm/assets/79048895/f627a08b-3af5-4f4d-a252-9bfa164e0fe9" width="324" height="702" /> <img src="https://github.com/sjunh812/algorithm/assets/79048895/674a491a-c56b-456b-b1f0-4d0a70ff5479" width="324" height="702" />

- `Google Map`
- `Google Location API`
- `Kakao Auth SDK`  
- `Firebase Auth`
- `Firebase Realtime Database`
- `Glide`
- `Lottie`
- `View Animation`
<br>

## 구현 기능
- 카카오 로그인
- 지도에서 현재 이동 중인 사람들의 위치를 확인
  - 카카오 로그인에서 가져온 프로필 이미지를 이용하여 마커 표현
- 지도에서 특정 사람에게 감정 표현
  - `Lottie` + `View Animation`
<br>

## Google Map
[Google Map Documentation](https://developers.google.com/maps/documentation/android-sdk?hl=ko)

Google 에서 제공하는 Android 용 지도 서비스  
마커, 다각형, 오버레이를 지도에 추가하여, 지도 위치에 대한 정보를 추가로 제공하거나, 사용자 상호작용을 지원  

**지도 API 를 사용하기 위해서는 결제 계정 등록이 필수**  

<br>

## Kakao Auth SDK
[카카오 로그인](https://developers.kakao.com/docs/latest/ko/kakaologin/android)

- 카카오 로그인을 이용하여 OAuth 로그인 구현
- OAuth 는 비밀번호를 제공하지 않고, 다른 웹사이트 상의 정보에 대한 접근 권한을 부여할 수 있는 공통적인 수단으로 사용되는, 접근 위임을 위한 개방형 표준
- 구글, 페이스북, 애플, 카카오톡 로그인 등이 이에 해당하며, OAuth 로그인을 통해 발급 받은 토큰을 통해 해당 서버에서 부여받은 권한에 따른 정보를 얻어올 수 있음.
<br>

## GPS 사용
사용자의 현재 위치를 받아오기 위해, **Google Location API** 를 이용  
1. `ACCESS_FINE_LOCATION` 및 `ACCESS_COARSE_LOCATION` 권한 받기  
    - Android 12 부터 앱에서 `ACCESS_FINE_LOCATION` 런타임 권한을 요청하더라도 사용자는 앱이 대략적인 위치 정보만 검색하도록 요청할 수 있으므로, **두 권한 모두 필요**
2. `FusedLocationClient` 가져오기
3. `requestLocationUpdates()` 로 현재 위치 가져오기
4. `lastLocation` 을 통해 마지막 위치 가져오기
<br>
