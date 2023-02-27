package org.sjhstudio.fastcampus.part1.chapter7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part1.chapter7.database.Word
import org.sjhstudio.fastcampus.part1.chapter7.databinding.ItemWordBinding

class WordAdapter(
    private val itemClicked: (word: Word) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    var list: MutableList<Word> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }?.let { pos ->
                    itemClicked(list[pos])
                }
            }
        }

        fun bind(word: Word) {
            with(binding) {
                tvWord.text = word.text
                tvMean.text = word.mean
                chipType.text = word.type
            }
        }
    }
}