package com.davidcharo.goalscorer.model.score


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Away(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("winner")
    val winner: Any?
)