package org.sjhstudio.fastcampus.part1.chapter8.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part1.chapter8.data.FrameItem
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ItemFrameBinding
import org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.viewholder.FrameViewHolder

class FrameAdapter(private val frameList: List<FrameItem>) :
    RecyclerView.Adapter<FrameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        val binding = ItemFrameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FrameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        holder.bind(frameList[position])
    }

    override fun getItemCount() = frameList.size
}