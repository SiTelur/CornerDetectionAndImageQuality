package com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    lateinit var binding : ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.dashboard)

        binding.toggleButton.addOnButtonCheckedListener{ toggleButton, checkedId, isChecked ->
            when (checkedId) {
                 binding.buttonAll.id -> {
                        Log.d("ToggleButton", "onCreate: ALL")

                }
                binding.buttonSuccess.id -> {
                    if (isChecked) {
                        Log.d("ToggleButton", "onCreate: SUCCESS")
                    }
                }
                binding.buttonFailed.id -> {
                    if (isChecked) {
                        Log.d("ToggleButton", "onCreate: FAILED")
                    }
                }
            }
        }
    }
}