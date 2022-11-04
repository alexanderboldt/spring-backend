package com.alex.moviebackend.util

import java.time.Instant
import java.util.Date

fun Instant.toDate() = Date.from(this)