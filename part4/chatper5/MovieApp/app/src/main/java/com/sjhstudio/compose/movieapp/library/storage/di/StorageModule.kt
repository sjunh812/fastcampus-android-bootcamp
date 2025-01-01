package com.sjhstudio.compose.movieapp.library.storage.di

import com.sjhstudio.compose.movieapp.library.storage.IStorage
import com.sjhstudio.compose.movieapp.library.storage.StorageManager
import com.sjhstudio.compose.movieapp.library.storage.helpers.DataConverter
import com.sjhstudio.compose.movieapp.library.storage.helpers.DataEncoding
import com.sjhstudio.compose.movieapp.library.storage.prefs.InMemoryStorageProvider
import com.sjhstudio.compose.movieapp.library.storage.prefs.SharedPrefStorageProvider
import com.sjhstudio.compose.movieapp.library.storage.prefs.StorageProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideOnDiscStorage(
        @SharedPrefs storage: StorageProvider,
        converter: DataConverter,
        encoding: DataEncoding
    ): IStorage = StorageManager(storage, converter, encoding)

    @Provides
    @SharedPrefs
    fun provideSharedPrefStorageProvider(provider: SharedPrefStorageProvider): StorageProvider = provider

    @Provides
    @InMemory
    fun provideInMemoryStorageProvider(provider: InMemoryStorageProvider): StorageProvider = provider
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPrefs

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InMemory