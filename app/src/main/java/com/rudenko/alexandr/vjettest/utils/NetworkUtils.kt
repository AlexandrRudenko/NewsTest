package com.rudenko.alexandr.vjettest.utils

import android.content.Context
import android.net.ConnectivityManager
import com.rudenko.alexandr.vjettest.App

object NetworkUtils  {

    fun isOnline(): Boolean {
        val cm = App.instance.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}