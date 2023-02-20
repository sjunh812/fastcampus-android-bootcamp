package org.sjhstudio.fastcampus.part0.chapter2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 화면전환 시, 데이터 복구하기.
        savedInstanceState?.let { state -> number = state.getInt(NUMBER, 0) }

        val tvCount = findViewById<TextView>(R.id.tv_count)
        val btnReset = findViewById<Button>(R.id.btn_reset)
        val btnPlus = findViewById<Button>(R.id.btn_plus)

        tvCount.text = number.toString()

        btnReset.setOnClickListener {
            number = 0
            tvCount.text = number.toString()
            Log.d("onClick", "리셋(number:$number)")
        }

        btnPlus.setOnClickListener {
            number++
            tvCount.text = number.toString()
            Log.d("onClick", "더하기(number:$number)")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NUMBER, number)
    }

    companion object {

        private const val NUMBER = "number"
    }
}