package com.example.myapplication

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(private val apps: List<App>) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appIcon: View = view.findViewById(R.id.appIcon)
        val appName: TextView = view.findViewById(R.id.appName)
        val appCategory: TextView = view.findViewById(R.id.appCategory)
        val appRating: TextView = view.findViewById(R.id.appRating)
        val appSize: TextView = view.findViewById(R.id.appSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.appName.text = app.name
        holder.appCategory.text = app.category
        holder.appRating.text = app.rating.toString()
        holder.appSize.text = app.size
        
        // Set icon color
        val drawable = holder.appIcon.background as GradientDrawable
        drawable.setColor(app.iconColor)
    }

    override fun getItemCount() = apps.size
}
