package com.example.sungdong_fe.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.databinding.EventBoxFragmentBinding
import com.example.sungdong_fe.databinding.EventCategoryFragmentBinding
import com.example.sungdong_fe.model.db.Category
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.viewmodel.EventViewModel

class EventAdapter(val viewModel: EventViewModel) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    private var events = viewModel.events.value ?: listOf()
    private lateinit var binding : EventBoxFragmentBinding
    inner class ViewHolder(private val binding: EventBoxFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Dto.Event){
            binding.eventName.text = event.name
            binding.eventEnddate.text = "(~"+event.endDate+")"
            binding.eventPlace.text = event.placeName
            binding.eventLink.setOnClickListener{
                viewModel.updateIsLinkOpened(true, event.url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = EventBoxFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    fun updateEvents(newList: List<Dto.Event>){
        events = newList
        notifyDataSetChanged()
    }
}