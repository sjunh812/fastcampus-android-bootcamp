package org.sjhstudio.fastcampus.part2.chapter12.player.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.fastcampus.part2.chapter12.R
import org.sjhstudio.fastcampus.part2.chapter12.databinding.ItemVideoBinding
import org.sjhstudio.fastcampus.part2.chapter12.databinding.ItemVideoHeaderBinding
import org.sjhstudio.fastcampus.part2.chapter12.player.model.PlayerHeaderModel
import org.sjhstudio.fastcampus.part2.chapter12.player.model.PlayerModel
import org.sjhstudio.fastcampus.part2.chapter12.player.model.PlayerVideoModel

class PlayerVideoListAdapter(
    private val onClick: (PlayerVideoModel) -> Unit
) : ListAdapter<PlayerModel, RecyclerView.ViewHolder>(diffCallback) {

    inner class HeaderViewHolder(private val binding: ItemVideoHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayerHeaderModel) {
            with(binding) {
                // title
                tvTitle.text = item.title

                // sub title
                tvSubTitle.text = root.context.getString(R.string.header_sub_title_video, item.viewCount, item.dateText)

                // channel name
                tvChannelName.text = item.channelName

                // channel logo
                Glide.with(ivChannelLogo)
                    .load(item.channelThumb)
                    .circleCrop()
                    .into(ivChannelLogo)
            }
        }
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlayerVideoModel) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                HeaderViewHolder(
                    ItemVideoHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                VideoViewHolder(
                    ItemVideoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (getItemViewType(position)) {
            VIEW_TYPE_HEADER -> (holder as HeaderViewHolder).bind(currentList[position] as? PlayerHeaderModel ?: return)
            else -> (holder as VideoViewHolder).bind(currentList[position] as? PlayerVideoModel ?: return)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_VIDEO
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_VIDEO = 1

        private val diffCallback = object : DiffUtil.ItemCallback<PlayerModel>() {
            override fun areItemsTheSame(oldItem: PlayerModel, newItem: PlayerModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PlayerModel, newItem: PlayerModel): Boolean {
                return if (oldItem is PlayerHeaderModel && newItem is PlayerHeaderModel) {
                    oldItem == newItem
                } else if (oldItem is PlayerVideoModel && newItem is PlayerVideoModel) {
                    oldItem == newItem
                } else {
                    false
                }
            }
        }
    }
}