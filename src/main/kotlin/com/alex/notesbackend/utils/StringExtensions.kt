package com.alex.notesbackend.utils

fun String.getBearerToken() = this.drop(7)

fun String.toSortPairs(): List<Pair<String,Boolean>> {
    val sortPairs = mutableListOf<Pair<String,Boolean>>()

    split(",").forEach { split ->
        sortPairs += when (split.startsWith("-")) {
            true -> split.substring(1, split.length) to false
            false -> split to true
        }
    }

    return sortPairs
}