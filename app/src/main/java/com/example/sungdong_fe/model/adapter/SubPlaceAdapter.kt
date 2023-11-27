package com.example.sungdong_fe.model.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.databinding.FootprintBinding
import com.example.sungdong_fe.databinding.SubBinding
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.viewmodel.MypageViewModel
import com.example.sungdong_fe.viewmodel.component.DetailMapViewModel

class SubPlaceAdapter(val viewModel: DetailMapViewModel) : RecyclerView.Adapter<SubPlaceAdapter.ViewHolder>() {
    private var subPlaces = viewModel.subList.value ?: listOf()
    private lateinit var binding: SubBinding

    inner class ViewHolder(private val binding: SubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subPlace: Dto.SubPlace) {
            binding.name.text = subPlace.name
            binding.btn.setOnClickListener {
                viewModel.updateSubPlace(subPlace)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = SubBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subPlaces[position])
    }

    override fun getItemCount(): Int = subPlaces.size

    fun updateSubPlaces(newList: List<Dto.SubPlace>?) {
        subPlaces = newList ?: listOf()
        notifyDataSetChanged()
    }
}
