package org.sjhstudio.fastcampus.part2.chapter8.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.naver.maps.geometry.LatLng
import org.sjhstudio.fastcampus.part2.chapter8.databinding.ItemRestaurantBinding
import org.sjhstudio.fastcampus.part2.chapter8.model.Restaurant

class RestaurantAdapter(
    private val onClick: (LatLng) -> Unit
) : ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Restaurant>() {
            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.title == newItem.title && oldItem.latLng == newItem.latLng
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?.let { position ->
                        onClick.invoke(currentList[position].latLng)
                    }
            }
        }

        fun bind(data: Restaurant) {
            with(binding) {
                tvTitle.text = Html.fromHtml(data.title, Html.FROM_HTML_MODE_LEGACY)
                tvCategory.text = data.category
                tvAddress.text = data.address

                data.subCategory.forEach { category ->
                    val chip = Chip(itemView.context).apply {
                        text = category
                    }
                    chipGroupSubCategory.addView(chip)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(
            ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}