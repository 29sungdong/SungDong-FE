package com.example.sungdong_fe.model.db

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    // token : String, userId: Int
    private val prefs: SharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }

    fun setInt(key: String, int: Int) {
        prefs.edit().putInt(key, int).apply()
    }
}