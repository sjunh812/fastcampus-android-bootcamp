package com.example.chapter4.mvi

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.chapter4.databinding.ActivityMviBinding
import com.example.chapter4.mvi.repository.ImageRepositoryImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MviActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMviBinding
    private val viewModel: MviViewModel by viewModels {
        MviViewModel.MviViewModelFactory(ImageRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMviBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is MviState.Idle -> {
                            binding.progress.isVisible = false
                        }

                        is MviState.Loading -> {
                            binding.progress.isVisible = true
                        }

                        is MviState.LoadedImage -> {
                            binding.progress.isVisible = false
                            binding.ivRandom.run {
                                setBackgroundColor(Color.parseColor(state.image.color))
                                load(state.image.url) {
                                    crossfade(300)
                                }
                            }
                            binding.tvCount.text = "불러온 이미지 수 : ${state.count}"
                        }
                    }
                }
            }
        }
    }

    fun loadRandomImage() {
        viewModel.sendIntent(MviIntent.LoadImage)
    }
}
