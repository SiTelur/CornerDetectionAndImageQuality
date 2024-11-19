package com.nbs.cornerdetectiondimagequality.presentation.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nbs.cornerdetectiondimagequality.databinding.ActivityRegisterBinding
import com.nbs.cornerdetectiondimagequality.presentation.auth.viewmodel.AuthViewModel
import com.nbs.cornerdetectiondimagequality.presentation.auth.viewmodel.ViewModelFactory
import com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard.DashboardActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}