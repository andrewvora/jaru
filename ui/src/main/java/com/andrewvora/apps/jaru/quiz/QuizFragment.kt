package com.andrewvora.apps.jaru.quiz

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrewvora.apps.domain.models.QuestionType
import com.andrewvora.apps.jaru.R
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelFragment
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.element_toolbar.*
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : ViewModelFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel::class.java)
    }

    private val quizQuestionAdapter by lazy {
        QuizQuestionAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initObservers()

        val setId = arguments?.getString(EXTRA_SET_ID) ?: ""
        viewModel.loadQuestions(setId)
    }

    private fun initViews() {
        val parent = activity as AppCompatActivity
        parent.setSupportActionBar(toolbar)
        parent.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        parent.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24dp)

        quiz_bottom_app_bar.replaceMenu(R.menu.quiz_bottom_menu)
        quiz_bottom_app_bar.setNavigationOnClickListener { view ->
            when (view.id) {
                R.id.menu_skip -> {}
                R.id.menu_hint -> {}
            }
        }

        mic_fab.hide()
        mic_fab.setOnClickListener {
            TODO()
        }

        val llm = object: LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false) {

            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        question_view_pager.layoutManager = llm
        question_view_pager.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val currentItem = llm.findFirstVisibleItemPosition()
                when (quizQuestionAdapter.questions.getOrNull(currentItem)?.type) {
                    QuestionType.MULTIPLE_CHOICE -> mic_fab.hide()
                    else -> mic_fab.show()
                }
            }
        })
        question_view_pager.adapter = quizQuestionAdapter
        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(question_view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.quiz_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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

    private fun initObservers() {
        viewModel.questionSet.observe(this, Observer {
            quizQuestionAdapter.questions = it.questions
        })
    }

    companion object {
        private const val EXTRA_SET_ID = "questionSetId"

        fun create(setId: String): QuizFragment {
            return QuizFragment().apply {
                val args = Bundle()
                args.putString(EXTRA_SET_ID, setId)
                arguments = args
            }
        }
    }
}