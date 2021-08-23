package com.alex.notesbackend.exception

class UnauthorizedException(override val message: String) : RuntimeException(message)