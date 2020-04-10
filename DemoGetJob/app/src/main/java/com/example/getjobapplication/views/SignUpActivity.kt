package com.example.getjobapplication.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.getjobapplication.R
import com.example.getjobapplication.databinding.ActivitySignUpBinding
import com.example.getjobapplication.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    var signUpViewModel = SignUpViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_sign_up
        )
        binding.apply { this.lifecycleOwner = this@SignUpActivity
                        this.viewModel = signUpViewModel}
    }
}
