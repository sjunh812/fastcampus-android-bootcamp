package org.sjhstudio.fastcampus.part1.chapter7.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part1.chapter7.R
import org.sjhstudio.fastcampus.part1.chapter7.database.Word
import org.sjhstudio.fastcampus.part1.chapter7.databinding.ItemWordBinding

class WordAdapter(
    private val itemClicked: (word: Word) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    var list: MutableList<Word> = mutableListOf()
    var selectedIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(list[position], selectedIndex == position)
    }

    override fun getItemCount(): Int = list.size

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }?.let { pos ->
                    itemClicked(list[pos])
                    binding.container.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.gray_f2
                        )
                    )
                    notifyItemChanged(selectedIndex)
                    notifyItemChanged(pos)
                    selectedIndex = pos
                }
            }
        }

        fun bind(word: Word, isSelected: Boolean) {
            with(binding) {
                tvWord.text = word.text
                tvMean.text = word.mean
                chipType.text = word.type

                container.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        if (isSelected) R.color.gray_f2
                        else R.color.white
                    )
                )
            }
        }
    }
}