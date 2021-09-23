package com.davidcharo.goalscorer.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Extratime(
    @SerializedName("away")
    val away: Any?,
    @SerializedName("home")
    val home: Any?
)