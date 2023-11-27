package com.example.sungdong_fe.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.databinding.DetailMapFragmentBinding
import com.example.sungdong_fe.databinding.WalkFragmentBinding
import com.example.sungdong_fe.view.component.DetailMapFragment
import com.example.sungdong_fe.viewmodel.WalkViewModel

class WalkFragment() : Fragment() {
    companion object {
        lateinit var viewModel: WalkViewModel
    }

    private lateinit var binding: WalkFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WalkFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.isWalk.observe(viewLifecycleOwner){
            if(it) {
                binding.walkviewmodel = viewModel
            }
            else{
                viewModel.updateIsDetailMap(it)
            }
        }
        viewModel.isDetailMap.observe(viewLifecycleOwner){
            when(it){
                true ->{
                    binding.map.removeView(viewModel.tmapview.value)
                    binding.tag.text = "시설지도 입장중"
                    binding.detailMapBtn.visibility = View.GONE
                    binding.stamp.visibility = View.GONE
                    binding.finTxt.visibility = View.GONE

                    try {
                        viewModel.arrive()
                    }catch (e:Exception){
                        println(e)
                    }
                }
                else ->{
                }
            }
        }
        binding.detailMapBtn.visibility = View.VISIBLE
        binding.stamp.visibility = View.VISIBLE
        binding.finTxt.visibility = View.VISIBLE

        binding.detailMapBtn.setOnClickListener {
            viewModel.updateIsDetailMap(true)
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.tmapview.value != null) {
            binding.map.removeView(viewModel.tmapview.value)
            binding.map.addView(viewModel.tmapview.value)
        }
    }
}