package com.example.wealthtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.wealthtracker.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = WealthPagerAdapter(this)
        binding.gcLiViewpager.adapter = adapter

        binding.gcLiViewpager.orientation = ViewPager2.ORIENTATION_VERTICAL

        val tabTitles = listOf("შეყვანა", "ანალიტიკა", "პროფილი")
        val tabIcons = listOf(
            R.drawable.ic_input,
            R.drawable.ic_analytics,
            R.drawable.ic_profile
        )

        TabLayoutMediator(binding.gcLiTabLayout, binding.gcLiViewpager) { tab, position ->
            tab.text = tabTitles[position]
            tab.setIcon(tabIcons[position])
        }.attach()
    }
}
