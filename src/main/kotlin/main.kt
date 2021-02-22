package main.kotlin

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
import kotlinx.serialization.Serializable

fun main() {
    val server = embeddedServer(Netty, port = 8080, module = Application::mymodule)
        .apply { start(wait = false) }

    runBlocking {
        val client = HttpClient(CIO) {
            install(JsonFeature)
        }

        client.get<List<CustomerReading>>("http://localhost:8080/customerReading")
            .apply {
                println(this)
            }

        client.get<CustomerReading>("http://localhost:8080/customerReading/123-456")
            .apply {
                println(this)
            }

        client.get<String>("http://localhost:8080/")
            .apply {
                println(this)
            }

        client.close()
        server.stop(1000, 1000)
    }
}

@Serializable
data class CustomerReading(val customerId: String, val reading: Int)

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
}