package com.nbs.cornerdetectiondimagequality.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nbs.cornerdetectiondimagequality.databinding.ActivityLoginBinding
import com.nbs.cornerdetectiondimagequality.presentation.auth.viewmodel.AuthViewModel
import com.nbs.cornerdetectiondimagequality.presentation.auth.viewmodel.ViewModelFactory
import com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard.DashboardActivity
import com.nbs.cornerdetectiondimagequality.presentation.ui.register.RegisterActivity
import com.ozcanalasalvar.otp_view.view.OtpView

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isRegistered.observe(this){ isRegistered ->
            if (!isRegistered){
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

        }

        binding.textfieldPin.setTextChangeListener(object : OtpView.ChangeListener {
            override fun onTextChange(value: String, completed: Boolean) {
                if (completed) {
                    val pinPassword = value
                    viewModel.pin.observe(this@LoginActivity) {
                        if (pinPassword == value) {
                            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                            finish()
                            Log.d("PIN", "PIN: $value")
                        } else {
                            Toast.makeText(this@LoginActivity, "PIN salah", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("PIN", "PIN salah")
                        }
                    }
                }
            }
        })

//        binding.btnLogin.setOnClickListener {
//            viewModel.pin.observe(this) { pin ->
//                val pinPassword = binding.pin.text.toString()
//                if (pinPassword.isEmpty()) {
//                    Toast.makeText(this, "PIN tidak boleh kosong", Toast.LENGTH_SHORT).show()
//                    Log.d("PIN", "PIN kosong")
//                }
//                if (pinPassword == pin) {
//                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
//                    Log.d("PIN", "PIN: $pin")
//                } else {
//                    Toast.makeText(this, "PIN salah", Toast.LENGTH_SHORT).show()
//                    Log.d("PIN", "PIN salah")
//                }
//            }
//        }

        binding.btnRegister.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }
    }
}