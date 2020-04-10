package com.example.apiservice.endpoints

import com.example.apiservice.responses.GPUserResponse
import com.example.apiservice.services.GPUserService
import com.example.apiservice.utils.GPProperties
import com.example.model.GPUserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GPUserEndpointImp(val serviceGP: GPUserService): GPUserEndpoint {
    override fun login(
        email: String?,
        password: String?,
        callback: (GPUserResponse?, Throwable?) -> Unit?
    ) {

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {

            val user = GPUserModel()
            user.email = email
            user.password = password
            val dataObject = HashMap<String, Any>()
            dataObject[GPProperties.USER] = user

            serviceGP.userLogin(dataObject).enqueue(object: Callback<GPUserResponse> {
                override fun onResponse(call: Call<GPUserResponse>, response: Response<GPUserResponse>) {
                    callback.invoke(response.body(), null)
                }

                override fun onFailure(call: Call<GPUserResponse>, t: Throwable) {
                    callback.invoke(null, t)
                }

            })
        } else {
            callback.invoke(null, Throwable("email or password is empty"))
        }

    }

    override fun signup(
        email: String?,
        password: String?,
        callback: (GPUserResponse?, Throwable?) -> Unit?
    ) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {

            val user = GPUserModel()
            user.email = email
            user.password = password
            val dataObject = HashMap<String, Any>()
            dataObject[GPProperties.USER] = user

            serviceGP.userSignup(dataObject).enqueue(object: Callback<GPUserResponse> {
                override fun onResponse(call: Call<GPUserResponse>, response: Response<GPUserResponse>) {
                    callback.invoke(response.body(), null)
                }

                override fun onFailure(call: Call<GPUserResponse>, t: Throwable) {
                    callback.invoke(null, t)
                }

            })
        } else {
            callback.invoke(null, Throwable("email or password is empty"))
        }
    }
}