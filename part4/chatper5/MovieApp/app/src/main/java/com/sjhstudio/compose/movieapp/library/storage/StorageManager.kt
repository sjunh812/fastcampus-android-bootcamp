package com.sjhstudio.compose.movieapp.library.storage

import com.sjhstudio.compose.movieapp.library.storage.di.SharedPrefs
import com.sjhstudio.compose.movieapp.library.storage.helpers.DataConverter
import com.sjhstudio.compose.movieapp.library.storage.helpers.DataEncoding
import com.sjhstudio.compose.movieapp.library.storage.prefs.StorageProvider
import javax.inject.Inject

class StorageManager @Inject constructor(
    @SharedPrefs private val storage: StorageProvider,
    private val converter: DataConverter,
    private val encoding: DataEncoding
) : IStorage {

    override fun <T> save(key: String, value: T): Boolean {
        var saved = false
        val serializedData = converter.serialize(value)
        if (serializedData != null) {
            encoding.encodeToString(serializedData)?.also { data ->
                saved = storage.save(key, data)
            }
        }
        return saved
    }

    override fun <T> save(key: String, value: T, callback: (Boolean) -> Unit) {
        callback(save(key, value))
    }

    override fun exists(key: String): Boolean {
        return storage.get(key) != null
    }

    override fun <T> get(key: String): T? {
        val savedData = storage.get(key)
        var data: T? = null
        if (savedData.isNullOrEmpty().not()) {
            data = converter.deserialize<T>(encoding.decode(savedData))
        }
        return data
    }

    override fun <T> get(key: String, callback: (data: T?) -> Unit) {
        callback(get(key))
    }

    override fun remove(key: String): Boolean {
        return storage.remove(key)
    }

    override fun remove(key: String, callback: (Boolean) -> Unit) {
        callback(remove(key))
    }

    override fun clear(): Boolean {
        storage.clear()
        return true
    }
}