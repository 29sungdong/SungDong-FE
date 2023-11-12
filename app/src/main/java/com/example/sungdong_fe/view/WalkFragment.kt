package com.example.sungdong_fe.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.sungdong_fe.databinding.WalkFragmentBinding
import com.example.sungdong_fe.view.component.SearchFragment
import com.example.sungdong_fe.viewmodel.SearchViewModel
import com.example.sungdong_fe.viewmodel.WalkViewModel
import com.skt.Tmap.TMapView

class WalkFragment : Fragment() {
    private val MAP_APP_KEY = "qT7dD5vUfW5vDWypqKwBO74qHiihiEi8126GHvsr"
    private lateinit var viewModel: WalkViewModel
    private lateinit var binding: WalkFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WalkFragmentBinding.inflate(layoutInflater)
        transactionFragment(binding.sheet.id, SearchFragment())
        // viewModel
        SearchFragment.vm = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel = ViewModelProvider(this).get(WalkViewModel::class.java)
        viewModel.updateLocation(context)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tMapView = TMapView(context)
        tMapView.setSKTMapApiKey(MAP_APP_KEY)
        binding.map.addView(tMapView)
        binding.gps.setOnClickListener{
            viewModel.updateLocation(context)
        }

        viewModel.longitude.observe(this){
            tMapView.setCenterPoint(viewModel.longitude.value!!, viewModel.latitude.value!!, false)
        }
        SearchFragment.vm.enabled.observe(this) {
            binding.sheet.visibility = when (SearchFragment.vm.enabled.value) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        }

    }
    private fun transactionFragment(container_id: Int, fragment: Fragment) = parentFragmentManager
        .beginTransaction()
        .replace(container_id, fragment)
        .commitAllowingStateLoss()
}