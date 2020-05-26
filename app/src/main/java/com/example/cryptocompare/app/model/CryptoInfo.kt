package com.example.cryptocompare.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoInfo (
    @SerializedName("Name")
    @Expose
    val name: String? = null
)
