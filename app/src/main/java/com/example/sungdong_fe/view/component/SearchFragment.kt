package com.example.sungdong_fe.view.component

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.sungdong_fe.databinding.SearchFragmentBinding
import com.example.sungdong_fe.model.adapter.SearchResultAdapter
import com.example.sungdong_fe.viewmodel.component.SearchViewModel

class SearchFragment : Fragment(){

    companion object {
        lateinit var viewModel: SearchViewModel
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
        binding.searchInput.doOnTextChanged { text, start, before, count ->
            viewModel.updateSearchResult(text.toString())
        }

        viewModel.searchResult.observe(this, Observer {
            // adapter 리스트 바꾸기
            resultAdapter.updateList(viewModel.searchResult.value?:emptyList())
            binding.searchResult.adapter = resultAdapter
        })
        viewModel.sheetEnabled.observe(this){
            // search fragment visibility 조정
            binding.root.visibility = it
            if(viewModel.sheetEnabled.value == View.GONE){
                binding.searchInput.setText("")
                hideKeyboardFrom(requireContext(), view)
            }
        }
        HeaderFragment.viewModel.searchBtnEnabled.observe(this){
            if(it == View.GONE && viewModel.sheetEnabled.value == View.VISIBLE)
                viewModel.updateSheetEnabled()
        }

    }
    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}