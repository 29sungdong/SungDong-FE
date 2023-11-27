package com.example.sungdong_fe.view.component

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.DetailMapFragmentBinding
import com.example.sungdong_fe.databinding.MissionBinding
import com.example.sungdong_fe.model.adapter.MissionAdapter
import com.example.sungdong_fe.model.adapter.SubPlaceAdapter
import com.example.sungdong_fe.viewmodel.component.DetailMapViewModel

class DetailMapFragment(val placeId: Int) : Fragment() {

    private lateinit var viewModel: DetailMapViewModel
    private lateinit var binding: DetailMapFragmentBinding
    private lateinit var missionBinding: MissionBinding
    private lateinit var dialog: Dialog
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialog = Dialog(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailMapFragmentBinding.inflate(layoutInflater)
        missionBinding = MissionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailMapViewModel::class.java)
        viewModel.updateSubList(placeId)
        binding.subList.adapter = SubPlaceAdapter(viewModel)
        missionBinding.missions.adapter = MissionAdapter(viewModel)
        dialog.setContentView(missionBinding.root)

        viewModel.subList.observe(viewLifecycleOwner){
            (binding.subList.adapter as SubPlaceAdapter).updateSubPlaces(it)
        }
        viewModel.subPlace.observe(viewLifecycleOwner){
            if(it != null){
                missionBinding.name.text = it.name
                context?.dialogResize(dialog, 0.9f, 0.7f)
                dialog.show()
            }
        }
        viewModel.missions.observe(viewLifecycleOwner){
            (missionBinding.missions.adapter as MissionAdapter).updateMissions(it)
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