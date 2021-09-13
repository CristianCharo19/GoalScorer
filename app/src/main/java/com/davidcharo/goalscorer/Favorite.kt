package com.davidcharo.goalscorer

import java.io.Serializable

class Favorite(
    var id: String? = null,
    var team: String? = null,
    var league: String? = null,
    var urlPicture: String? = null
) : Serializable
