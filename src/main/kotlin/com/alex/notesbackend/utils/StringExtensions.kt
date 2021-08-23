package com.alex.notesbackend.utils

fun String.toSortPair(): Pair<String,Boolean> {
    return when (startsWith("-")) {
        true -> substring(1, length) to false
        false -> this to true
    }
}