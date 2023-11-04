# 🏦 금융 서비스
<img src="https://github.com/sjunh812/fastcampus-android-bootcamp/assets/79048895/89354246-7d42-41c1-97b1-d8fefa883d05" width="324" height="702" /> 

- `GridLayout`
- `BindingAdapter` 
- `TextInputLayout`
- `CountDownTimer`
- `SmsReceiver`
<br>

## 구현 기능
- 보안키패드
  - shuffle random keyboard
  - 정규 표현식을 이용한 데이터 무결성 체크
- 휴대폰 본인인증
  - `TextInputLayout` 및 `Chip` 을 활용한 UI 구성
  - `SmsRetriever` 를 이용하여 SMS 권한없이, SMS 메시지 가져오기
<br>

## SMS Retriever API
[SMS Retriever API 공식문서](https://developers.google.com/identity/sms-retriever/request?authuser=0&hl=ko)  

1. 종속성 추가
```
implementation 'com.google.android.gms:play-services-auth-api-phone:18.0.1'
```
2. SMS Retriever 구현 (`BroadcastReceiver`)
- 문자 내용이 140 byte 를 초과하면 안된다.
- 문자 맨 앞에 `<#>` 가 포함이 되어야 한다.
- 맨 마지막에 앱을 식별하는 11글자 해시코드가 존재해야 한다.
```kotlin
class AuthOtpReceiver : BroadcastReceiver() {

    private var listener: OtpReceiveListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            intent.extras?.let { bundle ->
                val status = bundle.get(SmsRetriever.EXTRA_STATUS) as Status
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val otpSms = bundle.getString(SmsRetriever.EXTRA_SMS_MESSAGE, "")
                        Log.e("sjh", "onReceive :: otpSms >> $otpSms")
                        if (listener != null && otpSms.isNotEmpty()) {
                            val otp = PATTERN.toRegex().find(otpSms)?.destructured?.component1()
                            if (!otp.isNullOrBlank()) {
                                Log.e("sjh", "onReceive :: otp >> $otp")
                                listener!!.onOtpReceived(otp)
                            }
                        }
                    }
                }
            }
        }
    }

    fun setOtpReceiveListener(receiveListener: OtpReceiveListener) {
        this.listener = receiveListener
    }

    fun doFilter() = IntentFilter().apply {
        addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
    }

    interface OtpReceiveListener {
        fun onOtpReceived(otp: String)
    }

    companion object {
        private const val PATTERN = "^<#>.*\\[Sample\\].+\\[(\\d{6})\\].+\$"
    }
}
```
```kotlin
private fun startSmsReceiver() {
    SmsRetriever.getClient(this@VerifyOtpActivity).startSmsRetriever().also { task ->
        task.addOnSuccessListener {
            if (smsReceiver == null) {
                smsReceiver = AuthOtpReceiver().apply {
                    setOtpReceiveListener(this@VerifyOtpActivity)
                }
                ContextCompat.registerReceiver(this, smsReceiver, smsReceiver!!.doFilter(), ContextCompat.RECEIVER_EXPORTED)
            }
        }
        task.addOnFailureListener {
            stopSmsReceiver()
        }
    }
}

private fun stopSmsReceiver() {
    if (smsReceiver != null) {
        unregisterReceiver(smsReceiver)
        smsReceiver = null
    }
}
```

- `BroadcastReceiver` 를 정적으로 선언하는 방법
```
<receiver android:name=".MySMSBroadcastReceiver" android:exported="true"
          android:permission="com.google.android.gms.auth.api.phone.permission.SEND">
    <intent-filter>
        <action android:name="com.google.android.gms.auth.api.phone.SMS_RETRIEVED"/>
    </intent-filter>
</receiver>
```
<br>
