package com.dietrich.psiuapikt.controller.appointment

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("messages")
class MessageController {
    @GetMapping
    @PreAuthorize("hasAnyAuthority('CHAT_API')")
    fun test(): String {
        return "test"
    }
}