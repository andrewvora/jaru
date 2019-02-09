package com.andrewvora.apps.jaru.di.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andrewvora.apps.jaru.di.Injector
import javax.inject.Inject

/**
 * Created on 2/1/2019.
 */
abstract class ViewModelActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.inject(this)
        super.onCreate(savedInstanceState)
    }
}