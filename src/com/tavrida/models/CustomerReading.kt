package com.tavrida.models

import io.jsondb.annotation.Document
import io.jsondb.annotation.Id
import kotlinx.serialization.Serializable

@Serializable
@Document(collection = "customer_readings", schemaVersion = "1.0")
data class CustomerReading(
    @Id
    val id: Int,
    val customerId: Int,
    val reading: Int,
    val dateTime: Long
)