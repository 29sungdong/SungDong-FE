package com.example.sungdong_fe.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
                    context?.dialogResize(dialog, 0.93f, 0.9f)
                    dialog.show()
                }
                else -> {
                    dialog.dismiss()
                }
            }
        }
        dialog.setOnDismissListener {
            viewModel.updateIsLinkOpened(false, null)
        }
        viewModel.eventUrl.observe(viewLifecycleOwner){
            webviewBinding.webview.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(it)
            }
        }
    }
    fun Context.dialogResize(dialog: Dialog, width: Float, height: Float){
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30){
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialog.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)

        }else{
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialog.window
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }

    }

}