package com.example.sungdong_fe.view.component

import android.content.ClipData.Item
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.TopBarFragmentBinding
import com.example.sungdong_fe.viewmodel.TopBarViewModel

class TopBarFragment : Fragment() {

    companion object {
        fun newInstance() = TopBarFragment()
    }

    private lateinit var viewModel: TopBarViewModel
    private lateinit var binding: TopBarFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TopBarFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopBarViewModel::class.java)
        viewModel.searchText.observe(viewLifecycleOwner, Observer<String>{
                viewModel.updateSearchText(it)
        })
    }

}