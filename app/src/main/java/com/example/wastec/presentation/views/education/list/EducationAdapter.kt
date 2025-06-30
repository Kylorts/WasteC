package com.example.wastec.presentation.views.education.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wastec.databinding.ItemEducationCategoryBinding
import com.example.wastec.presentation.model.WasteCategoryUi

class EducationAdapter(private val onClick: (WasteCategoryUi) -> Unit) : ListAdapter<WasteCategoryUi, EducationAdapter.CategoryViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemEducationCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CategoryViewHolder(private val binding: ItemEducationCategoryBinding, val onClick: (WasteCategoryUi) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private var currentCategory: WasteCategoryUi? = null

        init {
            itemView.setOnClickListener {
                currentCategory?.let {
                    onClick(it)
                }
            }
        }

        fun bind(category: WasteCategoryUi) {
            currentCategory = category
            binding.textCategoryName.text = category.name
            binding.textCategoryDescription.text = category.description

            Glide.with(itemView.context)
                .load(category.iconUrl)
                .into(binding.imageCategoryIcon)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WasteCategoryUi>() {

            override fun areItemsTheSame(oldItem: WasteCategoryUi, newItem: WasteCategoryUi): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WasteCategoryUi, newItem: WasteCategoryUi): Boolean {
                return oldItem == newItem
            }
        }
    }
}