package com.tavrida.client

import com.tavrida.models.CustomerReading
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*

class CustomerReadingApiClient(val serverHost: String, val serverPort: Int, engineFactory: CIO = CIO) {
    val httpClient = HttpClient(engineFactory) {
        install(JsonFeature)
    }

    fun post(reading: CustomerReading) {
        TODO()
    }
}