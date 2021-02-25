package com.tavrida

import com.tavrida.client.CustomerReadingApiClient
import com.tavrida.models.CustomerReading
import com.tavrida.server.ServerApplication
import com.tavrida.utils.log
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking
import java.lang.Exception


fun main() {
    val serverHost = "localhost"
    // val serverHost = "192.168.195.131"
    runBlocking {
        CustomerReadingApiClient(serverHost, 8080, engineFactory = CIO)
            .use { client ->
                CustomerReading(-1, 88888, 1111, 4444).let {
                    client.post(it).shouldBe(1)
                }

                try {
                    CustomerReading(-2, 88888, 1111, 4444).let {
                        client.post(it).shouldBe(1)
                    }
                } catch (e: Exception) {
                    "error".log("CLIENT")
                }

                CustomerReading(-1, 88888, 1111, 4444).let {
                    client.post(it).shouldBe(2)
                }

                client.get().let {
                    it.size.shouldBe(2)
                }
            }
    }
}