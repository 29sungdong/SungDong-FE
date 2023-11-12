package com.example.sungdong_fe.view.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sungdong_fe.databinding.SearchFragmentBinding
import com.example.sungdong_fe.model.adapter.SearchResultAdapter
import com.example.sungdong_fe.viewmodel.SearchViewModel

class SearchFragment : Fragment(){

    companion object {
        lateinit var vm : SearchViewModel
    }

    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultAdapter = SearchResultAdapter()

        binding.searchResult.adapter = resultAdapter
        binding.searchBtn.setOnClickListener{
            // search
            vm.updateSearchResult(binding.searchInput.text.toString())
        }
        vm.searchResult.observe(this, Observer {
            // adapter 리스트 바꾸기
            resultAdapter.updateArray(vm.searchResult.value!!)
            binding.searchResult.adapter = resultAdapter
        })
        vm.enabled.observe(this){
            if(vm.enabled.value == false){
                binding.searchInput.text = null
            }
        }

    }

}