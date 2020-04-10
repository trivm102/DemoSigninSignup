package com.example.apiservice

import com.example.apiservice.endpoints.GPUserEndpoint
import com.example.apiservice.endpoints.GPUserEndpointImp
import com.example.apiservice.services.GPUserService
import com.example.apiservice.utils.GPOAuthInterceptor
import com.example.apiservice.utils.GPToken
import com.example.apiservice.utils.GPUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GPApi: Api {

    companion object {

        @JvmStatic val INSTANCE by lazy { GPApi() }

    }

    // Retrofit
    private lateinit var retrofit : Retrofit

    // Services
    private lateinit var userService: GPUserService

    // Endpoints
    private lateinit var userEndpoint: GPUserEndpoint

    override val user: GPUserEndpoint
        get() = userEndpoint

    init {
        initRetrofit()
        initServices()
        initEndpoints()
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GPUrl.BASE)
            .client(initOkHttpClient())
            .build()
    }

    private fun initOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(GPOAuthInterceptor("Bearer", GPToken.INSTANCE.getToken()))
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        return  httpClient.build()
    }

    private fun initServices() {
        userService = retrofit.create(GPUserService::class.java)
    }

    private fun initEndpoints() {
        userEndpoint = GPUserEndpointImp(userService)
    }

}

