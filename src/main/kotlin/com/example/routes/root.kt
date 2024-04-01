package com.example.routes

import com.example.components.*
import kotlinx.html.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import io.ktor.server.request.uri

fun Route.root_page() {
    get("/") {
        val boosted = call.request.headers["hx-boosted"]
        val navbar = hashMapOf("/" to "Home", "/about" to "About", "/contact" to "Contact")
        val active = call.request.uri
        println("active: %s, boosted: %s".format(active, boosted))
        call.respondHtmlTemplate(LayoutTemplate(navbar = navbar, active = active, boosted = boosted != "true")) {
            title_ { +"Home" }
            content {
                p { +"Welcome to the home page!" }
            }
        }
    }
}