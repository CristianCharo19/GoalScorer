package com.davidcharo.goalscorer.model


import com.google.gson.annotations.SerializedName

data class EventsList(
    @SerializedName("events")
    val events: List<Event>?
)