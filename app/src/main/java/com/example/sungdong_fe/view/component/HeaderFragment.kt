package com.example.sungdong_fe.view.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.databinding.HeaderFragmentBinding

class HeaderFragment : Fragment() {

    companion object {
        fun newInstance() = HeaderFragment()
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
        binding.search.setOnClickListener{
            //viewmodel-update searchText
        }
        binding.search.setOnClickListener {
            SearchFragment.vm.updateEnabled()
        }
    }


}