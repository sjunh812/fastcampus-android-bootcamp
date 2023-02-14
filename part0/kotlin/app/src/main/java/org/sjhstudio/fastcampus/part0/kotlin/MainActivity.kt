package org.sjhstudio.fastcampus.part0.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = View(this)
        // SAM(Single Abstract Method)
        // Kotlin 에서는 단일 추상 메소드를 람다식으로 표현할 수 있다.
        // (1) java style
        view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                println("click!!")
            }
        })
        // (2) use lambda
        view.setOnClickListener { println("click!!") }
    }
}