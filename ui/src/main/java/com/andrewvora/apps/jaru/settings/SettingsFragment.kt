package com.andrewvora.apps.jaru.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrewvora.apps.jaru.R
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelFragment

/**
 * Created on 2/8/2019.
 */
class SettingsFragment : ViewModelFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
}