package com.rudenko.alexandr.vjettest.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

object DateFormatter{

    @SuppressLint("SimpleDateFormat")
    private val possibleFormats = arrayOf(
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
    )
    @SuppressLint("SimpleDateFormat")
    private val outputFormat = SimpleDateFormat("yyyy-MM-dd")

    fun format(source: String): String {
        possibleFormats.forEach {
            try {
                val date = it.parse(source)
                return outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return ""
    }

}