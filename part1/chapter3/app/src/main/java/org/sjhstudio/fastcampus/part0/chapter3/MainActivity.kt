package org.sjhstudio.fastcampus.part0.chapter3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import org.sjhstudio.fastcampus.part0.chapter3.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var inputNumber = 0
    private var cmToM = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etInput.addTextChangedListener { text ->
            inputNumber = if (text.isNullOrEmpty()) 0 else text.toString().toInt()
            setOutputText()
        }

        binding.btnSwap.setOnClickListener {
            cmToM = cmToM.not()
            setUnitText()
            setOutputText()
        }
    }

    private fun setUnitText() {
        binding.tvInputUnit.text = if (cmToM) "cm" else "m"
        binding.tvOutputUnit.text = if (cmToM) "m" else "cm"
    }

    private fun setOutputText() {
        binding.tvOutput.text = if (cmToM) {
            inputNumber.times(0.01).times(1000.0).roundToInt().div(1000.0).toString()
//            inputNumber.times(0.01).toString()
        } else {
            inputNumber.times(100).toString()
        }
    }

    // onStop() 이후 호출
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(CM_TO_M, cmToM)
        super.onSaveInstanceState(outState)
    }

    // onStart() 이후 호출
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        cmToM = savedInstanceState.getBoolean(CM_TO_M)
        setUnitText()
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {

        private const val CM_TO_M = "cmToM"
    }
}