package com.davidcharo.goalscorer.model.score


import com.google.gson.annotations.SerializedName

data class Penalty(
    @SerializedName("away")
    val away: Any?,
    @SerializedName("home")
    val home: Any?
)