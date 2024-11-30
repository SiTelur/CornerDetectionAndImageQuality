package com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.ActivityDashboardBinding
import com.nbs.cornerdetectiondimagequality.presentation.component.DashboardListAdapter
import com.nbs.cornerdetectiondimagequality.presentation.component.HistoryAdapter
import com.nbs.cornerdetectiondimagequality.presentation.component.ReminderFragment
import com.nbs.cornerdetectiondimagequality.presentation.component.ViewPagerAdapter
import com.nbs.cornerdetectiondimagequality.presentation.ui.camera.MainActivity
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.DashboardViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory

class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    private val dashboardViewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        supportActionBar?.title = getString(R.string.dashboard)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "All"
                1 -> "Success"
                2 -> "Failed"
                else -> null
            }
        }.attach()

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}