package com.tavrida.server

import com.tavrida.models.Customer
import com.tavrida.models.CustomerReading
import io.jsondb.JsonDBTemplate
import java.io.File

class CustomerReadingDB(dbFilesLocation: File) {
    val jsonDB = JsonDBTemplate(dbFilesLocation.absolutePath, "com.tavrida.models")

    init {
        if (!jsonDB.collectionExists(CustomerReading::class.java)) {
            jsonDB.createCollection(CustomerReading::class.java)
        }
        if (!jsonDB.collectionExists(Customer::class.java)) {
            jsonDB.createCollection(Customer::class.java)
        }
    }

    fun allReadingsWithCustomer(): List<Pair<CustomerReading, Customer>> {
        TODO()
        return listOf()
    }

    fun allReadings(): List<CustomerReading> {
        return listOf()
    }

    fun save(reading: CustomerReading) {
        TODO()
    }
}