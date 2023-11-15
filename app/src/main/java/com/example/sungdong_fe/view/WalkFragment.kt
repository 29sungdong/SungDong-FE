package com.example.sungdong_fe.view

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.databinding.WalkFragmentBinding
import com.example.sungdong_fe.view.component.HeaderFragment
import com.example.sungdong_fe.view.component.SearchFragment
import com.example.sungdong_fe.viewmodel.component.SearchViewModel
import com.example.sungdong_fe.viewmodel.WalkViewModel
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK

class WalkFragment : Fragment() {
    private lateinit var viewModel: WalkViewModel
    private lateinit var binding: WalkFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WalkFragmentBinding.inflate(layoutInflater)
        transactionFragment(binding.sheet.id, SearchFragment())

        // viewModel
        SearchFragment.viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel = ViewModelProvider(this).get(WalkViewModel::class.java)
        viewModel.updateLocation(context)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.gps.setOnClickListener{
            viewModel.updateLocation(context)
        }

        viewModel.longitude.observe(this){
//            tMapView.setCenterPoint(viewModel.longitude.value!!, viewModel.latitude.value!!, false)
        }
        SearchFragment.viewModel.sheetEnabled.observe(this) {
            binding.sheet.visibility = it
        }

    }

    override fun onStart() {
        super.onStart()
        HeaderFragment.viewModel.updateSearchBtnEnabled(View.VISIBLE)
    }
    override fun onStop() {
        super.onStop()
        HeaderFragment.viewModel.updateSearchBtnEnabled(View.GONE)
    }
    private fun transactionFragment(container_id: Int, fragment: Fragment) = fragmentManager!!
        .beginTransaction()
        .replace(container_id, fragment)
        .commitAllowingStateLoss()
}