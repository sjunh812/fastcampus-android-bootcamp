package org.sjhstudio.fastcampus.part2.chapter9

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import org.sjhstudio.fastcampus.part2.chapter9.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val kakaoAccountLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            // 로그인 실패
            Log.e(LOG, "Error login with kakao account : $error")
            error.printStackTrace()
        } else if (token != null) {
            // 로그인 성공
            Log.e(LOG, "Success login with kakao account $token")
        }
    }

    companion object {
        private const val LOG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnKakaoLogin.setOnClickListener { kakaoLogin() }
        }
    }

    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡이 설치되어 있음. → 카카오톡으로 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    // 카카오톡 로그인 에러
                    Log.e(LOG, "Error login with kakao talk : $error")
                    error.printStackTrace()

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        // 의도적인 로그인 취소 (뒤로가기)
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(
                        context = this,
                        callback = kakaoAccountLoginCallback
                    )
                } else if (token != null) {
                    // 로그인 성공
                    Log.e(LOG, "Success login with kakao talk : $token")
                }
            }
        } else {
            // 카카오톡이 설치되어있지 않음. → 카카오계정으로 로그인
            UserApiClient.instance.loginWithKakaoAccount(
                context = this,
                callback = kakaoAccountLoginCallback
            )
        }
    }
}