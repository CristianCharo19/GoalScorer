package com.davidcharo.goalscorer.model


import com.google.gson.annotations.SerializedName

data class Goals(
    @SerializedName("away")
    val away: Int?,
    @SerializedName("home")
    val home: Int?
)