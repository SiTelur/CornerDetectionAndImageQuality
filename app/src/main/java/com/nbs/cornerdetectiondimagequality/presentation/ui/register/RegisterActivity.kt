package com.nbs.cornerdetectiondimagequality.presentation.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.ActivityRegisterBinding
import com.nbs.cornerdetectiondimagequality.presentation.ui.login.LoginActivity
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.AuthViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory
import com.ozcanalasalvar.otp_view.view.OtpView
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        binding.textfieldPin.setTextChangeListener(object : OtpView.ChangeListener{
            override fun onTextChange(value: String, completed: Boolean) {
                if (completed){
                    lifecycleScope.launch {
                        val dialog = showDialog()
                        delay(3000)
                        dialog.dismiss()
                        viewModel.savePin(value)
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        })
    }

    private fun showDialog() : AlertDialog {
        return MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.alert_title)
            setMessage(R.string.alert_message)
        }.show()
    }
}