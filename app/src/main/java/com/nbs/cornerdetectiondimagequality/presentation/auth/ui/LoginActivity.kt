package com.nbs.cornerdetectiondimagequality.presentation.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nbs.cornerdetectiondimagequality.databinding.ActivityLoginBinding
import com.nbs.cornerdetectiondimagequality.presentation.auth.viewmodel.AuthViewModel
import com.nbs.cornerdetectiondimagequality.presentation.auth.viewmodel.AuthViewModelFactory
import com.nbs.cornerdetectiondimagequality.presentation.dashboard.DashboardActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            viewModel.pin.observe(this) { pin ->
                val pinPassword = binding.pin.text.toString()
                if (pinPassword.isEmpty()) {
                    Toast.makeText(this, "PIN tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    Log.d("PIN", "PIN kosong")
                }
                if (pinPassword == pin) {
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    Log.d("PIN", "PIN: $pin")
                } else {
                    Toast.makeText(this, "PIN salah", Toast.LENGTH_SHORT).show()
                    Log.d("PIN", "PIN salah")
                }
            }
        }

        binding.btnRegister.setOnClickListener { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }
    }
}