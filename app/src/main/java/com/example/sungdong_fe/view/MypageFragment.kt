package com.example.sungdong_fe.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.MypageFragmentBinding
import com.example.sungdong_fe.model.adapter.FootprintAdapter
import com.example.sungdong_fe.viewmodel.MypageViewModel

class MypageFragment : Fragment() {

    private lateinit var binding: MypageFragmentBinding
    private lateinit var viewModel: MypageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MypageFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MypageViewModel::class.java)
        binding.placeList.adapter = FootprintAdapter(viewModel)
        binding.loginButton.setOnClickListener{
            viewModel.login(binding.email.text.toString(), binding.password.text.toString())
        }
        viewModel.isLogin.observe(viewLifecycleOwner){
            when(it){
                true -> {
                    binding.loginBox.visibility = View.GONE
                    binding.placeList.visibility = View.VISIBLE
                    viewModel.updateFootprint()
                }
                else -> {
                    binding.loginBox.visibility = View.VISIBLE
                    binding.placeList.visibility = View.GONE
                }
            }
        }
        viewModel.places.observe(viewLifecycleOwner){
            (binding.placeList.adapter as FootprintAdapter).updatePlaces(it)
        }
    }

}