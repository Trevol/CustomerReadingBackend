package com.tavrida

import com.tavrida.client.CustomerReadingApiClient
import com.tavrida.models.CustomerReading
import com.tavrida.utils.log
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking
import java.io.File


fun main() {
    val dbFilesLocation = File("src/test/resources/CustomerReadingDBTest")
    dbFilesLocation.deleteRecursively()

    createServer(wait = false, dbFilesLocation).start().use {
        runBlocking {
            CustomerReadingApiClient("localhost", 8080, engineFactory = CIO)
                .use { client ->
                    CustomerReading(-1, 88888, 1111, 4444).let {
                        client.post(it).shouldBe(1)
                    }
                    client.get().let {
                        it.size.shouldBe(1)
                    }
                }
        }
    }

    dbFilesLocation.deleteRecursively()
}

fun <T> T.shouldBe(expected: T) {
    if (this != expected) {
        throw AssertionError("Expected: $expected. Actual: $this")
    }
}