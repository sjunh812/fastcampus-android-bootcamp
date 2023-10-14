package com.example.chapter2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinViewModel : ViewModel() {

    private var _passwordLiveData = MutableLiveData<CharSequence>()
    val passwordLiveData: LiveData<CharSequence>
        get() = _passwordLiveData

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
        password.replace(0, password.length, "")
        _passwordLiveData.value = password.toString()
    }
 }