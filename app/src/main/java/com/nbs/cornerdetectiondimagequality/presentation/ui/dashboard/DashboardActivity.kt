package com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.ActivityDashboardBinding
import com.nbs.cornerdetectiondimagequality.presentation.component.DashboardListAdapter
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.DashboardViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory

class DashboardActivity : AppCompatActivity() {
    lateinit var binding : ActivityDashboardBinding
    private val dashboardViewModel by viewModels<DashboardViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.dashboard)

        val adapter = DashboardListAdapter()
        binding.recyclerViewHistory.adapter = adapter


        binding.toggleButton.addOnButtonCheckedListener{ toggleButton, checkedId, isChecked ->
            when (checkedId) {
                 binding.buttonAll.id -> {
                        adapter.submitList(dashboardViewModel.getAllData.value)
                }
                binding.buttonSuccess.id -> {
                    adapter.submitList(dashboardViewModel.getSuccessData.value)
                }
                binding.buttonFailed.id -> {
                    adapter.submitList(dashboardViewModel.getFailedData.value)
                }
            }
        }
    }
}