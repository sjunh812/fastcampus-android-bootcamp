# 🎨 웹툰 앱
<img src="https://user-images.githubusercontent.com/79048895/223631176-2c079b84-65f2-4e3a-94f5-17c73f92be51.png" width="324" height="702" />  <img src="https://user-images.githubusercontent.com/79048895/223631354-01f18e5d-c7af-41ad-90d7-accbad87046b.png" width="324" height="702" />  

- `WebView`를 사용하여 네이버 웹툰 화면 띄우기
- `Fragment`를 이용하여 `ViewPager2` 구현 (with `TabLayout`)
- `SharedPreference`를 이용하여 데이터 처리
<br>

## 구현 기능
1. `ViewPager2`를 이용하여 3개의 서로 다른 네이버 웹툰 화면으로 이루어진 `Fragment`를 구성  
  (각 `Fragment` 는 `WebView` 를 전체화면으로 구성)
2. `TabLayout` 과 `ViewPager2` 를 연동하고, 탭 이름을 동적으로 변경
3. 웹툰의 마지막 조회 시점을 로컬에 저장하고, 앱 실행 시 불러옴
<br> 
