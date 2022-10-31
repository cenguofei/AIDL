package com.example.messenger_server

import android.os.Environment


object MyConstants {
    val CHAPTER_2_PATH: String = (
            Environment.getExternalStorageDirectory().path
            + "/singwhatiwanna/chapter_2/"
            )
    val CACHE_FILE_PATH: String = CHAPTER_2_PATH + "usercache"
    const val MSG_FROM_CLIENT = 0
    const val MSG_FROM_SERVICE = 1
}