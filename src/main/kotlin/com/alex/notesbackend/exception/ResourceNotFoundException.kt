package com.alex.notesbackend.exception

class ResourceNotFoundException(override val message: String) : RuntimeException(message)