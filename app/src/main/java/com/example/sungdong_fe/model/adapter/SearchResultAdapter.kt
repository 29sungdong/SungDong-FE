package com.example.sungdong_fe.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.databinding.SearchResultFragmentBinding

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    private var results = emptyArray<String>()
    private lateinit var binding : SearchResultFragmentBinding

    inner class ViewHolder(private val binding: SearchResultFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: String) {
            binding.item.text = result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = SearchResultFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateArray(newArray: Array<String>){
        results = newArray
        notifyDataSetChanged()
    }
}