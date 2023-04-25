package org.sjhstudio.fastcampus.part2.chapter9

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import org.sjhstudio.fastcampus.part2.chapter9.util.Constants

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val hashKey = Utility.getKeyHash(this)
        println(hashKey)

        KakaoSdk.init(this, Constants.KAKAO_API_KEY)
    }
}