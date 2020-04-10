package com.example.apiservice

import com.example.apiservice.endpoints.GPUserEndpoint

interface Api {
    val user: GPUserEndpoint
}