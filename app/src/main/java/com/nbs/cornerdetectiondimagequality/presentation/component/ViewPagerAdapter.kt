package com.nbs.cornerdetectiondimagequality.presentation.component

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard.fragment.AllFragment
import com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard.fragment.FailureFragment
import com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard.fragment.SuccessFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllFragment()
            1 -> SuccessFragment()
            2 -> FailureFragment()
            else -> AllFragment() // Default case
        }
    }

    override fun getItemCount(): Int = 3
}