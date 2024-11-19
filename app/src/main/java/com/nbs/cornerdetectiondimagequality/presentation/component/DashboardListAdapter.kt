package com.nbs.cornerdetectiondimagequality.presentation.component

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.layout.LayoutInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.databinding.ItemLayoutBinding

class DashboardListAdapter: ListAdapter<HistoryActivity, DashboardListAdapter.DashboardViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DashboardViewHolder,
        position: Int
    ) {
        val activity = getItem(position)
        holder.showList(activity)
    }

    inner class DashboardViewHolder(val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun showList(activity: HistoryActivity) {

            binding.tvTitle.text = activity.title
            binding.tvDate.text = activity.timestamp.toString()
            binding.imageView.setImageURI(Uri.parse(activity.pictureUri))
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryActivity>() {
            override fun areItemsTheSame(oldItem: HistoryActivity, newItem: HistoryActivity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HistoryActivity, newItem: HistoryActivity): Boolean {
                return oldItem == newItem
            }
        }
    }
}