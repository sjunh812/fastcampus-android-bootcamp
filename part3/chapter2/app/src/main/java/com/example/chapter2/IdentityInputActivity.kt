package com.example.chapter2

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.chapter2.databinding.ActivityIdentityInputBinding
import com.example.chapter2.util.ViewUtil.hideKeyboard
import com.example.chapter2.util.ViewUtil.setOnEditorActionListener
import com.example.chapter2.util.ViewUtil.showKeyboard
import com.example.chapter2.util.ViewUtil.showKeyboardDelay

class IdentityInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.view = this
        initViews()
        binding.etName.showKeyboardDelay()
    }

    private fun initViews() {
        with(binding) {
            etName.setOnEditorActionListener(EditorInfo.IME_ACTION_NEXT) {
                if (validName()) {
                    tilName.error = null
                    if (tilPhone.isVisible) {
                        btnConfirm.isVisible = true
                    } else {
                        tilBirth.isVisible = true
                        etBirth.showKeyboard()
                    }
                } else {
                    btnConfirm.isVisible = false
                    tilName.error = "2자 이상의 한글을 입력해주세요."
                }
            }

            etBirth.doAfterTextChanged {
                if (etBirth.length() > 7) {
                    if (validBirthday()) {
                        tilBirth.error = null
                        if (tilPhone.isVisible) {
                            btnConfirm.isVisible = true
                        } else {
                            llGender.isVisible = true
                            etBirth.hideKeyboard()
                        }
                    } else {
                        llGender.isVisible = false
                        tilBirth.error = "생년월일 형식이 다릅니다."
                    }
                }
            }

            etBirth.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                if (validBirthday() && etBirth.length() > 7) {
                    btnConfirm.isVisible = tilPhone.isVisible
                    tilBirth.error = null
                } else {
                    tilBirth.error = "생년월일 형식이 다릅니다."
                }
            }

            chipGroupGender.setOnCheckedStateChangeListener { group, checkedIds ->
                if (llTelecom.isVisible.not()) {
                    llTelecom.isVisible = true
                }
            }

            chipGroupTelecom.setOnCheckedStateChangeListener { group, checkedIds ->
                if (tilPhone.isVisible.not()) {
                    tilPhone.isVisible = true
                    etPhone.showKeyboard()
                }
            }

            etPhone.doAfterTextChanged {
                if (etPhone.length() > 10) {
                    if (validPhone()) {
                        tilPhone.error = null
                        btnConfirm.isVisible = true
                        etPhone.hideKeyboard()
                    } else {
                        btnConfirm.isVisible = false
                        tilPhone.error = "전화번호 형식이 다릅니다."
                    }
                }
            }

            etPhone.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                if (validPhone() && etPhone.length() > 9) {
                    btnConfirm.isVisible = true
                    tilPhone.error = null
                    etPhone.hideKeyboard()
                } else {
                    btnConfirm.isVisible = etPhone.length() > 9 && validPhone()
                    tilPhone.error = "전화번호 형식이 다릅니다."
                }
            }
        }
    }

    fun onClickDone() {
        if (validName().not()) {
            binding.tilName.error = "2자이상의 한글을 입력해주세요."
            return
        }

        if (validBirthday().not()) {
            binding.tilBirth.error = "생년월일 형식이 다릅니다."
            return
        }

        if (validPhone().not()) {
            binding.tilPhone.error = "전화번호 형식이 다릅니다."
            return
        }

        startActivity(Intent(this, VerifyOtpActivity::class.java))
    }

    private fun validName() = binding.etName.text.isNullOrBlank().not()
            && REGEX_NAME.toRegex().matches(binding.etName.text!!)

    private fun validBirthday() = binding.etBirth.text.isNullOrBlank().not()
            && REGEX_BIRTHDAY.toRegex().matches(binding.etBirth.text!!)

    private fun validPhone() = binding.etPhone.text.isNullOrBlank().not()
            && REGEX_PHONE.toRegex().matches(binding.etPhone.text!!)

    companion object {
        private const val REGEX_NAME = "^[가-힣]{2,}\$"
        private const val REGEX_BIRTHDAY = "^(19|20)[0-9]{2}(0[1-9]|1[0-9])(0[1-9]|[12][0-9]|3[01])\$"
        private const val REGEX_PHONE = "^01([016789])([0-9]{3,4})([0-9]{4})\$"
    }
}