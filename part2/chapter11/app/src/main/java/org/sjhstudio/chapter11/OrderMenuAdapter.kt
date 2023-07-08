package org.sjhstudio.chapter11

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.chapter11.databinding.ItemMenuOrderBinding
import org.sjhstudio.chapter11.model.MenuItem

class OrderMenuAdapter : ListAdapter<MenuItem, OrderMenuAdapter.OrderMenuViewHolder>(diffCallback) {

    inner class OrderMenuViewHolder(private val binding: ItemMenuOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuItem) {
            with(binding) {
                Glide.with(ivMenu)
                    .load(item.imageUrl)
                    .circleCrop()
                    .into(ivMenu)

                tvMenuName.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderMenuViewHolder {
        return OrderMenuViewHolder(
            ItemMenuOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderMenuViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MenuItem>() {
            override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}