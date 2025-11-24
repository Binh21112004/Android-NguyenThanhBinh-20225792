package com.example.myapplication

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecommendedAdapter(private val apps: List<App>) : 
    RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder>() {

    class RecommendedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recommendedIcon: View = view.findViewById(R.id.recommendedIcon)
        val recommendedName: TextView = view.findViewById(R.id.recommendedName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommended, parent, false)
        return RecommendedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        val app = apps[position]
        holder.recommendedName.text = app.name
        
        // Set icon color
        val drawable = holder.recommendedIcon.background as GradientDrawable
        drawable.setColor(app.iconColor)
    }

    override fun getItemCount() = apps.size
}
