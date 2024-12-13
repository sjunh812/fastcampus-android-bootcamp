package com.sjhstudio.compose.movieapp.library.storage.helpers

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.inject.Inject
import timber.log.Timber

class DataConverter @Inject constructor() {

    fun <T> serialize(obj: T): ByteArray? {
        return try {
            val bytesOS = ByteArrayOutputStream()
            ObjectOutputStream(bytesOS).writeObject(obj)
            bytesOS.toByteArray()
        } catch (e: IOException) {
            Timber.e(e)
            null
        }
    }

    fun <T> deserialize(data: ByteArray?): T? {
        return try {
            val bytesIS = ByteArrayInputStream(data)
            ObjectInputStream(bytesIS).readObject() as T?
        } catch (e: ClassNotFoundException) {
            Timber.e(e)
            null
        } catch (e: IOException) {
            Timber.e(e)
            null
        }
    }
}