package com.tavrida

import com.tavrida.client.CustomerReadingApiClient
import com.tavrida.models.CustomerReading
import com.tavrida.server.ServerApplication
import io.ktor.server.cio.*
import kotlinx.coroutines.runBlocking


fun main() {
    ServerApplication(8080, wait = false, factory = CIO).start().use {
        runBlocking {
            CustomerReadingApiClient("localhost", 8080, engineFactory = io.ktor.client.engine.cio.CIO).use { client ->
                // client.get().log()
                // client.get("123-456").log()
                client.post(CustomerReading("88888", 1111))
            }
        }
    }

}