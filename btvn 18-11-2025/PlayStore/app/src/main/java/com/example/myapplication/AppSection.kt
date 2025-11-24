package com.example.myapplication

data class AppSection(
    val title: String,
    val isSponsored: Boolean,
    val apps: List<App>
)
