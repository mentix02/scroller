package com.mentix02.scroller.ui

import android.view.View
import android.view.ViewGroup
import com.mentix02.scroller.R
import android.view.LayoutInflater
import com.mentix02.scroller.models.Thought
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_thought.view.*

class ThoughtsAdapter(private val thoughts : List<Thought>): RecyclerView.Adapter<ThoughtsAdapter.ThoughtViewHolder>() {

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThoughtViewHolder {
    return ThoughtViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_thought, parent, false)
    )
}

override fun getItemCount(): Int = thoughts.size

override fun onBindViewHolder(holder: ThoughtViewHolder, position: Int) {

    val thought = thoughts[position]

    holder.view.thoughtText.text = thought.body
    holder.view.thoughtTimestamp.text = thought.timestamp

}

class ThoughtViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
