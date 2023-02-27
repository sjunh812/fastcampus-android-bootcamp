package org.sjhstudio.fastcampus.part1.chapter7

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import org.sjhstudio.fastcampus.part1.chapter7.database.AppDatabase
import org.sjhstudio.fastcampus.part1.chapter7.database.Word
import org.sjhstudio.fastcampus.part1.chapter7.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        with(binding) {
            chipGroupType.apply {
                types.forEach { type -> addView(createChip(type)) }
            }
            btnAdd.setOnClickListener {
                add()
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
        val word = binding.etWord.text.toString()
        val mean = binding.etMean.text.toString()
        val type = binding.chipGroupType.checkedChipId.takeIf { id -> id != View.NO_ID }?.let { id ->
            findViewById<Chip>(id).text
        }.toString()

        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.addWord(Word(word, mean, type))

            runOnUiThread {
                Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent().putExtra("isUpdated", true)
                setResult(RESULT_OK, intent)
                finish()
            }
        }.start()
    }

    companion object {

        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")
    }
}