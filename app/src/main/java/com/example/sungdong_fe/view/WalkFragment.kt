package com.example.sungdong_fe.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.WalkFragmentBinding
import com.example.sungdong_fe.viewmodel.WalkViewModel
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK

class WalkFragment : Fragment() {
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

        binding.walkviewmodel = viewModel

    }

    private fun transactionFragment(container_id: Int, fragment: Fragment) = parentFragmentManager
        .beginTransaction()
        .replace(container_id, fragment)
        .commitAllowingStateLoss()
}