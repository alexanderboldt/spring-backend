package com.alex.notesbackend.features.root

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RootController {

    @GetMapping("/")
    fun get() = "Welcome!!!"
}