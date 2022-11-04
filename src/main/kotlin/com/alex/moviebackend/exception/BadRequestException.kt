package com.alex.moviebackend.exception

class BadRequestException(override val message: String) : RuntimeException(message)