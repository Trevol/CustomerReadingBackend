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
            client.post(CustomerReading(-1, 88888, 1111, System.currentTimeMillis()))
        }
    }
}