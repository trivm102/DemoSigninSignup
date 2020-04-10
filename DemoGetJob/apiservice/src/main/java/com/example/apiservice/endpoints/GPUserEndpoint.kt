package com.example.apiservice.endpoints

import com.example.apiservice.responses.GPUserResponse


interface GPUserEndpoint {
    fun login(email: String?, password: String?, callback:(GPUserResponse?, Throwable?) -> Unit?)
    fun signup(email: String?, password: String?, callback:(GPUserResponse?, Throwable?) -> Unit?)
}