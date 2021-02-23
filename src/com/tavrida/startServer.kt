package com.tavrida

import com.tavrida.client.CustomerReadingApiClient
import com.tavrida.models.CustomerReading
import com.tavrida.server.ServerApplication
import kotlinx.coroutines.runBlocking


fun main() {
    ServerApplication(8080, wait = true).start()
}