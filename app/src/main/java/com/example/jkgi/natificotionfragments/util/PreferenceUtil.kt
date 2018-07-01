package com.example.jkgi.natificotionfragments.util

import android.content.Context

import android.preference.PreferenceManager

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferenceUtil {

    fun saveArrayList(list: ArrayList<Int>, key: String, context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String, context: Context): ArrayList<Int>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<Int>>() {

        }.type
        return gson.fromJson<ArrayList<Int>>(json, type)
    }
}
