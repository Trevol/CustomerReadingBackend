package com.tavrida

import com.tavrida.client.CustomerReadingApiClient
import com.tavrida.models.CustomerReading
import com.tavrida.server.ServerApplication
import kotlinx.coroutines.runBlocking


fun main() {
    runBlocking {
        CustomerReadingApiClient("localhost", 8080).use { client ->
            // client.get().log()
            // client.get("123-456").log()
            client.post(CustomerReading("88888", 1111))
        }
    }
}