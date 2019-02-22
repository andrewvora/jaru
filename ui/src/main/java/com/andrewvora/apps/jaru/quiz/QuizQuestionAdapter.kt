package com.andrewvora.apps.jaru.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrewvora.apps.domain.models.Question
import com.andrewvora.apps.domain.models.QuestionType
import com.andrewvora.apps.jaru.R
import kotlinx.android.synthetic.main.item_answer.view.*
import kotlinx.android.synthetic.main.item_question_free_form.view.*
import kotlinx.android.synthetic.main.item_question_multi_choice.view.*

class QuizQuestionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var questions: List<Question> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutResId = when (viewType) {
            INPUT_VIEW_HOLDER -> R.layout.item_question_free_form
            else -> R.layout.item_question_multi_choice
        }
        val view = layoutInflater.inflate(layoutResId, parent, false)
        return when (viewType) {
            INPUT_VIEW_HOLDER -> TextInputViewHolder(view)
            else -> MultiChoiceViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(questions[position].type) {
            QuestionType.SINGLE_INPUT,
            QuestionType.FREE_FORM,
            QuestionType.UNKNOWN -> INPUT_VIEW_HOLDER
            QuestionType.MULTIPLE_CHOICE -> MULTI_CHOICE_VIEW_HOLDER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextInputViewHolder -> { holder.bind(questions[position]) }
            is MultiChoiceViewHolder -> { holder.bind(questions[position]) }
        }
    }

    inner class TextInputViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(question: Question) {
            itemView.question_text?.text = question.text
            itemView.question_transcript?.text = question.transcript
        }
    }

    inner class MultiChoiceViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(question: Question) {
            itemView.multi_choice_question_text.text = question.text
            itemView.multi_choice_question_transcript.text = question.transcript
            itemView.answer_grid_layout.removeAllViews()
            question.answers.forEach {  answer ->
                val answerView = LayoutInflater.from(itemView.context).inflate(
                    R.layout.item_answer,
                    itemView.answer_grid_layout,
                    false)

                answerView.answer_text.text = answer.text

                itemView.answer_grid_layout.addView(answerView)
            }
            for (i in 0..(5 - question.answers.size)) {
                val fillerView = getFillerAnswerView()
                itemView.answer_grid_layout.addView(fillerView)
            }
        }

        private fun getFillerAnswerView(): View {
            return LayoutInflater.from(itemView.context).inflate(
                R.layout.item_answer,
                itemView.answer_grid_layout,
                false)
                .apply {
                    isEnabled = false
                    visibility = View.INVISIBLE
                }
        }
    }

    companion object {
        const val INPUT_VIEW_HOLDER = 1
        const val MULTI_CHOICE_VIEW_HOLDER = 2
    }
}