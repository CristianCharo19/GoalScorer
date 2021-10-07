package com.davidcharo.goalscorer.model.rating


import com.google.gson.annotations.SerializedName

data class Away(
    @SerializedName("draw")
    val draw: Int?,
    @SerializedName("goals")
    val goals: GoalsX?,
    @SerializedName("lose")
    val lose: Int?,
    @SerializedName("played")
    val played: Int?,
    @SerializedName("win")
    val win: Int?
)