package com.andrewvora.apps.jaru

import android.app.Application
import com.andrewvora.apps.jaru.di.AppComponent
import com.andrewvora.apps.jaru.di.AppComponentProvider
import com.andrewvora.apps.jaru.di.DaggerAppComponent
import com.facebook.stetho.Stetho

@Suppress("unused")
class JaruApp : Application(), AppComponentProvider {

    override val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        component.textToSpeechHelper().init()
    }
}