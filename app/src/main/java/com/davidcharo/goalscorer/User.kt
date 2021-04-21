package com.davidcharo.goalscorer

import java.io.Serializable

data class User(
    var name : String? = null,
    var email: String? = null,
    var password: String? = null
) : Serializable