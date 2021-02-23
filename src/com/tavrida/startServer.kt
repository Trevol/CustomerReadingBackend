package com.tavrida

import com.tavrida.models.CustomerReading
import com.tavrida.server.ServerApplication
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking

fun main() {
    ServerApplication(8080, wait = false).start().use{
        /*val server = embeddedServer(Netty, port = 8080, module = Application::mymodule)
            .apply { start(wait = false) }*/

        runBlocking {
            HttpClient(CIO) {
                install(JsonFeature)
            }.use { client ->
                client.get<List<CustomerReading>>("http://localhost:8080/api/customerReading")
                    .apply {
                        println(this)
                    }

                client.get<CustomerReading>("http://localhost:8080/api/customerReading/123-456")
                    .apply {
                        println(this)
                    }

                client.get<String>("http://localhost:8080/")
                    .apply {
                        println(this)
                    }
            }
        }
    }

}

/*
fun Application.mymodule() {
    install(ContentNegotiation) { json() }
    routing {
        route("/") {
            get {
                call.respond("Root html!!!")
            }
        }
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
        }
    }
}*/
