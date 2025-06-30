package com.example.wastec.presentation.views.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wastec.data.datasource.local.db.entity.HistoryEntity
import com.example.wastec.databinding.ItemHistoryBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter : ListAdapter<HistoryEntity, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryEntity) {
            binding.textWasteLabel.text = history.label
            binding.textConfidence.text = "Akurasi ${(history.confidence * 100).toInt()}%"

            val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
            val date = Date(history.timestamp)
            binding.textScanTimestamp.text = sdf.format(date)

            val imageFile = File(history.imagePath)
            Glide.with(itemView.context)
                .load(imageFile)
                .into(binding.imageWasteThumbnail)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HistoryEntity,
                newItem: HistoryEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}