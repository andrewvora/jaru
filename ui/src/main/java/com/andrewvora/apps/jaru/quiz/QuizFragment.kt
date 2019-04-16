package com.andrewvora.apps.jaru.quiz

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrewvora.apps.domain.models.Answer
import com.andrewvora.apps.domain.models.Question
import com.andrewvora.apps.domain.models.QuestionType
import com.andrewvora.apps.jaru.R
import com.andrewvora.apps.jaru.di.Injector
import com.andrewvora.apps.jaru.di.viewmodel.ViewModelFragment
import com.andrewvora.apps.jaru.tts.TextToSpeechHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.element_toolbar.*
import kotlinx.android.synthetic.main.fragment_quiz.*
import javax.inject.Inject

class QuizFragment : ViewModelFragment(), QuizQuestionAdapter.Callback {

    @Inject
    lateinit var textToSpeechHelper: TextToSpeechHelper

    private val viewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel::class.java)
    }

    private val quizQuestionAdapter: QuizQuestionAdapter by lazy {
        QuizQuestionAdapter(this@QuizFragment)
    }

    private val layoutManager: LinearLayoutManager by lazy { object: LinearLayoutManager(
            activity,
            HORIZONTAL,
            false) {

            override fun canScrollHorizontally(): Boolean {
                return scrollable
            }
        }
    }

    private var scrollable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.inject(this)
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
        parent.supportActionBar?.title = null

        mic_fab.hide()
        mic_fab.setOnClickListener {
            textToSpeechHelper.speechToText(this)
        }

        question_pager_view.layoutManager = layoutManager
        question_pager_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val currentItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                when (quizQuestionAdapter.questions.getOrNull(currentItem)?.type) {
                    QuestionType.MULTIPLE_CHOICE -> mic_fab.post { mic_fab.hide() }
                    else -> mic_fab.post {
                        if (textToSpeechHelper.hasSpeechRecognizer()) {
                            mic_fab.show()
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollable = false
                }
            }
        })
        question_pager_view.adapter = quizQuestionAdapter
        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(question_pager_view)

        quiz_bottom_app_bar.replaceMenu(R.menu.quiz_bottom_menu)
        quiz_bottom_app_bar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_skip -> {
                    goToNextQuestion()
                }
                R.id.menu_hint -> {
                    quizQuestionAdapter.showHint = !quizQuestionAdapter.showHint
                }
            }
            return@setOnMenuItemClickListener true
        }
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
            R.id.menu_play -> {
                playCurrentQuestion()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun playCurrentQuestion() {
        val position = layoutManager.findFirstCompletelyVisibleItemPosition()
        val questionText = quizQuestionAdapter.questions[position].text
        textToSpeechHelper.textToSpeech(questionText)
    }

    private fun initObservers() {
        viewModel.questionSet.observe(this, Observer {
            quizQuestionAdapter.questions = it.questions
        })
        viewModel.answerResult.observe(this, Observer { result ->
            activity?.let {
                val answersMsg = result.answers.joinToString(separator = "\n") { answer ->
                    answer.text
                }
                AlertDialog.Builder(it)
                    .setTitle(if (result.wasCorrect) {
                        R.string.correct_answer_title
                    } else {
                        R.string.wrong_answer_title
                    })
                    .setMessage(resources.getQuantityString(R.plurals.correct_answers_message,
                        result.answers.size,
                        answersMsg.trim()))
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, null)
                    .setOnDismissListener {
                        goToNextQuestion()
                    }
                    .create()
                    .show()
            }
        })
    }

    private fun goToNextQuestion() {
        scrollable = true

        val nextPos = layoutManager.findFirstVisibleItemPosition() + 1
        if (nextPos >= quizQuestionAdapter.itemCount) {
            onQuizCompleted()
        } else {
            question_pager_view.smoothScrollToPosition(nextPos)
        }
    }

    private fun onQuizCompleted() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.completed_quiz_title)
                .setMessage(getString(R.string.completed_quiz_message, viewModel.getScore()))
                .setPositiveButton(android.R.string.ok, null)
                .setOnDismissListener { _ ->
                    it.finish()
                }
                .setCancelable(false)
                .create()
                .show()
        }
    }

    override fun onAnswer(question: Question, answer: Answer) {
        viewModel.answerQuestion(question = question, answer = answer)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        textToSpeechHelper.getTextFromBundle(data).takeIf { it.isNotBlank() }?.let {
            val currentQuestion = layoutManager.findFirstCompletelyVisibleItemPosition()
            quizQuestionAdapter.userAnswers[currentQuestion] = Answer(text = it)
            quizQuestionAdapter.notifyItemChanged(currentQuestion)
        }
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