package com.example.apiservice.utils

import JWT
import android.util.Log

class GPToken {

    private lateinit var token: String


    companion object {
        @JvmStatic val INSTANCE by lazy { GPToken() }

    }

    init {
        initToken()
    }

    fun initToken() {
        val claim = HashMap<String, Any?>()
        claim.put("ts", System.currentTimeMillis()/1000)
        claim.put("client_id", "1bba2eb7-6edb-4204-858d-67f1838c78c9")
        val jwt = JWT(claim)
        val sig = jwt.sign(Key.PRIVATE_KEY)
        Log.d("TRIVM", "Token: " + sig)
        token = sig
    }

    fun getToken(): String {
        return token
    }

    fun updateToken(refreshToken: String) {
        token = refreshToken
    }

    object Key {
        val PRIVATE_KEY = "LS0tLS1CRUdJTiBQUklWQVRFIEtFWS0tLS0tCk1JR0hBZ0VBTUJNR0J5cUdTTTQ5QWdFR0NDcUdTTTQ5QXdFSEJHMHdhd0lCQVFRZ2hGQ0RvWEt0MXkwM3JNWWcKeENsM1ZjdWdHeDBadEhOZ2dOWnppK0JLV04yaFJBTkNBQVRjQytUaGNYb29BUTg1VTZBckVwdm9ycSs4L0c1SAo5NmxqMy92dTlYcFRWNC90NlphaUF0d2tLQXVneGlETFJPR2ZuVXJ2NmhNR0gvWGJEMXlzVXVzegotLS0tLUVORCBQUklWQVRFIEtFWS0tLS0tCg=="
    }
}