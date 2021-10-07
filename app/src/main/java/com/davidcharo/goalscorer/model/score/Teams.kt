package com.davidcharo.goalscorer.model.score


import com.davidcharo.goalscorer.model.score.Away
import com.davidcharo.goalscorer.model.score.Home
import com.google.gson.annotations.SerializedName

data class Teams(
    @SerializedName("away")
    val away: Away?,
    @SerializedName("home")
    val home: Home?
)