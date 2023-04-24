package org.sjhstudio.fastcampus.part2.chapter9

import android.app.Application
import com.kakao.sdk.common.util.Utility

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val hashKey = Utility.getKeyHash(this)
        println(hashKey)
    }
}