package com.example.chapter2.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import com.example.chapter2.databinding.WidgetShuffleNumberKeyboardBinding
import kotlin.random.Random

class ShuffleNumberKeypad @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr), View.OnClickListener {

    interface KeypadListener {
        fun onClickNumber(number: String)
        fun onClickDelete()
        fun onClickDone()
    }

    private var _binding: WidgetShuffleNumberKeyboardBinding? = null
    val binding: WidgetShuffleNumberKeyboardBinding
        get() = _binding!!

    private var listener: KeypadListener? = null

    init {
        _binding = WidgetShuffleNumberKeyboardBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        shuffle()
    }

    fun setKeypadListener(keypadListener: KeypadListener) {
        this.listener = keypadListener
    }

    fun shuffle() {
        val numberList = mutableListOf<String>().also { list ->
            for (i in 0..9) {
                list.add(i, i.toString())
            }
        }
        binding.glRoot.children.forEach { view ->
            if (view is TextView && view.tag != null) { // Number Keypad
                val randIndex = Random.nextInt(numberList.size)
                view.text = numberList[randIndex]
                numberList.removeAt(randIndex)
            }
        }
    }

    fun onClickDelete() {
        listener?.onClickDelete()
    }

    fun onClickDone() {
        listener?.onClickDone()
    }

    override fun onClick(v: View) {
        if ((v is TextView) && (v.tag == TAG_NUMBER)) {
            listener?.onClickNumber(v.text.toString())
        }
    }

    companion object {
        private const val TAG_NUMBER = "num"
    }
}