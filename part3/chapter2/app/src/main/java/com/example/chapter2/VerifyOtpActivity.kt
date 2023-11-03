package com.example.chapter2

import android.os.Bundle
import android.os.CountDownTimer
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.chapter2.databinding.ActivityVerifyOtpBinding
import com.example.chapter2.receiver.AuthOtpReceiver
import com.example.chapter2.util.ViewUtil.setOnEditorActionListener
import com.example.chapter2.util.ViewUtil.showKeyboardDelay
import com.google.android.gms.auth.api.phone.SmsRetriever

class VerifyOtpActivity : AppCompatActivity(), AuthOtpReceiver.OtpReceiveListener {

    private lateinit var binding: ActivityVerifyOtpBinding

    private var smsReceiver: AuthOtpReceiver? = null
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
        startSmsReceiver()
    }

    override fun onDestroy() {
        clearTimer()
        stopSmsReceiver()
        super.onDestroy()
    }

    private fun initView() {
        startTimer()
        with(binding) {
            etOtp.doAfterTextChanged {
                if (etOtp.length() >= 6) {
                    stopTimer()
                    Toast.makeText(this@VerifyOtpActivity, "인증번호 입력이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            etOtp.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {

            }
        }
    }

    override fun onOtpReceived(otp: String) {
        binding.etOtp.setText(otp)
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

    private fun startSmsReceiver() {
        SmsRetriever.getClient(this@VerifyOtpActivity).startSmsRetriever().also { task ->
            task.addOnSuccessListener {
                if (smsReceiver == null) {
                    smsReceiver = AuthOtpReceiver().apply {
                        setOtpReceiveListener(this@VerifyOtpActivity)
                    }
                    ContextCompat.registerReceiver(this, smsReceiver, smsReceiver!!.doFilter(), ContextCompat.RECEIVER_NOT_EXPORTED)
                }
            }
            task.addOnFailureListener {
                stopSmsReceiver()
            }
        }
    }

    private fun stopSmsReceiver() {
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver)
            smsReceiver = null
        }
    }
}