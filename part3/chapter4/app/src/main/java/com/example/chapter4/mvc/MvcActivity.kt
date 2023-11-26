package com.example.chapter4.mvc

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.chapter4.databinding.ActivityMvcBinding
import com.example.chapter4.mvc.provider.ImageProvider

class MvcActivity : AppCompatActivity(), ImageProvider.Callback {

    private lateinit var binding: ActivityMvcBinding

    private val model = ImageCountModel()
    private val imageProvider = ImageProvider(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvcBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }
    }

    fun loadRandomImage() = imageProvider.getRandomImage()

    override fun onLoadRandomImage(url: String, color: String) {
        model.increase()
        with(binding) {
            ivRandom.run {
                setBackgroundColor(Color.parseColor(color))
                load(url)
            }
            tvCount.text = "불러온 이미지 수 : ${model.count}"
        }
    }
}