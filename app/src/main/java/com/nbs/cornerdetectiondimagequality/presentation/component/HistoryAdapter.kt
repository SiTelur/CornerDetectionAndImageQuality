package com.nbs.cornerdetectiondimagequality.presentation.component

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.databinding.ItemLayoutBinding
import com.nbs.cornerdetectiondimagequality.presentation.component.DashboardListAdapter.DashboardViewHolder

class HistoryAdapter : PagingDataAdapter<HistoryActivity, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {
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
        fun showList(activity: HistoryActivity?) {

            binding.tvTitle.text = activity?.title
            binding.tvDate.text = activity?.timestamp.toString()
            Glide.with(binding.root)
                .load(activity?.pictureUri)
                .centerCrop()
                .into(binding.imageView)

        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryActivity>() {
            override fun areItemsTheSame(oldItem: HistoryActivity, newItem: HistoryActivity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: HistoryActivity, newItem: HistoryActivity): Boolean =
                oldItem == newItem
        }
    }

}