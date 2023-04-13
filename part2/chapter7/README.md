# ☀️ 날씨 앱
<img src="https://user-images.githubusercontent.com/79048895/231416729-4d706e09-b368-4e23-8c5a-5b8fa7ae9c2e.gif" width="324" height="702" /> <img src="https://user-images.githubusercontent.com/79048895/231416744-627f2d57-0ac1-43ba-ab47-f14ba20e5228.gif" width="324" height="702" /> 

- `Location`
- `Geocoder`
- `Foreground Service`
- `Android Widget`
- 공공 API
<br>

## 구현 기능
- 공공데이터포털에서 날씨 데이터를 가져와 현재 위치의 지역명, 기온, 하늘상태, 강수확률 및 간단한 예보 구현
- 현재 위치의 간단한 날씨를 알려주는 위젯 구현
<br>

## Location
https://developer.android.com/training/location/permissions?hl=ko  

### 대략적인 위치 vs 정확한 위치
- 대략적인 위치
  - `ACCESS_COARSE_LOCATION` 권한을 통해 받을 수 있음
  - 런타임 권한
  - 오차범위 3km 이내
  - 정확한 위치가 필요 없는 경우, 대략적인 위치권한 사용 권장
- 정확한 위치
  - `ACCESS_FINE_LOCATION` 권한을 통해 받을 수 있음
  - 런타임 권한
  - 오차범위 50m 이내
  - **Android 12 (API 31)** 이후부터는 사용자가 정확한 위치와 대략적인 위치권한 중 선택하여 허용할 수 있으므로, 
**targetSDK가 31이상**일 경우, 두 권한을 동시에 요청해야함

### 포그라운드 위치 vs 백그라운드 위치
- 포그라운드 위치 : 앱을 사용하는 중 요청하는 위치 권한으로 백그라운드 위치권한과는 별개  

- 백그라운드 위치 : `ACCESS_BACKGROUND_LOCATION` 권한 필요  
  - Android 10 (API 29) : 백그라운드 위치를 요청시, 시스템 권한 대화상자에는 항상 허용이라는 옵션이 포함된다.   
  - Android 11 (API 30) : 백그라운드 위치를 요청시, 항상 허용이라는 옵션이 추가되지 않고 설정에서 직접 허용하도록 유도하는 팝업이 뜬다.  
  - Android 12 (API 31) : 백그라운드 위치를 위치권한과 함께 요청하면 권한 요청 팝업이 노출되지 않는다.
따라서 위치권한이 승인된 상태에서 별도로 백그라운드 위치를 요청해야하고, 요청시 앱 권한 설정 페이지로 이동한다.  

![image](https://user-images.githubusercontent.com/79048895/231401747-f57dc11c-5cee-4e8f-a86d-482ff86a62a1.png)  

※ **Fused Location Provider API** : 간편하고 배터리 효율적인 Android용 위치 API  
[Fused Location Provider API | Google Developers](https://developers.google.com/location-context/fused-location-provider?hl=ko)  
<br>

## Foreground Service
앱이 **API 26** 이상을 대상으로 한다면, 앱이 포그라운드에 있지 않을 때 시스템에서 백그라운 서비스 실행에 대한 제한을 적용  

포그라운드 서비스의 경우, 사용자가 서비스가 실행되고 있음을 인식할 수 있도록 상태 표시줄에 알림을 제공 → `Notification`  

![image](https://user-images.githubusercontent.com/79048895/231405004-14ee4890-946e-4682-bbe8-28071699ba7d.png)  
<br>  

## Android Widget
다른 애플리케이션(ex) 홈화면)에 삽입되어 주기적인 업데이트를 받을 수 있는 소형 애플리케이션 뷰  

위젯을 만들기 위해 다음이 필요하다.  
- `AppWidgetProviderInfo` : 앱 위젯의 레이아웃, 업데이트 주기와 같은 메타 데이터를 나타냄. (XML로 정의)
- `AppWidgetProvider` : 브로드캐스팅을 기반으로 앱 위젯을 컨트롤 함. 업데이트, 사용 설정, 사용 중지, 삭제될 때 브로드 캐스트를 수신함.
- 레이아웃 : XML로 정의된 앱 위젯의 초기 레이아웃을 정의  
<br>

## Fused Location Provider API 사용법
- `FusedLocationProviderClient` : 위치 정보를 가져오기 위해 필요한 제공자
- `LocationRequest` : 위치 정보 제공자에 관한 요청 파라미터를 저장 (업데이트 주기, 우선순위)
- `locationSettingsRequest` : Google Play 서비스 및 위치 서비스 API에 연결하여 사용자 기기의 현재 위치 설정을 가져옴.
  - 위치를 요청할 수 있도록 위치 설정이 되어있는지 확인
  - 실패 결과값으로 `onFailure()` 메서드에 전달된 Exception 객체가 `ResolvableApiException` 클래스의 경우,
`startResolutionForResult()` 호출을 통해 사용자에게 위치 설정을 수정할 수 있는 권한을 요청하는 대화상자를 표시할 수 있음.

```Kotlin
private lateinit var fusedLocationClient: FusedLocationProviderClient
private lateinit var locationCallback: LocationCallback
private val locationRequest by lazy {
    LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()
}

private val resolutionResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                startLocationUpdates()
            } 
        }

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ...

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            Log.e(LOG, "${result.locations}")
        
            ...
    
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    ...
}

private fun startLocationUpdates() {
    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) return

    val locationSettingsRequest = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .build()
    val settingsClient = LocationServices.getSettingsClient(this)

    settingsClient.checkLocationSettings(locationSettingsRequest)
        .addOnSuccessListener {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                Log.e(LOG, "ResolvableApiException")
                try {
                    resolutionResultLauncher.launch(
                        IntentSenderRequest.Builder(exception.resolution).build()
                    )
                } catch (sendException: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            } else {
                Log.e(LOG, "UnknownException")
            }
        }
}
```
