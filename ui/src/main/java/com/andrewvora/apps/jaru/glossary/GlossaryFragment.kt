package com.andrewvora.apps.jaru.glossary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.andrewvora.apps.jaru.R
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelFragment
import com.andrewvora.apps.jaru.viewglossary.ViewGlossaryActivity
import kotlinx.android.synthetic.main.fragment_glossary.*


class GlossaryFragment : ViewModelFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GlossaryViewModel::class.java)
    }

    private val glossaryAdapter by lazy {
        GlossaryAdapter { selectedGlossary ->
            val intent = ViewGlossaryActivity.start(activity!!, selectedGlossary.id)
            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_glossary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        glossary_recycler_view.layoutManager = GridLayoutManager(activity, 2)
        glossary_recycler_view.adapter = glossaryAdapter

        initObservers()
        viewModel.loadGlossaries()
    }

    private fun initObservers() {
        viewModel.glossaries.observe(this, Observer {
            glossaryAdapter.setData(it)
        })
    }
}