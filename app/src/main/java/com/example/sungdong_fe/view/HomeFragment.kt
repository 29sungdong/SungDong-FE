package com.example.sungdong_fe.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.ActivityBottomNavigationBinding
import com.example.sungdong_fe.databinding.HomeFragmentBinding
import com.example.sungdong_fe.model.adapter.CardBigAdapter
import com.example.sungdong_fe.viewmodel.HomeViewModel
import com.example.sungdong_fe.viewmodel.component.CardBigViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: CardBigViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CardBigViewModel::class.java)
        binding.contents.adapter = CardBigAdapter(requireContext(), viewModel)
        viewModel.updatePlaces()

        viewModel.places.observe(viewLifecycleOwner){
            (binding.contents.adapter as CardBigAdapter).updateList(it)
        }

    }

}