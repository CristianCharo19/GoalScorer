package com.davidcharo.goalscorer.model.rating


import com.google.gson.annotations.SerializedName

data class GoalsX(
    @SerializedName("against")
    val against: Int?,
    @SerializedName("for")
    val forX: Int?
)