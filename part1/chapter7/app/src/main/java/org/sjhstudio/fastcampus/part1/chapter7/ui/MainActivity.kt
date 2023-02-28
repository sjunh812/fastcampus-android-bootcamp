package org.sjhstudio.fastcampus.part1.chapter7.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.sjhstudio.fastcampus.part1.chapter7.database.AppDatabase
import org.sjhstudio.fastcampus.part1.chapter7.database.Word
import org.sjhstudio.fastcampus.part1.chapter7.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part1.chapter7.ui.adapter.WordAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedWord: Word? = null
    private val wordAdapter: WordAdapter by lazy {
        WordAdapter { word -> updateClickedItemUi(word) }
    }
    private val addWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val isUpdated = result.data?.getBooleanExtra("isUpdated", false) ?: false
            if (result.resultCode == RESULT_OK && isUpdated) {
                addAndUpdateItemUi()
            }
        }
    private val updateWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val editWord = result.data?.getParcelableExtra<Word>("editWord")
            if (result.resultCode == RESULT_OK && editWord != null) {
                editAndUpdateItemUi(editWord)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initDatabase()
    }

    private fun initView() {
        with(binding) {
            rvWord.apply {
                adapter = wordAdapter
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                val dividerItemDecoration =
                    DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
                addItemDecoration(dividerItemDecoration)
            }
            btnAdd.setOnClickListener {
                addWordResult.launch(Intent(this@MainActivity, AddActivity::class.java))
            }
            ivDelete.setOnClickListener {
                deleteAndUpdateItemUi()
            }
            ivEdit.setOnClickListener {
                selectedWord?.let { word ->
                    val intent = Intent(this@MainActivity, AddActivity::class.java)
                        .apply {
                            putExtra("originWord", word)
                        }
                    updateWordResult.launch(intent)
                }
            }
        }
    }

    private fun initDatabase() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getAll()?.let { list ->
                wordAdapter.list = list.toMutableList()
                runOnUiThread {
                    wordAdapter.notifyDataSetChanged()
                    wordAdapter.list.takeIf { it.isNotEmpty() }
                        ?.let { updateClickedItemUi(it.first()) }
                }
            }
        }.start()
    }

    private fun updateClickedItemUi(word: Word?) {
        selectedWord = word

        with(binding) {
            tvWord.text = word?.text ?: ""
            tvMean.text = word?.mean ?: ""
        }
    }

    private fun addAndUpdateItemUi() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let { word ->
                wordAdapter.list.add(0, word)
                wordAdapter.selectedIndex = 0
                runOnUiThread {
                    wordAdapter.notifyDataSetChanged()
                    updateClickedItemUi(word)
                }
            }
        }.start()
    }

    private fun deleteAndUpdateItemUi() {
        Thread {
            selectedWord?.let { word ->
                AppDatabase.getInstance(this)?.wordDao()?.removeWord(word)
                wordAdapter.list.remove(word)
                wordAdapter.selectedIndex = 0
                runOnUiThread {
                    wordAdapter.notifyDataSetChanged()

                    if (wordAdapter.list.isNotEmpty()) updateClickedItemUi(wordAdapter.list.first())
                    else updateClickedItemUi(null)

                    Toast.makeText(this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun editAndUpdateItemUi(editWord: Word) {
        val updatedIndex = wordAdapter.list.indexOfFirst { word -> word.id == editWord.id }
        wordAdapter.list[updatedIndex] = editWord
        wordAdapter.notifyItemChanged(updatedIndex)
        updateClickedItemUi(editWord)
    }
}