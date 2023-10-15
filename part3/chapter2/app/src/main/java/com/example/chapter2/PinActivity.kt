package com.example.chapter2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.chapter2.databinding.ActivityPinBinding
import com.example.chapter2.widget.ShuffleNumberKeypad

class PinActivity : AppCompatActivity(), ShuffleNumberKeypad.KeypadListener {

    private lateinit var binding: ActivityPinBinding
    private val viewModel: PinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
        initViews()
        observeData()
    }

    private fun bind() {
        with(binding) {
            lifecycleOwner = this@PinActivity
            viewModel = this@PinActivity.viewModel
        }
    }

    private fun initViews() {
        with(binding) {
            shuffleNumberKeypad.setKeypadListener(this@PinActivity)
        }
    }

    private fun observeData() {
        with(viewModel) {
            messageLiveData.observe(this@PinActivity) {
                Toast.makeText(this@PinActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClickNumber(number: String) {
        viewModel.input(number)
    }

    override fun onClickDelete() {
        viewModel.delete()
    }

    override fun onClickDone() {
        viewModel.done()
    }
}