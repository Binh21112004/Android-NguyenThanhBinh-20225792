package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SectionAdapter(private val sections: List<AppSection>) : 
    RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sectionTitle: TextView = view.findViewById(R.id.sectionTitle)
        val sponsoredLabel: TextView = view.findViewById(R.id.sponsoredLabel)
        val dotSeparator: TextView = view.findViewById(R.id.dotSeparator)
        val appsRecyclerView: RecyclerView = view.findViewById(R.id.appsRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sections[position]
        holder.sectionTitle.text = section.title
        
        if (section.isSponsored) {
            holder.sponsoredLabel.visibility = View.VISIBLE
            holder.dotSeparator.visibility = View.VISIBLE
        } else {
            holder.sponsoredLabel.visibility = View.GONE
            holder.dotSeparator.visibility = View.GONE
        }
        
        // Setup horizontal RecyclerView for apps
        holder.appsRecyclerView.layoutManager = LinearLayoutManager(
            holder.itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        holder.appsRecyclerView.adapter = AppAdapter(section.apps)
    }

    override fun getItemCount() = sections.size
}
