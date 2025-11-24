package com.example.myapplication

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(private val emails: List<Email>) :
    RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
        val tvSenderName: TextView = itemView.findViewById(R.id.tvSenderName)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvSubject: TextView = itemView.findViewById(R.id.tvSubject)
        val tvPreview: TextView = itemView.findViewById(R.id.tvPreview)
        val ivStar: ImageView = itemView.findViewById(R.id.ivStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emails[position]

        // Set avatar
        holder.tvAvatar.text = email.senderName.first().toString()
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.OVAL
        drawable.setColor(email.avatarColor)
        holder.tvAvatar.background = drawable

        // Set email details
        holder.tvSenderName.text = email.senderName
        holder.tvTime.text = email.time
        holder.tvSubject.text = email.subject
        holder.tvPreview.text = email.preview

        // Set star icon
        if (email.isStarred) {
            holder.ivStar.setImageResource(android.R.drawable.star_big_on)
        } else {
            holder.ivStar.setImageResource(android.R.drawable.star_big_off)
        }
    }

    override fun getItemCount(): Int = emails.size
}
