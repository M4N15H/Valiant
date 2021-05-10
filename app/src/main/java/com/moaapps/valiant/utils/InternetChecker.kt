package com.moaapps.valiant.utils

import android.content.Context
import android.net.ConnectivityManager

object InternetChecker {
    fun isConnected(context: Context):Boolean{
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return info != null && info.isConnected
    }
}