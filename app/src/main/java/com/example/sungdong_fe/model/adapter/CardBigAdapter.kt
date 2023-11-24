package com.example.sungdong_fe.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.databinding.CardBigFragmentBinding
import com.example.sungdong_fe.databinding.SearchResultFragmentBinding
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.viewmodel.component.CardBigViewModel

class CardBigAdapter(viewModel: CardBigViewModel): RecyclerView.Adapter<CardBigAdapter.ViewHolder>() {

    private var places = viewModel.places.value ?: listOf()
    private lateinit var binding : CardBigFragmentBinding

    inner class ViewHolder(private val binding: CardBigFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Dto.PlaceInfo){
            binding.name.text = place.name
            binding.loc.text = place.address
            binding.time.text = place.openingTime+'-'+place.closingTime
            when(place.tel){
                ""-> binding.tel.text = "번호 없음"
                else -> binding.tel.text = place.tel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = CardBigFragmentBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    fun updateList(newList: List<Dto.PlaceInfo>?){
        places = newList ?: listOf()
        notifyDataSetChanged()
    }
}