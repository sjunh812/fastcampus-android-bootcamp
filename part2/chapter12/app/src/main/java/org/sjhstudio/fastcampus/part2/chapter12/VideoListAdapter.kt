package org.sjhstudio.fastcampus.part2.chapter12

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.fastcampus.part2.chapter12.databinding.ItemVideoBinding

class VideoListAdapter(
    private val onClick: (VideoItem) -> Unit
) : ListAdapter<VideoItem, VideoListAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoItem) {
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
        private val diffCallback = object : DiffUtil.ItemCallback<VideoItem>() {
            override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}