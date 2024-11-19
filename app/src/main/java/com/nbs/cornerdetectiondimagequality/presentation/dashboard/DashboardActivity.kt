package com.nbs.cornerdetectiondimagequality.presentation.dashboard

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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