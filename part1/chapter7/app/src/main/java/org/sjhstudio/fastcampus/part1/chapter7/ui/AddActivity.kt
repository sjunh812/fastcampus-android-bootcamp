package org.sjhstudio.fastcampus.part1.chapter7.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import com.google.android.material.chip.Chip
import org.sjhstudio.fastcampus.part1.chapter7.database.AppDatabase
import org.sjhstudio.fastcampus.part1.chapter7.database.Word
import org.sjhstudio.fastcampus.part1.chapter7.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private var originWord: Word? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originWord = intent?.getParcelableExtra("originWord")

        initView()
        handleInputText()
    }

    private fun initView() {
        with(binding) {
            chipGroupType.apply {
                types.forEach { type -> addView(createChip(type)) }
            }
            btnAdd.setOnClickListener {
                if (originWord == null) add() else edit()
            }
            originWord?.let { word ->
                tvTitle.text = "단어 수정"
                btnAdd.text = "수정"
                etWord.setText(word.text)
                etMean.setText(word.mean)
                val selectedChip =
                    chipGroupType.children.firstOrNull { (it as Chip).text == word.type } as? Chip
                Log.d("chip", selectedChip?.text.toString())
                selectedChip?.isChecked = true
            }
        }
    }

    private fun createChip(type: String): Chip {
        return Chip(this).apply {
            text = type
            isCheckable = true
            isClickable = true
        }
    }

    private fun add() {
        if (!handleValidation()) return

        val word = binding.etWord.text.toString()
        val mean = binding.etMean.text.toString()
        val type = binding.chipGroupType.checkedChipId.takeIf { id -> id != View.NO_ID }
            ?.let { id -> findViewById<Chip>(id).text }.toString()

        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.addWord(Word(word, mean, type))
            setResult(RESULT_OK, Intent().putExtra("isUpdated", true))
            finish()
            runOnUiThread {
                Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun edit() {
        if (!handleValidation()) return

        val word = binding.etWord.text.toString()
        val mean = binding.etMean.text.toString()
        val type = binding.chipGroupType.checkedChipId.takeIf { id -> id != View.NO_ID }
            ?.let { id -> findViewById<Chip>(id).text }.toString()

        originWord?.copy(text = word, mean = mean, type = type)?.let { editWord ->
            Thread {
                AppDatabase.getInstance(this)?.wordDao()?.updateWord(editWord)
                setResult(RESULT_OK, Intent().putExtra("editWord", editWord))
                finish()
                runOnUiThread {
                    Toast.makeText(this, "수정을 완료했습니다.", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }
    }

    private fun handleInputText() {
        binding.etWord.addTextChangedListener { changedText ->
            changedText?.let { text ->
                binding.layoutWord.error = when {
                    text.isEmpty() -> "값을 입력해주세요."
                    text.length < 2 -> "2자 이상 입력해주세요."
                    text.length > 30 -> "30자 이하 입력 가능합니다."
                    else -> null
                }
            }
        }
        binding.etMean.addTextChangedListener { changedText ->
            changedText?.let { text ->
                binding.layoutMean.error = if (text.isEmpty()) "값을 입력해주세요." else null
            }
        }
    }

    private fun handleValidation(): Boolean {
        if (binding.layoutWord.error != null || binding.layoutMean.error != null) {
            Toast.makeText(this, "올바른 값을 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else if (binding.etWord.text.isNullOrEmpty() || binding.etMean.text.isNullOrEmpty() || binding.chipGroupType.checkedChipId == View.NO_ID) {
            Toast.makeText(this, "입력값이 누락되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            return true
        }

        return false
    }

    companion object {

        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")
    }
}