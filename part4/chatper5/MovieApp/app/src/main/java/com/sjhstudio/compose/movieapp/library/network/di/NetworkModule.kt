package com.sjhstudio.compose.movieapp.library.network.di

import com.google.gson.Gson
import com.sjhstudio.compose.movieapp.BuildConfig
import com.sjhstudio.compose.movieapp.library.network.api.ApiService
import com.sjhstudio.compose.movieapp.library.network.retrofit.NetworkRequestFactory
import com.sjhstudio.compose.movieapp.library.network.retrofit.NetworkRequestFactoryImpl
import com.sjhstudio.compose.movieapp.library.network.retrofit.StringConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(logBaseUrl("https://kgeun.github.io/assets/fastcampus_android_compose/movie/"))
            .addConverterFactory(StringConverterFactory(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideNetworkRequestFactory(impl: NetworkRequestFactoryImpl): NetworkRequestFactory = impl

    private fun logBaseUrl(baseUrl: String): String {
        return baseUrl.also {
            Timber.d("baseUrl: $it")
        }
    }
}