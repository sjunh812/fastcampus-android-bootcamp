package com.example.chapter4.mvp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.chapter4.databinding.ActivityMvpBinding
import com.example.chapter4.mvp.model.ImageCountModel
import com.example.chapter4.mvp.repository.ImageRepositoryImpl

class MvpActivity : AppCompatActivity(), MvpContractor.View {

    private lateinit var binding: ActivityMvpBinding
    private lateinit var presenter: MvpContractor.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvpBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }
        presenter = MvpPresenter(imageCountModel = ImageCountModel(), imageRepository = ImageRepositoryImpl())
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    fun loadRandomImage() {
        presenter.loadRandomImage()
    }

    override fun showImage(url: String, color: String) {
        binding.ivRandom.run {
            setBackgroundColor(Color.parseColor(color))
            load(url) {
                crossfade(300)
            }
        }
    }

    override fun showImageCountText(count: Int) {
        binding.tvCount.text = "불러온 이미지 수 : $count"
    }
}