package com.example.chapter2

import android.os.Bundle
import android.os.CountDownTimer
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.chapter2.databinding.ActivityVerifyOtpBinding
import com.example.chapter2.util.ViewUtil.setOnEditorActionListener
import com.example.chapter2.util.ViewUtil.showKeyboardDelay

class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyOtpBinding

    private var timer: CountDownTimer? = object : CountDownTimer((3 * 60 * 1000), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val min = (millisUntilFinished / 1000) / 60
            val sec = (millisUntilFinished / 1000) % 60
            binding.tvTimer.text = "$min:${String.format("%02d", sec)}"
        }

        override fun onFinish() {
            binding.tvTimer.text = ""
            Toast.makeText(this@VerifyOtpActivity, "입력 제한시간을 초과하였습니다.\n다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.view = this
        initView()
    }

    override fun onResume() {
        super.onResume()
        binding.etOtp.showKeyboardDelay()
    }

    override fun onDestroy() {
        clearTimer()
        super.onDestroy()
    }

    private fun initView() {
        startTimer()
        with(binding) {
            etOtp.doAfterTextChanged {
                if (etOtp.length() >= 6) {
                    stopTimer()
                }
            }
            etOtp.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {

            }
        }
    }

    private fun startTimer() {
        timer?.start()
    }

    private fun stopTimer() {
        timer?.cancel()
    }

    private fun clearTimer() {
        stopTimer()
        timer = null
    }
}