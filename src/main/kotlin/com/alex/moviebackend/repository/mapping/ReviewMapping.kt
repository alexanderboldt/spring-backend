package com.alex.moviebackend.repository.mapping

import com.alex.moviebackend.repository.api.review.ApiModelReviewGet
import com.alex.moviebackend.repository.database.review.DbModelReview

// from database to api

fun Iterable<DbModelReview>.toApiModelGet() = map { it.toApiModelGet() }

fun DbModelReview.toApiModelGet() = ApiModelReviewGet(id, description, createdAt)