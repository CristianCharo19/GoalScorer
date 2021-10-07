package com.davidcharo.goalscorer.model.rating


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Standing(
    @SerializedName("all")
    val all: All?,
    @SerializedName("away")
    val away: Away?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("form")
    val form: String?,
    @SerializedName("goalsDiff")
    val goalsDiff: Int?,
    @SerializedName("group")
    val group: String?,
    @SerializedName("home")
    val home: Home?,
    @SerializedName("points")
    val points: Int?,
    @SerializedName("rank")
    val rank: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("team")
    val team: Team?,
    @SerializedName("update")
    val update: String?
) : Serializable