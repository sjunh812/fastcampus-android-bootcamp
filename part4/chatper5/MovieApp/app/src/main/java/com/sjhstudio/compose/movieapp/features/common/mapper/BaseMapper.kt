package com.sjhstudio.compose.movieapp.features.common.mapper

import com.sjhstudio.compose.movieapp.features.common.entity.EntityWrapper
import com.sjhstudio.compose.movieapp.library.network.model.ApiResponse
import com.sjhstudio.compose.movieapp.library.network.model.ApiResult

abstract class BaseMapper<R, E> {

    fun mapFromResult(result: ApiResult<R>, extra: Any? = null): EntityWrapper<E> =
        when (result.response) {
            is ApiResponse.Success -> getSuccess(response = result.response.data, extra = extra)
            is ApiResponse.Fail -> getFail(error = result.response.error)
        }

    abstract fun getSuccess(response: R?, extra: Any?): EntityWrapper.Success<E>

    abstract fun getFail(error: Throwable): EntityWrapper.Fail<E>
}