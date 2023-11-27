package com.example.sungdong_fe.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sungdong_fe.databinding.MissionBoxBinding
import com.example.sungdong_fe.databinding.SubBinding
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.viewmodel.component.DetailMapViewModel

class MissionAdapter(val viewModel: DetailMapViewModel) : RecyclerView.Adapter<MissionAdapter.ViewHolder>() {
    private var missions = viewModel.missions.value ?: listOf()
    private lateinit var binding: MissionBoxBinding

    inner class ViewHolder(private val binding: MissionBoxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mission: Dto.Mission) {
            binding.txt.text = mission.content
            binding.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = MissionBoxBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(missions[position])
    }

    override fun getItemCount(): Int = missions.size

    fun updateMissions(newList: List<Dto.Mission>?) {
        missions = newList ?: listOf()
        notifyDataSetChanged()
    }
}
