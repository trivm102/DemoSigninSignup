package com.example.getjobapplication.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apiservice.GPApi

class LoginViewModel(private val activity: AppCompatActivity) : SignUpViewModel(activity) {
    fun login() {
        val username = email.value
        val pass = password.value
        loading.postValue(true)
        GPApi.INSTANCE.user.login(username, pass, callback = { response, t ->
            loading.postValue(false)
            if (response != null && t == null) {
                val user = response.data?.user
                val toastMessage = "Success\n"
                Log.d("TRIVM", toastMessage)

                Toast.makeText(activity, toastMessage, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity, t?.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}