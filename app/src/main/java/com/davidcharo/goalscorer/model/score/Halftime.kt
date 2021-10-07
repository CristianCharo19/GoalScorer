package com.davidcharo.goalscorer.model.score


import com.google.gson.annotations.SerializedName

data class Halftime(
    @SerializedName("away")
    val away: Int?,
    @SerializedName("home")
    val home: Int?
)