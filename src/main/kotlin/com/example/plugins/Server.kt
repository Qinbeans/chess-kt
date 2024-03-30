package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.http.content.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import java.io.File
import io.ktor.server.html.*
import io.ktor.server.request.uri
import kotlinx.html.*
import com.example.components.*

fun Application.configureServer() {
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
    install(ContentNegotiation) {
        json()
    }
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        get("/") {
            // pull hx-boosted from header
            val boosted = call.request.headers["hx-boosted"]
            val navbar = hashMapOf("/" to "Home", "/about" to "About", "/contact" to "Contact")
            val active = call.request.uri
            println("active "+active+" navbar "+navbar)
            call.respondHtmlTemplate(LayoutTemplate(navbar = navbar, active = active, boosted = boosted == "true")) {
                title_ { +"Home" }
                content {
                    p { +"Welcome to the home page!" }
                }
            }
        }
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
        get("/contact") {
            val boosted = call.request.headers["hx-boosted"]
            val navbar = hashMapOf("/" to "Home", "/about" to "About", "/contact" to "Contact")
            val active = call.request.uri
            call.respondHtmlTemplate(LayoutTemplate(navbar = navbar, active = active, boosted = boosted != "true")) {
                title_ { +"Contact" }
                content {
                    p { +"WIP contact page." }
                }
            }
        }
        staticFiles("/static", File("assets"))
    }
}
