package org.sjhstudio.fastcampus.part2.chapter9

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import org.sjhstudio.fastcampus.part2.chapter9.databinding.ActivityLoginBinding
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER_NAME
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER_PROFILE_PHOTO
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants.DB_USER_UID
import org.sjhstudio.fastcampus.part2.chapter9.util.showToastMessage

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var pendingUser: User? = null
    private val emailDialog by lazy {
        EmailBottomSheetDialog { email ->
            pendingUser?.let { user -> signInFirebase(user, email) }
        }
    }

    private val kakaoAccountLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            // 로그인 실패
            Log.e(LOG, "Error login with kakao account : $error")
            error.printStackTrace()

            if (!(error is ClientError && error.reason == ClientErrorCause.Cancelled)) {
                showToastMessage("로그인에 실패하였습니다.")
            }
        } else if (token != null) {
            // 로그인 성공
            Log.e(LOG, "Success login with kakao account $token")
            getKakaoAccountInfo()
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
            btnKakaoLogin.setOnClickListener {
                kakaoLogin()
            }
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
                    getKakaoAccountInfo()
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

    private fun getKakaoAccountInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(LOG, "Error get kakao account : $error")
                error.printStackTrace()
                showToastMessage("로그인에 실패하였습니다.")
            } else if (user != null) {
                Log.e(
                    LOG,
                    "Success get kakao account : email=${user.kakaoAccount?.email}, " +
                            "nickname=${user.kakaoAccount?.profile?.nickname}, " +
                            "profile image=${user.kakaoAccount?.profile?.profileImageUrl}"
                )
                pendingUser = user
                checkKakaoAccountData(user)
            }
        }
    }

    private fun checkKakaoAccountData(user: User) {
        // 이메일이 제대로 내려오는지 확인
        val email = user.kakaoAccount?.email.orEmpty()

        if (email.isEmpty()) {
            // 사용자로부터 직접 이메일을 입력 받아야함.
            emailDialog.show(supportFragmentManager, emailDialog.tag)
        } else {
            signInFirebase(user, email)
        }
    }

    private fun signInFirebase(user: User, email: String) {
        val uid = user.id.toString()

        Firebase.auth.createUserWithEmailAndPassword(email, uid)
            .addOnCompleteListener { signUpResult ->
                if (signUpResult.isSuccessful) {
                    updateFirebaseDatabase(user)
                }
            }
            .addOnFailureListener { signUpException ->
                if (signUpException is FirebaseAuthUserCollisionException) {
                    // 이미 회원가입된 계정
                    Firebase.auth.signInWithEmailAndPassword(email, uid)
                        .addOnCompleteListener { loginResult ->
                            if (loginResult.isSuccessful) {
                                updateFirebaseDatabase(user)
                            }
                        }
                        .addOnFailureListener { loginException ->
                            loginException.printStackTrace()
                            showToastMessage("로그인에 실패하였습니다.")
                        }
                }
            }
    }

    private fun updateFirebaseDatabase(user: User) {
        val uid = Firebase.auth.currentUser?.uid.orEmpty()
        val userMap = mutableMapOf<String, Any>(
            DB_USER_UID to uid,
            DB_USER_NAME to user.kakaoAccount?.profile?.nickname.orEmpty(),
            DB_USER_PROFILE_PHOTO to user.kakaoAccount?.profile?.thumbnailImageUrl.orEmpty()
        )

        Firebase.database.reference.child(DB_USER).child(uid).updateChildren(userMap)
        navigateToMapActivity()
    }

    private fun navigateToMapActivity() {
        startActivity(Intent(this, MapActivity::class.java))
    }
}