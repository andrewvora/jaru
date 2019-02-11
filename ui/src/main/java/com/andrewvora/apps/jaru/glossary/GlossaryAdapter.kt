package com.andrewvora.apps.jaru.glossary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrewvora.apps.domain.models.Glossary
import com.andrewvora.apps.jaru.R
import kotlinx.android.synthetic.main.item_glossary.view.*

class GlossaryAdapter
constructor(private val callback: (Glossary) -> Unit): RecyclerView.Adapter<GlossaryAdapter.ViewHolder>() {

    private var glossaries: List<Glossary> = emptyList()

    fun setData(glossaries: List<Glossary>) {
        this.glossaries = glossaries
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return glossaries.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_glossary,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(glossaries[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(glossary: Glossary) {
            itemView.setOnClickListener {
                callback(glossaries[adapterPosition])
            }
            itemView.glossary_title.text = glossary.title
            itemView.glossary_subtitle.text = glossary.subtitle
        }
    }
}