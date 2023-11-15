package com.example.sungdong_fe.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.databinding.SearchResultFragmentBinding
import com.example.sungdong_fe.model.db.Dto

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    private var results = emptyList<Dto.PlaceLocation>()
    private lateinit var binding : SearchResultFragmentBinding

    inner class ViewHolder(private val binding: SearchResultFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.item.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = SearchResultFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position].name)
    }

    override fun getItemCount(): Int = results.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Dto.PlaceLocation>){
        results = newList
        notifyDataSetChanged()
    }
}