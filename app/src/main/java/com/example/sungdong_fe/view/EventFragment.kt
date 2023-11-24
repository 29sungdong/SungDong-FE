package com.example.sungdong_fe.view

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.sungdong_fe.databinding.EventFragmentBinding
import com.example.sungdong_fe.databinding.WebviewFragmentBinding
import com.example.sungdong_fe.model.adapter.EventAdapter
import com.example.sungdong_fe.model.adapter.EventCategoryAdapter
import com.example.sungdong_fe.model.db.Category
import com.example.sungdong_fe.viewmodel.EventViewModel

class EventFragment : Fragment() {


    private lateinit var viewModel: EventViewModel
    private lateinit var binding: EventFragmentBinding
    private lateinit var webviewBinding: WebviewFragmentBinding
    private lateinit var dialog: Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialog = Dialog(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EventFragmentBinding.inflate(layoutInflater)
        webviewBinding = WebviewFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        binding.category.adapter = EventCategoryAdapter(viewModel)
        binding.events.adapter = EventAdapter(viewModel)
        dialog.setContentView(webviewBinding.root)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        viewModel.currentCategory.observe(viewLifecycleOwner){
            val category = when(it){
                Category.ALL -> null
                else -> it.name
            }
            viewModel.updateEvents(category, null)
        }
        viewModel.events.observe(viewLifecycleOwner){
            binding.noEvent.visibility = when(it.size){
                0 -> View.VISIBLE
                else -> View.GONE
            }
            (binding.events.adapter as EventAdapter).updateEvents(it)
        }
        viewModel.isLinkOpened.observe(viewLifecycleOwner){
            when(it){
                true -> {
                    dialog.show()
                }
                else -> {
                    dialog.dismiss()
                }
            }
        }
        viewModel.eventUrl.observe(viewLifecycleOwner){
            webviewBinding.webview.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(it)
            }
        }
    }

}