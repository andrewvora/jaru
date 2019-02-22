package com.andrewvora.apps.jaru.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andrewvora.apps.jaru.R

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        if (savedInstanceState == null) {
            val setId = intent.getStringExtra(EXTRA_SET_ID) ?: ""
            val quizFragment = QuizFragment.create(setId)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, quizFragment, "quizFragment")
                .commit()
        }
    }

    companion object {
        private const val EXTRA_SET_ID = "questionSetId"

        fun start(context: Context, setId: String): Intent {
            return Intent(context, QuizActivity::class.java).apply {
                putExtra(EXTRA_SET_ID, setId)
            }
        }
    }
}