package org.sjhstudio.fastcampus.part0.kotlin.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sjhstudio.fastcampus.part0.kotlin.R

class NextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        Toast.makeText(this, "라이프사이클 - Next : onCreate", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Next", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "Lifecycle - Next : onStart", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Next", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "Lifecycle - Next : onRestart", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Next", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Lifecycle - Next : onResume", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Next", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "Lifecycle - Next : onPause", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Next", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "Lifecycle - Next : onStop", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Next", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Lifecycle - Next : onDestroy", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle - Next", "onDestroy")
    }
}