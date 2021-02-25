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
import java.lang.Exception

class ServerApplication<TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration>(
    val db: CustomerReadingDB,
    port: Int,
    val wait: Boolean,
    factory: ApplicationEngineFactory<TEngine, TConfiguration>
) : AutoCloseable {
    private val server = embeddedServer(factory, port = port, module = { serverModule() })

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
                    // db.allReadingsWithCustomer()
                    call.respond("Root html!!!")
                }
            }
            route("/api") {
                route("/customerReading") {
                    get {
                        call.respond(db.allReadings())
                    }
                    post {
                        val reading = call.receive<CustomerReading>()
                        if (reading.id==-2){
                            throw Exception("TEST-ERROR")
                        }
                        val id = db.save(reading)
                        call.respond(id)
                        // call.respond(status = HttpStatusCode.Accepted, "Customer stored correctly")
                    }
                }
            }
        }

    }
}
