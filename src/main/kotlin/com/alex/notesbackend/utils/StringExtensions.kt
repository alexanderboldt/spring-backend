package com.alex.notesbackend.utils

fun String.getBearerToken() = this.drop(7)

fun String.toSortPair(): Pair<String,Boolean> {
    return when (startsWith("-")) {
        true -> substring(1, length) to false
        false -> this to true
    }
}