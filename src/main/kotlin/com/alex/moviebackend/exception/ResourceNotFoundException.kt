package com.alex.moviebackend.exception

class ResourceNotFoundException(override val message: String) : RuntimeException(message)