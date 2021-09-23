package com.davidcharo.goalscorer.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Fixture(
    @SerializedName("date")
    val date: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("periods")
    val periods: Periods?,
    @SerializedName("referee")
    val referee: String?,
    @SerializedName("status")
    val status: Status?,
    @SerializedName("timestamp")
    val timestamp: Int?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("venue")
    val venue: Venue?
)