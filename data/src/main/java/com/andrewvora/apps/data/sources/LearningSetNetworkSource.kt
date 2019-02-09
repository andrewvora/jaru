package com.andrewvora.apps.data.sources

import com.andrewvora.apps.data.apis.JaruApi
import com.andrewvora.apps.data.dtos.LearningSetDto

/**
 * Created on 1/19/2019.
 */
internal class LearningSetNetworkSource(
    private val jaruApi: JaruApi
) {
    fun getContent(): LearningSetDto? {
        return jaruApi.getFullLearningSet().execute().body()
    }
}