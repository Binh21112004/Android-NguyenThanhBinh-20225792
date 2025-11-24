package com.example.myapplication

data class Email(
    val senderName: String,
    val subject: String,
    val preview: String,
    val time: String,
    val avatarColor: Int,
    val isStarred: Boolean = false
)
