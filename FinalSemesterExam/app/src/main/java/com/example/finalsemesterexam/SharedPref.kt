package com.example.finalsemesterexam

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class SharedPref(context: Context) {
    private val pref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    fun savePref(key: String, value: String, listener: UserPreferenceListener) {
        try {
            editor.putString(key, value)
            editor.commit()
            listener.onSuccess()
        } catch (e: java.lang.Exception) {
            listener.onError(e.toString())
        }
    }

    fun getPref(key: String): String? {
        return pref.getString(key, null)
    }

    //Clear all preferences
    fun clearPref() {
        editor.clear().commit()
    }
}

interface UserPreferenceListener {
    fun onSuccess()

    fun onError(error: String)
}