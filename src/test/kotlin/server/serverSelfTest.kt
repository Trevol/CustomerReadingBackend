package com.tavrida

import com.tavrida.client.CustomerReadingApiClient
import com.tavrida.models.CustomerReading
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking


fun main() {
    createServer(wait = false).start().use {
        runBlocking {
            CustomerReadingApiClient("localhost", 8080, engineFactory = CIO)
                .use { client ->
                    client.post(CustomerReading(1, 88888, 1111, System.currentTimeMillis()))
                }
        }
    }

}