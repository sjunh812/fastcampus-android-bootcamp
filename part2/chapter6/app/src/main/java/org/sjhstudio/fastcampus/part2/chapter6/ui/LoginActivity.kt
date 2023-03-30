package org.sjhstudio.fastcampus.part2.chapter6.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ActivityLoginBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.User
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_URL
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS_FCM_TOKEN
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS_USER_ID
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS_USER_NAME
import org.sjhstudio.fastcampus.part2.chapter6.util.Validation
import org.sjhstudio.fastcampus.part2.chapter6.util.showToastMessage

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference

    companion object {
        private const val LOG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database(DB_URL).reference
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            etEmail.addTextChangedListener { text ->
                emailValidation(text.toString())
            }

            btnSignUp.setOnClickListener {
                signUp(etEmail.text.toString(), etPassword.text.toString())
            }

            btnLogin.setOnClickListener {
                login(etEmail.text.toString(), etPassword.text.toString())
            }
        }
    }

    // 회원가입
    private fun signUp(email: String, password: String) {
        if (!validate(email, password)) return

        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@LoginActivity) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공
                    showToastMessage("회원가입에 성공했습니다.")
                    login(email, password, signUp = true)
                } else {
                    // 회원가입 실패
                    Log.e(LOG, task.exception.toString())
                    showToastMessage("회원가입에 실패했습니다.")
                }
            }
    }

    // 로그인
    private fun login(email: String, password: String, signUp: Boolean = false) {
        if (!validate(email, password, loginValidation = true)) return

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@LoginActivity) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    Firebase.auth.currentUser?.let { currentUser ->
                        Firebase.messaging.token.addOnCompleteListener { task ->
                            val token = task.result
                            val updateMap = mutableMapOf<String, Any>(
                                DB_USERS_USER_ID to currentUser.uid,
                                DB_USERS_FCM_TOKEN to token
                            )

                            // 회원가입시, 닉네임을 이메일로 초기화
                            if (signUp) updateMap[DB_USERS_USER_NAME] = email

                            database.child(DB_USERS).child(currentUser.uid)
                                .updateChildren(updateMap)
                                .addOnSuccessListener { Log.d(LOG, "유저정보 쓰기 성공") }
                                .addOnFailureListener { Log.e(LOG, "유저정보 쓰기 실패: $it") }

                            navigateToMainActivity()
                        }
                    }
                } else {
                    // 로그인 실패
                    Log.e(LOG, task.exception.toString())
                    showToastMessage("로그인에 실패했습니다.")
                }
            }
    }

    private fun validate(
        email: String,
        password: String,
        loginValidation: Boolean = false
    ): Boolean {
        if (!emailValidation(email)) return false
        if (!passwordValidation(password, loginValidation)) return false

        return true
    }

    private fun emailValidation(email: String): Boolean {
        when (Validation.validateEmail(email)) {
            null -> {
                binding.etEmail.requestFocus()
                handleInputError(binding.textInputLayoutEmail, "이메일을 입력해주세요.")
            }
            false -> {
                binding.etEmail.requestFocus()
                handleInputError(binding.textInputLayoutEmail, "이메일 형식이 맞지 않습니다.")
            }
            true -> {
                handleInputSuccess(binding.textInputLayoutEmail)
                return true
            }
        }

        return false
    }

    private fun passwordValidation(password: String, loginValidation: Boolean): Boolean {
        when (Validation.validatePassword(password)) {
            null -> {
                binding.etPassword.requestFocus()
                handleInputError(binding.textInputLayoutPassword, "비밀번호를 입력해주세요.")
            }
            false -> {
                if (loginValidation) {
                    handleInputSuccess(binding.textInputLayoutPassword)
                    return true
                }
                binding.etPassword.requestFocus()
                handleInputError(binding.textInputLayoutPassword, "올바르지 않은 비밀번호입니다.")
                showToastMessage("영문 숫자 포함 8자이상 20이내의 비밀번호를 입력해주세요.")
            }
            true -> {
                handleInputSuccess(binding.textInputLayoutPassword)
                return true
            }
        }

        return false
    }

    private fun handleInputSuccess(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }

    private fun handleInputError(textInputLayout: TextInputLayout, message: String) {
        textInputLayout.error = message
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}