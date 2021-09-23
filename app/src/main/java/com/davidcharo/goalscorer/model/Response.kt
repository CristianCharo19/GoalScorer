package com.davidcharo.goalscorer.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Response(
    @SerializedName("fixture")
    val fixture: Fixture?,
    @SerializedName("goals")
    val goals: Goals?,
    @SerializedName("league")
    val league: League?,
    @SerializedName("score")
    val score: Score?,
    @SerializedName("teams")
    val teams: Teams?
) : Serializable