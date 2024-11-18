package com.nbs.cornerdetectiondimagequality.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.ActivityLoginBinding
import com.nbs.cornerdetectiondimagequality.presentation.dashboard.DashboardActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = Session.getInstance(application.session)
        val viewModel = ViewModelProvider(this, LoginViewModelFactory(session))[LoginViewModel::class.java]

        viewModel.pin.observe(this) { pin ->
            if (pin != null) {
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                Log.d("PIN", "PIN: $pin")
            }
        }

        binding.buttonContinue.setOnClickListener {
            val pin = binding.pin.text.toString()
            lifecycleScope.launch {
                if (pin.isNotEmpty()) {
                    viewModel.saveSession(pin)
                    Log.d("PIN", "PIN: $pin")
                } else {
                    Toast.makeText(this@LoginActivity, "PIN tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    Log.d("PIN", "PIN kosong")
                }
            }
        }
    }
}