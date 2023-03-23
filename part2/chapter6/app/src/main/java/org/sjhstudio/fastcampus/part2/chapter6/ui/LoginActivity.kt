package org.sjhstudio.fastcampus.part2.chapter6.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ActivityLoginBinding
import org.sjhstudio.fastcampus.part2.chapter6.showToastMessage
import org.sjhstudio.fastcampus.part2.chapter6.util.Validation

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

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
            etEmail.addTextChangedListener { text ->
                emailValidation(text.toString())
            }

            btnSignUp.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()

                if (!validate(email, password)) return@setOnClickListener

                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if (task.isSuccessful) {
                            // 회원가입 성공
                            showToastMessage("회원가입에 성공했습니다.")
                        } else {
                            // 회원가입 실패
                            Log.e(LOG, task.exception.toString())
                            showToastMessage("회원가입에 실패했습니다.")
                        }
                    }
            }

            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()

                if (!validate(email, password, loginValidation = true)) return@setOnClickListener

                Firebase.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if (task.isSuccessful) {
                            // 로그인 성공
                            navigateToMainActivity()
                        } else {
                            // 로그인 실패
                            Log.e(LOG, task.exception.toString())
                            showToastMessage("로그인에 실패했습니다.")
                        }
                    }
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
                handleInputError(binding.textInputLayoutEmail, "이메일을 입력해주세요.")
            }
            false -> {
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
                handleInputError(binding.textInputLayoutPassword, "비밀번호를 입력해주세요.")
            }
            false -> {
                if (loginValidation) {
                    handleInputSuccess(binding.textInputLayoutPassword)
                    return true
                }
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