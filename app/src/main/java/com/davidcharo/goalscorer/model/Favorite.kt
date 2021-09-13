package com.davidcharo.goalscorer.model

data class Favorite(
    var id: String? = null,
    var team: String? = null,
    var player: String? = null,
    var league: String? = null,
    var urlPicture: String? = null
)