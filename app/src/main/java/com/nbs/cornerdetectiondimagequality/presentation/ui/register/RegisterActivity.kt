package com.nbs.cornerdetectiondimagequality.presentation.ui.register

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nbs.cornerdetectiondimagequality.databinding.ActivityRegisterBinding
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.AuthViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory
import com.ozcanalasalvar.otp_view.view.OtpView

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

        viewModel.isRegistered.observe(this){ isRegistered ->
            finish()
        }

        binding.textfieldPin.setTextChangeListener(object : OtpView.ChangeListener{
            override fun onTextChange(value: String, completed: Boolean) {
                if (completed){
                    viewModel.savePin(value)
                }
            }
        })



    }
}