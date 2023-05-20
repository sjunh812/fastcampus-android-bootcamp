package org.sjhstudio.flow.chapter10

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.sjhstudio.flow.chapter10.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    val binding: FragmentAuthBinding
        get() = _binding ?: error("ViewBinding Error")

    private val inputMethodManager by lazy {
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        initSignViews(isSignOut = Firebase.auth.currentUser == null)
    }

    private fun initViews() {
        with(binding) {
            btnSignUp.setOnClickListener {
                hideKeyboard(it)
                signUp()
            }

            btnSignInOut.setOnClickListener {
                hideKeyboard(it)
                signInOut()
            }
        }
    }

    private fun initSignViews(isSignOut: Boolean) {
        with(binding) {
            etEmail.setText(Firebase.auth.currentUser?.email)
            etEmail.isEnabled = isSignOut
            textInputLayoutPassword.visibility = if (isSignOut) View.VISIBLE else View.INVISIBLE
            btnSignInOut.text =
                getString(if (isSignOut) R.string.button_sign_in else R.string.button_sign_out)
            btnSignUp.isEnabled = isSignOut
        }
    }

    private fun signUp() {
        val email = binding.etEmail.text ?: ""
        val password = binding.etPassword.text ?: ""

        if (!validateEmail(email.toString(), true)
            || !validatePassword(password.toString(), true)
        ) {
            return
        }

        Firebase.auth.createUserWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.root.showSnackBar("회원가입이 완료되었습니다.")
                    initSignViews(isSignOut = false)
                } else {
                    binding.root.showSnackBar("회원가입에 실패하였습니다.")
                }
            }
    }

    private fun signInOut() {
        val email = binding.etEmail.text ?: ""
        val password = binding.etPassword.text ?: ""

        if (Firebase.auth.currentUser == null) {    // 로그인
            if (!validateEmail(email.toString(), false)
                || !validatePassword(password.toString(), false)
            ) {
                return
            }

            Firebase.auth.signInWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // todo. 로그아웃 버튼으로 변경
                        initSignViews(isSignOut = false)
                    } else {
                        binding.root.showSnackBar("로그인에 실패하였습니다. 이메일 또는 비밀번호를 확인해 주세요.")
                    }
                }
        } else {    // 로그아웃
            Firebase.auth.signOut()
            // todo. 로그인 버튼으로 변경
            initSignViews(isSignOut = true)
        }
    }

    private fun validateEmail(email: String, isSignUp: Boolean): Boolean {
        return when {
            Validation.validateEmail(email) == null -> {
                handleErrorTextInputLayout(binding.textInputLayoutEmail, "이메일을 입력해 주세요.")
                false
            }
            Validation.validateEmail(email) == false && isSignUp -> {
                handleErrorTextInputLayout(binding.textInputLayoutEmail, "이메일 형식이 올바르지 않습니다.")
                false
            }
            else -> {
                handleSuccessTextInputLayout(binding.textInputLayoutEmail)
                true
            }
        }
    }

    private fun validatePassword(password: String, isSignUp: Boolean): Boolean {
        return when {
            Validation.validateEmail(password) == null -> {
                handleErrorTextInputLayout(binding.textInputLayoutPassword, "비밀번호를 입력해 주세요.")
                false
            }
            Validation.validatePassword(password) == false && isSignUp -> {
                handleErrorTextInputLayout(
                    binding.textInputLayoutPassword,
                    "비밀번호는 8자이상 20자이하의 영문숫자를 입력해 주세요."
                )
                false
            }
            else -> {
                handleSuccessTextInputLayout(binding.textInputLayoutPassword)
                true
            }
        }
    }

    private fun handleErrorTextInputLayout(textInputLayout: TextInputLayout, error: String) {
        textInputLayout.error = error
    }

    private fun handleSuccessTextInputLayout(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }

    private fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}