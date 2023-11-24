package com.example.sungdong_fe.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.CardBigFragmentBinding
import com.example.sungdong_fe.databinding.EventCategoryFragmentBinding
import com.example.sungdong_fe.databinding.SearchResultFragmentBinding
import com.example.sungdong_fe.model.db.Category
import com.example.sungdong_fe.viewmodel.EventViewModel

class EventCategoryAdapter(val viewModel: EventViewModel) : RecyclerView.Adapter<EventCategoryAdapter.ViewHolder>() {
    private val categories = arrayListOf(
        R.string.category_all,
        R.string.category_education,
        R.string.category_farm,
        R.string.category_culture,
        R.string.category_exhibition,
        R.string.category_volunteer,
        R.string.category_park,
        R.string.category_forest
        )
    private lateinit var binding : EventCategoryFragmentBinding
    inner class ViewHolder(private val binding: EventCategoryFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryStrId: Int){
            if(adapterPosition == viewModel.currentCategory.value?.ordinal){
                binding.categoryBtn.requestFocus()
            }
            binding.categoryBtn.setText(categoryStrId)
            binding.categoryBtn.setOnFocusChangeListener { _, b ->
                if(b) {
                    viewModel.updateCategory(Category.values()[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = EventCategoryFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}