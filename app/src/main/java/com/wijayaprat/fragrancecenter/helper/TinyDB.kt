package com.wijayaprat.fragrancecenter.helper

import android.content.Context
import android.content.SharedPreferences

class TinyDB(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("TinyDB", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }
}
