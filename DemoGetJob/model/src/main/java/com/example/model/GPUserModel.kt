package com.example.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GPUserModel {

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}