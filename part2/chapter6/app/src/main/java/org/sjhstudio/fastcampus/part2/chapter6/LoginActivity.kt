package org.sjhstudio.fastcampus.part2.chapter6

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnSignUp.setOnClickListener {
                validate()
//                Firebase.auth.createUserWithEmailAndPassword()
            }

            btnLogin.setOnClickListener {

            }
        }
    }

    private fun validate() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        when (Validation.validateEmail(email)) {
            null -> {
                handleInputError(binding.textInputLayoutEmail, "이메일을 입력해주세요.")
                return
            }
            false -> handleInputError(binding.textInputLayoutEmail, "이메일 형식이 맞지 않습니다.")
            true -> handleInputSuccess(binding.textInputLayoutEmail)
        }

        when (Validation.validatePassword(password)) {
            null -> {
                handleInputError(binding.textInputLayoutPassword, "비밀번호를 입력해주세요.")
                return
            }
            false -> {
                handleInputError(binding.textInputLayoutPassword, "올바르지 않은 비밀번호입니다.")
                Toast.makeText(this, "영문 숫자 포함 8자이상 20이내의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            true -> handleInputSuccess(binding.textInputLayoutPassword)
        }
    }

    private fun handleInputSuccess(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }

    private fun handleInputError(textInputLayout: TextInputLayout, message: String) {
        textInputLayout.error = message
    }
}