package org.sjhstudio.fastcampus.part1.chapter5

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sjhstudio.fastcampus.part1.chapter5.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var firstNumberStringBuilder = StringBuilder()
    private var secondNumberStringBuilder = StringBuilder()
    private var operatorStringBuilder = StringBuilder()
    private val decimalFormat = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun numberClicked(view: View) {
        val numberStringBuilder =
            if (operatorStringBuilder.isEmpty()) firstNumberStringBuilder else secondNumberStringBuilder
        val numberString = (view as? Button)?.text?.toString() ?: ""

        numberStringBuilder.append(numberString)
        updateEquationUI()
    }

    fun clearClicked(view: View) {
        clearKeypadStringBuilder()
        updateEquationUI()
        binding.tvResult.text = ""
    }

    fun equalClicked(view: View) {
        if (firstNumberStringBuilder.isEmpty() || secondNumberStringBuilder.isEmpty() || operatorStringBuilder.isEmpty()) {
            Toast.makeText(this, "올바르지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val firstNumber = firstNumberStringBuilder.toString().toBigDecimal()
        val secondNumber = secondNumberStringBuilder.toString().toBigDecimal()

        val result = when (operatorStringBuilder.toString()) {
            "+" -> decimalFormat.format(firstNumber.plus(secondNumber))
            "-" -> decimalFormat.format(firstNumber.minus(secondNumber))
            else -> ""
        }

        binding.tvResult.text = result
        clearKeypadStringBuilder()
    }

    fun operatorClicked(view: View) {
        if (firstNumberStringBuilder.isEmpty()) {
            Toast.makeText(this, "먼저 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (secondNumberStringBuilder.isNotEmpty()) {
            Toast.makeText(this, "1개의 연산자에 대해서만 연산이 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val operatorString = (view as? Button)?.text?.toString() ?: return

        operatorStringBuilder.clear().append(operatorString)
        updateEquationUI()
    }

    @SuppressLint("SetTextI18n")
    private fun updateEquationUI() {
        val firstFormattedNumberString =
            if (firstNumberStringBuilder.isNotEmpty()) decimalFormat.format(
                firstNumberStringBuilder.toString().toBigDecimal()
            )
            else ""
        val secondFormattedNumberString =
            if (secondNumberStringBuilder.isNotEmpty()) decimalFormat.format(
                secondNumberStringBuilder.toString().toBigDecimal()
            )
            else ""
        binding.tvEquation.text =
            "$firstFormattedNumberString $operatorStringBuilder $secondFormattedNumberString"
    }

    private fun clearKeypadStringBuilder() {
        firstNumberStringBuilder.clear()
        secondNumberStringBuilder.clear()
        operatorStringBuilder.clear()
    }
}