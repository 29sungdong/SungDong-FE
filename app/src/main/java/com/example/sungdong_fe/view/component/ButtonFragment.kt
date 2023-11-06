package com.example.sungdong_fe.view.component

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.R
import com.example.sungdong_fe.viewmodel.ButtonViewModel

class ButtonFragment : Fragment() {

    companion object {
        fun newInstance() = ButtonFragment()
    }

    private lateinit var viewModel: ButtonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.button_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ButtonViewModel::class.java)
        // TODO: Use the ViewModel
    }

}