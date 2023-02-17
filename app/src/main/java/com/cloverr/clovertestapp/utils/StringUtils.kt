package com.cloverr.clovertestapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    @SuppressLint("SimpleDateFormat")
    fun getDateStringFromLong(millis: Long): String {
        val date = Date(millis)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}