package com.example.apiservice.responses
import com.example.model.GPUserModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GPUserResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean = false

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("http_code")
    @Expose
    var code: Long? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("data")
    @Expose
    var data: DataResponse? = null


    class DataResponse {
        @SerializedName("user")
        @Expose
        var user: GPUserModel? = null
    }
}