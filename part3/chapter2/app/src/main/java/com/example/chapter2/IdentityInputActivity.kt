package com.example.chapter2

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
                tilBirth.isVisible = true
                etBirth.showKeyboard()
            }

            etBirth.doAfterTextChanged {
                if (etBirth.length() > 7) {
                    llGender.isVisible = true
                    etBirth.hideKeyboard()
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
                    btnConfirm.isVisible = true
                    etPhone.hideKeyboard()
                }
            }

            etPhone.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                if (etPhone.length() > 9) {
                    btnConfirm.isVisible = true
                    etPhone.hideKeyboard()
                }
            }
        }
    }
}