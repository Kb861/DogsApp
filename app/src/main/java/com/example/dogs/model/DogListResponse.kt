package com.example.dogs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DogListResponse {
    @SerializedName("status")
    @Expose

    val status: String? = null

    @SerializedName("message")
    @Expose
    val message: List<String>? = null
}