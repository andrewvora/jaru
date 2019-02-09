package com.andrewvora.apps.jaru.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.andrewvora.apps.jaru.R
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : ViewModelActivity() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this@HomeActivity, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        bottom_nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
        initObservers()
    }

    private fun initObservers() {
        viewModel.downloadingLearningSet.observe(this, Observer { downloading ->
            loading_text.visibility = if (downloading) View.VISIBLE else View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_refresh -> {
                viewModel.downloadLearningSet()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}