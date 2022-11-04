package com.alex.moviebackend.repository.mapping

import com.alex.moviebackend.repository.api.user.ApiModelUserGet
import com.alex.moviebackend.repository.database.user.DbModelUser

// from database to api

fun Iterable<DbModelUser>.toApiModelGet() = map { it.toApiModelGet() }

fun DbModelUser.toApiModelGet() = ApiModelUserGet(id, username, createdAt)