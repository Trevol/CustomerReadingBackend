package com.tavrida.models

import io.jsondb.annotation.Document
import io.jsondb.annotation.Id
import kotlinx.serialization.Serializable

@Serializable
@Document(collection = "customers", schemaVersion = "1.0")
data class Customer(
    @Id
    val id: Int,
    val name: String
)
