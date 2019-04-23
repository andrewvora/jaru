package com.andrewvora.apps.jaru.questionsets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.andrewvora.apps.jaru.R
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelFragment
import com.andrewvora.apps.jaru.quiz.QuizActivity
import kotlinx.android.synthetic.main.fragment_sets.*


class QuestionSetsFragment : ViewModelFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(QuestionSetsViewModel::class.java)
    }

    private val setsAdapter by lazy {
        QuestionSetsAdapter { set ->
            activity?.let {
                startActivity(QuizActivity.start(it, set.id))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sets_recycler_view.layoutManager = GridLayoutManager(activity, 2)
        sets_recycler_view.adapter = setsAdapter

        initObservers()

        loading_indicator.visibility = View.VISIBLE
    }

    private fun initObservers() {
        viewModel.questionSets.observe(this, Observer {
            loading_indicator.visibility = View.GONE
            setsAdapter.setData(it)
        })
        viewModel.error.observe(this, Observer {
            loading_indicator.visibility = View.GONE
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }
}