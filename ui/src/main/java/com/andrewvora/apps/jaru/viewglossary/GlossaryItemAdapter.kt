package com.andrewvora.apps.jaru.viewglossary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrewvora.apps.domain.models.GlossaryItem
import com.andrewvora.apps.jaru.R
import kotlinx.android.synthetic.main.item_glossary_item.view.*

class GlossaryItemAdapter : RecyclerView.Adapter<GlossaryItemAdapter.ViewHolder>() {

    private var items: List<GlossaryItem> = emptyList()

    fun setData(items: List<GlossaryItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_glossary_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: GlossaryItem) {
            itemView.item_text.text = item.text
            itemView.item_transcript.text = item.transcript
        }
    }
}