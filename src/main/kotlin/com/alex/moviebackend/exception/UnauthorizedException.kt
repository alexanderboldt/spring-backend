package com.alex.moviebackend.exception

class UnauthorizedException(override val message: String) : RuntimeException(message)