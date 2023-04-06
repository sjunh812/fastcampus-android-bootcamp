package org.sjhstudio.fastcampus.part2.chapter7.util

import android.graphics.Point
import kotlin.math.*

object GeoPointConverter {

    private const val NX = 149
    private const val NY = 253

    private const val RE = 6371.00877   // 지도반경
    private const val GRID = 5.0    // 격자간격 (km)
    private const val SLAT1 = 30.0  // 표준위도 1
    private const val SLAT2 = 60.0  // 표준위도 2
    private const val OLAT = 38.0   // 기준점 위도
    private const val OLON = 126.0 // 기준점 경도

    private const val XO = 210 / GRID
    private const val YO = 675 / GRID

    private const val DEGRAD = PI / 180.0
    private const val RADDEG = 180.0 / PI


    fun convert(lat: Double, lon: Double): Point {
        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olat = OLAT * DEGRAD
        val olon = OLON * DEGRAD

        var sn = tan(PI * 0.25 + slat2 * 0.5) / tan(PI * 0.25 + slat1 * 0.5)
        sn = log2(cos(slat1) / cos(slat2)) / log2(sn)

        var sf = tan(PI * 0.25 + slat1 * 0.5)
        sf = sf.pow(sn) * cos(slat1) / sn

        var ro = tan(PI * 0.25 + olat * 0.5)
        ro = re * sf / ro.pow(sn)

        var ra = tan(PI * 0.25 + lat * DEGRAD * 0.5)
        ra = re * sf / ra.pow(sn)

        var theta = lon * DEGRAD - olon
        if (theta > PI) theta -= 2 * PI
        if (theta < -PI) theta += 2 * PI
        theta *= sn

        val nx = ra * sin(theta) + XO + 1.5
        val ny = ro - ra * cos(theta) + YO + 1.5

        return Point(nx.toInt(), ny.toInt())
    }
}