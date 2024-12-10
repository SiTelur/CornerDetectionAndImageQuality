package com.nbs.cornerdetectiondimagequality.presentation.component.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryEntity
import com.nbs.cornerdetectiondimagequality.databinding.ItemLayoutBinding
import com.nbs.cornerdetectiondimagequality.presentation.component.DetailFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HistoryAdapter(private val onItemClick: (HistoryEntity) -> Unit) : PagingDataAdapter<HistoryEntity, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val activity = getItem(position)
        holder.showList(activity)
    }

    inner class ViewHolder(val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun showList(activity: HistoryEntity?) {

            binding.tvTitle.text = activity?.title
            activity?.timestamp?.let { binding.tvDate.text = DateToString(it)}
            Glide.with(binding.root)
                .load(activity?.pictureUri)
                .centerCrop()
                .into(binding.imageView)

            itemView.setOnClickListener { onItemClick(activity!!) }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean =
                oldItem == newItem
        }
    }

    fun DateToString(localDateTime: LocalDateTime): String {
        // Formatter dengan pola hari, tanggal, bulan, dan tahun
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.getDefault())
        return localDateTime.format(formatter)
    }
}

