package com.example.chapter2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class PinViewModel : ViewModel() {

    private var _passwordLiveData = MutableLiveData<CharSequence>()
    val passwordLiveData: LiveData<CharSequence>
        get() = _passwordLiveData

    private var _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val password = StringBuffer("")

    fun input(number: String) {
        if (password.length < 6) {
            password.append(number)
            _passwordLiveData.value = password.toString()
        }
    }

    fun delete() {
        if (password.isNotEmpty()) {
            password.deleteCharAt(password.lastIndex)
            _passwordLiveData.value = password.toString()
        }
    }

    fun done() {
        if (validPin()) {
            _messageLiveData.value = "설정한 비밀번호는 $password 입니다."
            reset()
        }
    }

    private fun reset() {
        password.replace(0, password.length, "")
        _passwordLiveData.value = password.toString()
    }

    private fun validPin(): Boolean {
        if (password.length < 6) {
            _messageLiveData.value = "비밀번호 6자리를 입력해주세요."
            return false
        }

        if (Pattern.compile("(\\w)\\1\\1").matcher(password.toString()).find()) {
            _messageLiveData.value = "3자리 이상 같은 숫자는 사용하실 수 없습니다."
            reset()
            return false
        }

        var count = 0
        password.reduce { before, after ->
            Log.d("sjh", "before:$before // after:$after")
            if (after - before == 1) {
                if (++count >= 2) {
                    _messageLiveData.value = "연속된 3자리 숫자는 사용하실 수 없습니다."
                    reset()
                    return false
                }
            } else {
                count = 0
            }
            after
        }

        return true
    }
}