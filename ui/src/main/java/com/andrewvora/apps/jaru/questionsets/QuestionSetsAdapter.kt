package com.andrewvora.apps.jaru.questionsets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrewvora.apps.domain.models.QuestionSet
import com.andrewvora.apps.jaru.R
import kotlinx.android.synthetic.main.item_set.view.*


class QuestionSetsAdapter
constructor(
    private val callback: (QuestionSet) -> Unit
) : RecyclerView.Adapter<QuestionSetsAdapter.ViewHolder>() {

    private var questionSets: List<QuestionSet> = emptyList()

    fun setData(items: List<QuestionSet>) {
        this.questionSets = items.toList()
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return questionSets.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_set,
            parent,
            false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questionSets[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(set: QuestionSet) {
            itemView.setOnClickListener {
                callback(questionSets[adapterPosition])
            }
            itemView.set_title.text = set.title
            itemView.set_subtitle.text = set.description
        }
    }
}