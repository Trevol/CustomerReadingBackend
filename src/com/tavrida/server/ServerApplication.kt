package com.tavrida.server

import com.tavrida.models.CustomerReading
import com.tavrida.utils.log
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class ServerApplication(val port: Int, val wait: Boolean) : AutoCloseable {
    private val server = embeddedServer(Netty, port = port, module = { serverModule() })

    fun start() = this.also {
        server.start(wait)
    }

    fun stop(gracePeriodMillis: Long, timeoutMillis: Long) {
        server.stop(gracePeriodMillis, timeoutMillis)
    }

    override fun close() {
        stop(1000, 1000)
    }

    private fun Application.serverModule() {
        install(ContentNegotiation) { json() }

        routing {
            route("/") {
                get {
                    call.respond("Root html!!!")
                }
            }
            route("/api") {
                route("/customerReading") {
                    get {
                        call.respond(
                            listOf(
                                CustomerReading("1234", 12346),
                                CustomerReading("12345", 1234446)
                            )
                        )
                    }
                    get("{id}") {
                        val id = call.parameters["id"] ?: "NULL"
                        call.respond(CustomerReading(id, 1234678))
                    }
                    post {
                        val reading = call.receive<CustomerReading>()
                        reading.log("FROM SERVER POST")
                        call.respond(status = HttpStatusCode.Accepted, "Customer stored correctly")
                    }
                }
            }
        }

    }
}
