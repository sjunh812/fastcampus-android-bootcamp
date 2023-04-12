package org.sjhstudio.fastcampus.part2.chapter7.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part2.chapter7.R
import org.sjhstudio.fastcampus.part2.chapter7.databinding.ItemForecastBinding
import org.sjhstudio.fastcampus.part2.chapter7.model.Forecast
import org.sjhstudio.fastcampus.part2.chapter7.util.formatTime
import org.sjhstudio.fastcampus.part2.chapter7.util.setSkyPtyImageResource

class ForecastAdapter : ListAdapter<Forecast, ForecastAdapter.ForecastViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Forecast>() {
            override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return oldItem.dateTime == newItem.dateTime
            }

            override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ForecastViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Forecast, isFirst: Boolean = false) {
            with(binding) {
                tvTime.text = if (isFirst) "지금" else formatTime(data.forecastTime)
                tvTmp.text = itemView.context.getString(R.string.format_temperature, data.tmp)
                ivSkyPty.setSkyPtyImageResource(data.skyPty)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(currentList[position], position == 0)
    }
}