package com.davidcharo.goalscorer.model.rating


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(
    @SerializedName("league")
    val league: League?
) : Serializable