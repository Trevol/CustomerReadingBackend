package com.tavrida

import com.tavrida.client.CustomerReadingApiClient
import com.tavrida.models.CustomerReading
import com.tavrida.server.ServerApplication
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking


fun main() {
    val serverHost = "localhost"
    // val serverHost = "192.168.195.131"
    runBlocking {
        CustomerReadingApiClient(serverHost, 8080, engineFactory = CIO).use { client ->
            // client.get().log()
            // client.get("123-456").log()
            client.post(CustomerReading(88888, 1111))
        }
    }
}