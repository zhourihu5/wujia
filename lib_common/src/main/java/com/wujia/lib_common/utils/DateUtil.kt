package com.wujia.lib_common.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
object DateUtil {

    //获取系统当前日期时间
    val currentyyyymmddhh: String
        get() {
            val formatter = SimpleDateFormat("yyyyMMddHH")
            val date = Date(System.currentTimeMillis())
            return formatter.format(date)
        }

    //获取系统当前日期
    val currentDate: String
        get() {
            val formatter = SimpleDateFormat("MM月dd日")
            val date = Date(System.currentTimeMillis())
            return formatter.format(date)
        }

    //获取系统当前日期(英文格式)
    val currentDateEnglish: String
        get() {
            val formatter = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)
            val date = Date(System.currentTimeMillis())
            return formatter.format(date)
        }

    //获取系统当前时间
    val currentTime: String
        get() {
            val formatter = SimpleDateFormat("HH:mm:ss")
            val date = Date(System.currentTimeMillis())
            return formatter.format(date)
        }


    //获取系统当前时间
    val currentTimeHHMM: String
        get() {
            val formatter = SimpleDateFormat("HH:mm")
            val date = Date(System.currentTimeMillis())
            return formatter.format(date)
        }


    //获取系统当前是星期几
    val currentWeekDay: String
        get() {
            var week = ""
            val c1 = Calendar.getInstance()
            when (c1.get(Calendar.DAY_OF_WEEK)) {
                1 -> week = "星期日"
                2 -> week = "星期一"
                3 -> week = "星期二"
                4 -> week = "星期三"
                5 -> week = "星期四"
                6 -> week = "星期五"
                7 -> week = "星期六"
            }
            return week
        }


    fun getDate(date: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.parse(date)
    }


}
