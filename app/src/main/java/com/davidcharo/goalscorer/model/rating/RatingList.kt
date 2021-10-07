package com.davidcharo.goalscorer.model.rating


import com.google.gson.annotations.SerializedName

data class RatingList(
    @SerializedName("errors")
    val errors: List<Any>?,
    @SerializedName("get")
    val `get`: String?,
    @SerializedName("paging")
    val paging: Paging?,
    @SerializedName("parameters")
    val parameters: Parameters?,
    @SerializedName("response")
    val rating: List<Rating>?,
    @SerializedName("results")
    val results: Int?
)