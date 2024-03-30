package com.example.components

import io.ktor.server.html.*
import kotlinx.html.*
import kotlinx.html.impl.DelegatingMap
import kotlin.collections.setOf

class LayoutTemplate: Template<HTML> {
    val title_ = Placeholder<TITLE>()
    var navbar = HashMap<String, String>()
    var active = ""
    val content = Placeholder<FlowContent>()
    var boosted = false

    constructor(navbar: HashMap<String, String> = HashMap(), active: String = "/", boosted: Boolean = false) {
        this.navbar = navbar
        this.active = active
        this.boosted = boosted
    }

    override fun HTML.apply() {
        if (boosted) {
            head {
                title { insert(title_) }
                link(rel = "stylesheet", href = "/static/styles/app.css", type = "text/css")
                script(src = "https://unpkg.com/htmx.org@1.9.11") {}
            }
        }
        body {
            classes = setOf("bg-black text-white h-dvh w-dvw")
            id = "main-content"
            if (navbar.isNotEmpty()) {
                header (classes="grid grid-cols-3 font-bold") {
                    nav (classes="col-start-2 flex justify-center gap-3 text-2xl") {
                        for ((key, value) in navbar) {
                            if (key == active) {
                                span(classes = "underline rounded-xl") { +value }
                            } else {
                                a {
                                    href = key
                                    classes = setOf("hover:bg-black/15 rounded-xl")
                                    attributes["hx-boost"] = "true"
                                    attributes["hx-target"] = "#main-content"
                                    +value
                                }
                            }
                        }
                    }
                }
            }
            insert(content)
        }
    }
}