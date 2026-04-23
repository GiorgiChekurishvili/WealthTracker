package com.example.wealthtracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WealthPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InputFragment()
            1 -> AnalyticsFragment()
            2 -> ProfileFragment()
            else -> InputFragment()
        }
    }
}