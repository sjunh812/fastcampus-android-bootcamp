package org.sjhstudio.fastcampus.part2.chapter12.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.fastcampus.part2.chapter12.R
import org.sjhstudio.fastcampus.part2.chapter12.databinding.ItemVideoBinding
import org.sjhstudio.fastcampus.part2.chapter12.main.data.VideoEntity

class VideoListAdapter(
    private val onClick: (VideoEntity) -> Unit
) : ListAdapter<VideoEntity, VideoListAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoEntity) {
            with(binding) {
                // video thumb
                Glide.with(ivVideoThumb)
                    .load(item.videoThumb)
                    .into(ivVideoThumb)

                // channel logo
                Glide.with(ivChannelLogo)
                    .load(item.channelThumb)
                    .circleCrop()
                    .into(ivChannelLogo)

                // title
                tvTitle.text = item.title

                // sub title
                tvSubTitle.text = root.context.getString(R.string.sub_title_video, item.channelName, item.viewCount, item.dateText)

                // click event
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<VideoEntity>() {
            override fun areItemsTheSame(oldItem: VideoEntity, newItem: VideoEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoEntity, newItem: VideoEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}