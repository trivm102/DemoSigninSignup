package com.example.getjobapplication.viewmodel

import androidx.databinding.Bindable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.example.apiservice.GPApi

open class SignUpViewModel(private val activity: AppCompatActivity): BaseObservable() {

    @Bindable
    var email = MutableLiveData<String>()

    @Bindable
    var password = MutableLiveData<String>()


    var loading = MutableLiveData<Boolean>()


    fun signup() {
        val username = email.value
        val pass = password.value
        loading.postValue(true)
        GPApi.INSTANCE.user.signup(username, pass, callback = { response, t ->
            loading.postValue(false)
            if (response != null && t == null) {
                val user = response.data?.user
                if (user != null) {
                    val toastMessage = "Success\n"
                    Log.d("TRIVM", toastMessage)

                    Toast.makeText(activity, toastMessage, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(activity, t?.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}


