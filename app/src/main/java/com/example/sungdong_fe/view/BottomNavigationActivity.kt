package com.example.sungdong_fe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.ActivityBottomNavigationBinding
import com.example.sungdong_fe.view.component.TopBarFragment

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionFragment(binding.menuFragment.id, HomeFragment())

        binding.nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> {
                    transactionFragment(binding.menuFragment.id, HomeFragment())
                }
                R.id.menu_walk -> {
                    transactionFragment(binding.menuFragment.id, WalkFragment())
                }
                R.id.menu_event -> {
                    transactionFragment(binding.menuFragment.id, EventFragment())
                }
            }
            true
        }
    }
    private fun transactionFragment(container_id: Int, fragment: Fragment) = supportFragmentManager
        .beginTransaction()
        .replace(container_id, fragment)
        .commitAllowingStateLoss()
}