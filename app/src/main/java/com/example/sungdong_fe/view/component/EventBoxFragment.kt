package com.example.sungdong_fe.view.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.EventBoxFragmentBinding

class EventBoxFragment : Fragment() {
    private lateinit var binding : EventBoxFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EventBoxFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

}