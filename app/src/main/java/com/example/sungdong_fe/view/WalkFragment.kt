package com.example.sungdong_fe.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.WalkFragmentBinding
import com.example.sungdong_fe.viewmodel.WalkViewModel

class WalkFragment : Fragment() {

    companion object {
        fun newInstance() = WalkFragment()
    }

    private lateinit var viewModel: WalkViewModel
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
        viewModel = ViewModelProvider(this).get(WalkViewModel::class.java)
        // TODO: Use the ViewModel
    }

}