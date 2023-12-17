package com.example.chapter4.mvp

import com.example.chapter4.mvp.model.ImageCountModel
import com.example.chapter4.mvp.repository.ImageRepository

class MvpPresenter(
    private val imageCountModel: ImageCountModel,
    private val imageRepository: ImageRepository
) : MvpContractor.Presenter, ImageRepository.Callback {

    private var view: MvpContractor.View? = null

    override fun attachView(view: MvpContractor.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadRandomImage() {
        imageRepository.getRandomImage(this)
    }

    override fun loadImage(url: String, color: String) {
        imageCountModel.increase()
        view?.showImage(url, color)
        view?.showImageCountText(imageCountModel.count)
    }
}