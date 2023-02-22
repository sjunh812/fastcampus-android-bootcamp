package org.sjhstudio.fastcampus.part0.kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sjhstudio.fastcampus.part0.kotlin.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_next).setOnClickListener { navigateNextActivity() }
        findViewById<Button>(R.id.btn_finish).setOnClickListener { finish() }

        Toast.makeText(this, "Lifecycle - Main : onCreate", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Main", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "Lifecycle - Main : onStart", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Main", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "Lifecycle - Main : onRestart", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Main", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Lifecycle - Main : onResume", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Main", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "Lifecycle - Main : onPause", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Main", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "Lifecycle - Main : onStop", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Main", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Lifecycle - Main : onDestroy", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Main", "onDestroy")
    }

    fun navigateNextActivity() {
        val intent = Intent(this, NextActivity::class.java)
        startActivity(intent)
    }

    private fun samExample() {
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