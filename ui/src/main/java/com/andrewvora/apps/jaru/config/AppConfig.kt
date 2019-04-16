package com.andrewvora.apps.jaru.config

import com.andrewvora.apps.jaru.BuildConfig


object AppConfig {

    private const val PROD_API_URL = "https://jaru-server.herokuapp.com/"
    private const val LOCAL_API_URL = "https://jaru-server.herokuapp.com/"

    val apiUrl = if (BuildConfig.DEBUG) {
        LOCAL_API_URL
    } else {
        PROD_API_URL
    }
}