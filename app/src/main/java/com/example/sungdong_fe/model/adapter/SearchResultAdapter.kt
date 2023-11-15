package com.example.sungdong_fe.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.databinding.SearchResultFragmentBinding
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.viewmodel.WalkViewModel
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    private var results = emptyList<Dto.PlaceLocation>()
    private lateinit var binding : SearchResultFragmentBinding
    private lateinit var viewModel: WalkViewModel

    inner class ViewHolder(private val binding: SearchResultFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Dto.PlaceLocation) {
            binding.item.text = item.name
            binding.item.setOnClickListener {
                viewModel.updateDestination(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = SearchResultFragmentBinding.inflate(inflater, parent, false)
        viewModel = ViewModelProvider(parent as ViewModelStoreOwner)[WalkViewModel::class.java]

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Dto.PlaceLocation>){
        results = newList
        notifyDataSetChanged()
    }
}