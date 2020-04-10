package com.example.apiservice.services

import com.example.apiservice.responses.GPUserResponse
import com.example.apiservice.utils.GPUrl
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GPUserService {

    @POST(GPUrl.LOGIN)
    fun userLogin(@Body user: HashMap<String, Any>): Call<GPUserResponse>

    @POST(GPUrl.SIGNUP)
    fun userSignup(@Body user: HashMap<String, Any>): Call<GPUserResponse>
}