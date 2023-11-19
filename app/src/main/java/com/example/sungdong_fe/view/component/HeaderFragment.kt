package com.example.sungdong_fe.view.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.databinding.HeaderFragmentBinding
import com.example.sungdong_fe.viewmodel.component.HeaderViewModel

class HeaderFragment : Fragment() {

    companion object {
        lateinit var viewModel: HeaderViewModel
    }
    private lateinit var binding: HeaderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HeaderFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchBtnEnabled.observe(viewLifecycleOwner){
            binding.search.visibility = it
        }
        binding.search.setOnClickListener {
            SearchFragment.viewModel.updateSheetEnabled()
        }
    }


}