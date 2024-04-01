package com.example.routes

import com.example.components.*
import kotlinx.html.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import io.ktor.server.request.uri

fun Route.about_page() {
    get("/about") {
        val boosted = call.request.headers["hx-boosted"]
        val navbar = hashMapOf("/" to "Home", "/about" to "About", "/contact" to "Contact")
        val active = call.request.uri
        call.respondHtmlTemplate(LayoutTemplate(navbar = navbar, active = active, boosted = boosted != "true")) {
            title_ { +"About" }
            content {
                p { +"This is the about page." }
            }
        }
    }
}