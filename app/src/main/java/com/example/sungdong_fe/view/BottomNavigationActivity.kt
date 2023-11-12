package com.example.sungdong_fe.view

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.ActivityBottomNavigationBinding
import com.example.sungdong_fe.view.component.HeaderFragment
import com.example.sungdong_fe.view.component.SearchFragment

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPermission()
        transactionFragment(binding.menuFragment.id, HomeFragment())
        transactionFragment(binding.header.id, HeaderFragment())

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

    private fun getPermission(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            val permissions = arrayOf(android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, 100)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty()) {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED)
                        super.onBackPressed()
                }
            }
        }
    }
}