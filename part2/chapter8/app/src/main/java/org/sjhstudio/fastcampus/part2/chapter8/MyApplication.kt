package org.sjhstudio.fastcampus.part2.chapter8

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import org.sjhstudio.fastcampus.part2.chapter8.util.Constants.NAVER_MAP_CLIENT_ID

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(NAVER_MAP_CLIENT_ID)
    }
}