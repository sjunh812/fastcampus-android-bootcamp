package org.sjhstudio.fastcampus.part0.kotlin

/**
 * 6. 컬렉션(Collection)
 */

fun main() {
    val list = listOf(1, 2, 3)  // 변경 불가능
    val mutableList = mutableListOf(1, 2, 3)    // 변경 가능
    mutableList.add(4)
    println(mutableList[0]) // indexing operator

    val map = mapOf((1 to "a"), (2 to "b")) // 변경 불가능
    val mutableMap = mutableMapOf((1 to "a"), (2 to "b"))   // 변경
    mutableMap[3] = "c" // indexing operator

    val diverseList = listOf(1, "a", 3.14, true)

    list.joinToString(",")  // 배열 데이터 출력 형식을 변경(separator : 구분자)
    list.map { it * 10 }    // 원소를 원하는 형태로 변환
    list.first { it == 1 }  // 조건을 만족하는 원소중 첫번째 값 반환
    list.filter { it != 2 } // 조건을 만족하는 원소들만 필터링
    list.dropWhile { it == 2 }  // 조건을 만족할 때까지 앞에서부터 인수를 버린다.
}