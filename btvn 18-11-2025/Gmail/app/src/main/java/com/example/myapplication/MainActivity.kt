package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewEmails)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Tạo dữ liệu mẫu giống Gmail
        val emails = listOf(
            Email(
                senderName = "Edurila.com",
                subject = "$19 Only (First 10 spots) - Bestselling...",
                preview = "Are you looking to Learn Web Design...",
                time = "12:34 PM",
                avatarColor = Color.parseColor("#5E97F6")
            ),
            Email(
                senderName = "Chris Abad",
                subject = "Help make Campaign Monitor better",
                preview = "Let us know your thoughts! No Images...",
                time = "11:22 AM",
                avatarColor = Color.parseColor("#EF6C00")
            ),
            Email(
                senderName = "Tuto.com",
                subject = "8h de formation gratuite et les nouvea...",
                preview = "Photoshop, SEO, Blender, CSS, WordPre...",
                time = "11:04 AM",
                avatarColor = Color.parseColor("#8BC34A")
            ),
            Email(
                senderName = "support",
                subject = "Société Ovh : suivi de vos services - hp...",
                preview = "SAS OVH - http://www.ovh.com 2 rue K...",
                time = "10:26 AM",
                avatarColor = Color.parseColor("#90A4AE")
            ),
            Email(
                senderName = "Matt from Ionic",
                subject = "The New Ionic Creator Is Here!",
                preview = "Announcing the all-new Creator, built...",
                time = "9:48 AM",
                avatarColor = Color.parseColor("#9CCC65")
            ),
            Email(
                senderName = "Facebook",
                subject = "Your weekly activity summary",
                preview = "See what happened this week on Facebook...",
                time = "Yesterday",
                avatarColor = Color.parseColor("#3F51B5")
            ),
            Email(
                senderName = "Twitter",
                subject = "New notifications from your network",
                preview = "You have 5 new followers and 3 mentions...",
                time = "Yesterday",
                avatarColor = Color.parseColor("#00ACC1")
            ),
            Email(
                senderName = "LinkedIn",
                subject = "People are looking at your profile",
                preview = "Your profile had 15 views this week...",
                time = "2 days ago",
                avatarColor = Color.parseColor("#0077B5")
            )
        )

        recyclerView.adapter = EmailAdapter(emails)
    }
}