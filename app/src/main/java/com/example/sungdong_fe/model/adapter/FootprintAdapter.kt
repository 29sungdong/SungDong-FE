package com.example.sungdong_fe.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.EventCategoryFragmentBinding
import com.example.sungdong_fe.databinding.FootprintBinding
import com.example.sungdong_fe.model.db.Category
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.viewmodel.MypageViewModel

class FootprintAdapter(val viewModel: MypageViewModel) : RecyclerView.Adapter<FootprintAdapter.ViewHolder>() {
    private var places = viewModel.places.value ?: listOf()
    private lateinit var binding : FootprintBinding
    inner class ViewHolder(private val binding: FootprintBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(footprint: Dto.Footprint){
            binding.name.text = footprint.name
            binding.stamp.visibility = when(footprint.isVisited){
                true -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = FootprintBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    fun updatePlaces(newList: List<Dto.Footprint>){
        places = newList
        notifyDataSetChanged()
    }
}