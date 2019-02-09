package com.andrewvora.apps.jaru.di

import android.app.Service
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


interface AppComponentProvider {
    val component: AppComponent
}

val AppCompatActivity.Injector
    get() = (application as AppComponentProvider).component

val Fragment.Injector
    get() = (activity?.application as AppComponentProvider).component

val Service.Injector
    get() = (application as AppComponentProvider).component