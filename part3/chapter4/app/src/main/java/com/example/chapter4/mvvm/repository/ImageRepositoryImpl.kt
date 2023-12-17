package com.example.chapter4.mvvm.repository

import com.example.chapter4.RetrofitManager
import com.example.chapter4.mvvm.model.Image
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageRepositoryImpl : ImageRepository {

    override fun getRandomImage() =
        RetrofitManager.imageService
            .getRandomImageRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { response ->
                Single.just(Image(url = response.urls.regular, color = response.color))
            }
}