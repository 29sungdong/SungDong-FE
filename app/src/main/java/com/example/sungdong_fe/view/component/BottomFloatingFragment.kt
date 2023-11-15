package com.example.sungdong_fe.view.component

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.R
import com.example.sungdong_fe.viewmodel.component.BottomFloatingViewModel

class BottomFloatingFragment : Fragment() {

    companion object {
        fun newInstance() = BottomFloatingFragment()
    }

    private lateinit var viewModel: BottomFloatingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_floating_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BottomFloatingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}