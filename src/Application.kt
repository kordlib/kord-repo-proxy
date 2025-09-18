package dev.kord.maven.proxy

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

private val githubPackagesUrl = Url("https://maven.pkg.github.com")

private val client = HttpClient {
    defaultRequest {
        // I am 99% sure the username is irrelevant
        basicAuth("pat", Config.GITHUB_TOKEN)
    }
}

fun main() {
    embeddedServer(Netty, port = Config.PORT, host = Config.HOST) { module() }.start(wait = true)
}

fun Application.module() {
    routing {
        get("healthz") {
            call.respond("All good!")
        }

        route("/{...}") {
            handle {
                val url = call.url {
                    val receivedSegments = pathSegments
                    takeFrom(githubPackagesUrl)
                    pathSegments = (Config.GITHUB_REPO + receivedSegments).filter(String::isNotBlank)
                }

                client.prepareRequest(url) {
                    method = call.request.local.method
                }.execute { response ->
                    response.headers.forEach { key, values ->
                        values.forEach { value ->
                            call.response.header(key, value)
                        }
                    }
                    call.respond(response.status, response.bodyAsChannel())
                }
            }
        }
    }
}
