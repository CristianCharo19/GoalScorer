package com.davidcharo.goalscorer.model.score


import com.davidcharo.goalscorer.model.score.Extratime
import com.davidcharo.goalscorer.model.score.Fulltime
import com.davidcharo.goalscorer.model.score.Halftime
import com.davidcharo.goalscorer.model.score.Penalty
import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName("extratime")
    val extratime: Extratime?,
    @SerializedName("fulltime")
    val fulltime: Fulltime?,
    @SerializedName("halftime")
    val halftime: Halftime?,
    @SerializedName("penalty")
    val penalty: Penalty?
)