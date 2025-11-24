package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTabs()
        setupRecyclerView()
    }

    private fun setupTabs() {
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("For you"))
        tabLayout.addTab(tabLayout.newTab().setText("Top charts"))
        tabLayout.addTab(tabLayout.newTab().setText("Other devices"))
        tabLayout.addTab(tabLayout.newTab().setText("Kids"))
    }

    private fun setupRecyclerView() {
        val sectionsRecyclerView = findViewById<RecyclerView>(R.id.sectionsRecyclerView)
        sectionsRecyclerView.layoutManager = LinearLayoutManager(this)

        val sections = createSampleData()
        sectionsRecyclerView.adapter = SectionAdapter(sections)
    }

    private fun createSampleData(): List<AppSection> {
        return listOf(
            AppSection(
                title = "Suggested for you",
                isSponsored = true,
                apps = listOf(
                    App(
                        name = "Mech Assemble: Zombie Swarm",
                        category = "Action • Role Playing • Roguelike • Zombie",
                        rating = 4.8,
                        size = "624 MB",
                        iconColor = Color.parseColor("#8B0000")
                    ),
                    App(
                        name = "MU: Hồng Hoá Đạo",
                        category = "Role Playing",
                        rating = 4.8,
                        size = "339 MB",
                        iconColor = Color.parseColor("#C41E3A")
                    ),
                    App(
                        name = "War Inc: Rising",
                        category = "Strategy • Tower defense",
                        rating = 4.9,
                        size = "231 MB",
                        iconColor = Color.parseColor("#4CAF50")
                    )
                )
            ),
            AppSection(
                title = "Recommended for you",
                isSponsored = false,
                apps = listOf(
                    App(
                        name = "Suno - AI Music & Songs",
                        category = "Music & Audio",
                        rating = 4.6,
                        size = "45 MB",
                        iconColor = Color.parseColor("#FF6B35")
                    ),
                    App(
                        name = "Claude by Anthropic",
                        category = "Productivity",
                        rating = 4.7,
                        size = "78 MB",
                        iconColor = Color.parseColor("#F4A261")
                    ),
                    App(
                        name = "DramaBox - Short Dramas",
                        category = "Entertainment",
                        rating = 4.5,
                        size = "92 MB",
                        iconColor = Color.parseColor("#E63946")
                    )
                )
            ),
            AppSection(
                title = "Popular apps",
                isSponsored = false,
                apps = listOf(
                    App(
                        name = "Instagram",
                        category = "Social",
                        rating = 4.2,
                        size = "156 MB",
                        iconColor = Color.parseColor("#E1306C")
                    ),
                    App(
                        name = "TikTok",
                        category = "Entertainment",
                        rating = 4.4,
                        size = "134 MB",
                        iconColor = Color.parseColor("#000000")
                    ),
                    App(
                        name = "WhatsApp",
                        category = "Communication",
                        rating = 4.3,
                        size = "67 MB",
                        iconColor = Color.parseColor("#25D366")
                    )
                )
            ),
            AppSection(
                title = "Gaming apps you might like",
                isSponsored = false,
                apps = listOf(
                    App(
                        name = "PUBG Mobile",
                        category = "Action",
                        rating = 4.1,
                        size = "1.8 GB",
                        iconColor = Color.parseColor("#FF6B00")
                    ),
                    App(
                        name = "Mobile Legends",
                        category = "Action • MOBA",
                        rating = 4.0,
                        size = "890 MB",
                        iconColor = Color.parseColor("#2E5EFF")
                    ),
                    App(
                        name = "Clash of Clans",
                        category = "Strategy",
                        rating = 4.5,
                        size = "245 MB",
                        iconColor = Color.parseColor("#FFD700")
                    )
                )
            ),
            AppSection(
                title = "Productivity boost",
                isSponsored = false,
                apps = listOf(
                    App(
                        name = "Google Drive",
                        category = "Productivity",
                        rating = 4.4,
                        size = "78 MB",
                        iconColor = Color.parseColor("#4285F4")
                    ),
                    App(
                        name = "Microsoft Office",
                        category = "Productivity",
                        rating = 4.5,
                        size = "234 MB",
                        iconColor = Color.parseColor("#D83B01")
                    ),
                    App(
                        name = "Notion",
                        category = "Productivity",
                        rating = 4.6,
                        size = "89 MB",
                        iconColor = Color.parseColor("#000000")
                    )
                )
            )
        )
    }
}