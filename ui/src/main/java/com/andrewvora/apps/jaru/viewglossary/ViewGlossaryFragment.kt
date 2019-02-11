package com.andrewvora.apps.jaru.viewglossary

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrewvora.apps.jaru.R
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelFragment
import kotlinx.android.synthetic.main.element_toolbar.*
import kotlinx.android.synthetic.main.fragment_glossary_viewer.*

class ViewGlossaryFragment : ViewModelFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ViewGlossaryViewModel::class.java)
    }

    private val adapter by lazy {
        GlossaryItemAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_glossary_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initObservers()

        viewModel.loadGlossary(getGlossaryId())
    }

    private fun getGlossaryId(): String {
        return arguments?.getString(EXTRA_GLOSSARY_ID) ?: ""
    }

    private fun initViews() {
        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        glossary_item_recycler_view.layoutManager = LinearLayoutManager(activity)
        glossary_item_recycler_view.adapter = adapter
    }

    private fun initObservers() {
        viewModel.glossary.observe(this, Observer {
            activity?.title = it.title
            adapter.setData(it.items)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val EXTRA_GLOSSARY_ID = "glossaryId"

        fun create(glossaryId: String): ViewGlossaryFragment {
            return ViewGlossaryFragment().apply {
                val args = Bundle()
                args.putString(EXTRA_GLOSSARY_ID, glossaryId)
                arguments = args
            }
        }
    }
}