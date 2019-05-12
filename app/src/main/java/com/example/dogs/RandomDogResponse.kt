package com.example.dogs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RandomDogResponse {
    @SerializedName("status")

    @Expose

    val status: String? = null

    @SerializedName("message")

    @Expose

    val message: String? = null

    var id: Int = 0

    var name: String? = null
}