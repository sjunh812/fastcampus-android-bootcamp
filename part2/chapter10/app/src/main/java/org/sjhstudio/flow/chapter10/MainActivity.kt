package org.sjhstudio.flow.chapter10

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.sjhstudio.flow.chapter10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val inputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.takeIf { view ->
            view is EditText && ev?.action == MotionEvent.ACTION_DOWN
        }?.let { v ->
            if (ev == null) return super.dispatchTouchEvent(ev)

            val x = ev.rawX
            val y = ev.rawY
            val outLocation = IntArray(2)

            v.getLocationOnScreen(outLocation)

            if (x < outLocation[0] || x > outLocation[0] + v.width || y < outLocation[1] || y > outLocation[1] + v.height) {
                hideKeyboard(v)
                v.clearFocus()
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}