package com.davidcharo.goalscorer.model


import com.google.gson.annotations.SerializedName

data class TeamList(
    @SerializedName("teams")
    val teams: List<Team>?
)