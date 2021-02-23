package com.tavrida.models

import kotlinx.serialization.Serializable

@Serializable
data class CustomerReading(val customerId: String, val reading: Int)