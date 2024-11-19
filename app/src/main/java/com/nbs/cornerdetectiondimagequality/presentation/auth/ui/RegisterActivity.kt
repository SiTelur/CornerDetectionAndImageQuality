package com.nbs.cornerdetectiondimagequality.presentation.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nbs.cornerdetectiondimagequality.databinding.ActivityRegisterBinding
import com.nbs.cornerdetectiondimagequality.presentation.auth.viewmodel.AuthViewModel
import com.nbs.cornerdetectiondimagequality.presentation.auth.viewmodel.AuthViewModelFactory
import com.nbs.cornerdetectiondimagequality.presentation.dashboard.DashboardActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

<<<<<<< Updated upstream:app/src/main/java/com/nbs/cornerdetectiondimagequality/presentation/auth/LoginActivity.kt
        supportActionBar?.hide()

        val session = Session.getInstance(application.session)
        val viewModel = ViewModelProvider(this, LoginViewModelFactory(session))[LoginViewModel::class.java]

        viewModel.pin.observe(this) { pin ->
            if (pin != null) {
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finish()
                Log.d("PIN", "PIN: $pin")
            }
        }

        binding.buttonContinue.setOnClickListener {
=======
        binding.btnRegister.setOnClickListener {
>>>>>>> Stashed changes:app/src/main/java/com/nbs/cornerdetectiondimagequality/presentation/auth/ui/RegisterActivity.kt
            val pin = binding.pin.text.toString()
            lifecycleScope.launch {
                if (pin.isNotEmpty()) {
                    viewModel.saveSession(pin)
                    startActivity(Intent(this@RegisterActivity, DashboardActivity::class.java))
                    Log.d("PIN", "PIN: $pin")
                } else {
                    Toast.makeText(this@RegisterActivity, "PIN tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    Log.d("PIN", "PIN kosong")
                }
            }
        }
    }

}