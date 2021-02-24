package com.tavrida.client

import com.tavrida.models.CustomerReading
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*

class CustomerReadingApiClient<T : HttpClientEngineConfig>(
    private val serverHost: String,
    private val serverPort: Int,
    private val apiPath: String = "api/customerReading",
    engineFactory: HttpClientEngineFactory<T>
) : AutoCloseable {

    private val httpClient = HttpClient(engineFactory) {
        install(JsonFeature) //{ serializer = KotlinxSerializer() }
    }

    suspend fun get(): List<CustomerReading> = httpClient.get(
        host = serverHost,
        port = serverPort,
        path = apiPath
    )


    suspend fun get(customerId: String): CustomerReading = httpClient.get(
        host = serverHost,
        port = serverPort,
        path = "$apiPath/$customerId"
    )


    suspend fun post(reading: CustomerReading): String = httpClient.post(
        host = serverHost,
        port = serverPort,
        path = apiPath,
        body = reading
    ) {
        contentType(ContentType.Application.Json)
    }


    override fun close() {
        httpClient.close()
    }
}