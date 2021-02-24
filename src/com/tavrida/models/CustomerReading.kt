package com.tavrida.models

import kotlinx.serialization.Serializable

@Serializable
data class CustomerReading(val customerId: Int, val reading: Int, val dateTime: Long)