package com.example.getjobapplication.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.getjobapplication.R
import com.example.getjobapplication.databinding.ActivityMainBinding
import com.example.getjobapplication.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var loginViewModel = LoginViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.apply { this.lifecycleOwner = this@MainActivity
                        this.viewModel = loginViewModel}
    }
}
