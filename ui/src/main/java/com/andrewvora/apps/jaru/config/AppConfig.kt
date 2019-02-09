package com.andrewvora.apps.jaru.config

import com.andrewvora.apps.jaru.BuildConfig


object AppConfig {

    private const val PROD_API_URL = ""
    private const val LOCAL_API_URL = "http://192.168.1.71:9000"

    val apiUrl = if (BuildConfig.DEBUG) {
        LOCAL_API_URL
    } else {
        PROD_API_URL
    }
}