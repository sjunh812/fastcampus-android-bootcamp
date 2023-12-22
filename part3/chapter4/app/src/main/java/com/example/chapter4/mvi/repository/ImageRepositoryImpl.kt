package com.example.chapter4.mvi.repository

import com.example.chapter4.RetrofitManager
import com.example.chapter4.mvi.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepositoryImpl(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ImageRepository {

    override suspend fun getRandomImage(): Image = withContext(dispatcher) {
        RetrofitManager.imageService.getRandomImageSuspend().let {
            Image(url = it.urls.regular, color = it.color)
        }
    }
}