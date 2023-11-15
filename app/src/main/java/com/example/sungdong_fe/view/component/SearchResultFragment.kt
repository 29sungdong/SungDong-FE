package com.example.sungdong_fe.view.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.databinding.SearchResultFragmentBinding
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK

class SearchResultFragment : Fragment() {
    private lateinit var binding: SearchResultFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchResultFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

}