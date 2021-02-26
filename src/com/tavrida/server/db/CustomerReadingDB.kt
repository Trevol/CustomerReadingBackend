package com.tavrida.server

import com.tavrida.models.Customer
import com.tavrida.models.CustomerReading
import com.tavrida.server.db.schema.CustomerReadingRec
import com.tavrida.server.db.schema.CustomerRec
import com.tavrida.server.db.schema.IdGenRec
import com.tavrida.utils.toMap
import io.jsondb.JsonDBTemplate
import java.io.File

class CustomerReadingDB(dbFilesLocation: File) {
    val jsonDb = JsonDBTemplate(dbFilesLocation.absolutePath, "com.tavrida.server.db.schema")

    init {
        jsonDb.init()
    }

    fun allReadingsWithCustomer(): List<Pair<CustomerReading, Customer?>> {
        val customerRecs = jsonDb.findAllCustomers().toMap { it.id }
        return jsonDb.findAllReadings()
            .apply {
                sortByDescending { it.dateTime }
            }
            .map { r ->
                r.map() to customerRecs[r.customerId]?.map()
            }
    }

    fun allReadings(): List<CustomerReading> {
        return jsonDb.findAllReadings().map { it.map() }
    }

    fun allCustomers(): List<Customer> {
        return jsonDb.findAllCustomers().map { it.map() }
    }

    fun save(customer: Customer): Int {
        val rec = customer.map()
        jsonDb.upsert<CustomerRec>(rec)
        return rec.id
    }

    fun save(reading: CustomerReading): Int {
        val readingRec = reading.map()
        readingRec.id = nextId()
        jsonDb.insert<CustomerReadingRec>(readingRec)
        return readingRec.id
    }

    fun nextId(): Int {
        val idGen = jsonDb.idGen()
        idGen.value++
        jsonDb.upsert<IdGenRec>(idGen)
        return idGen.value
    }

    private companion object {
        fun JsonDBTemplate.init() {
            checkAndCreateCollection(CustomerReadingRec::class.java)
            checkAndCreateCollection(CustomerRec::class.java)
            checkAndCreateCollection(IdGenRec::class.java)
        }

        fun <T> JsonDBTemplate.checkAndCreateCollection(entityClass: Class<T>) {
            if (collectionExists(entityClass)) return
            createCollection(entityClass)
        }

        fun JsonDBTemplate.idGen(): IdGenRec {
            return findById(1, IdGenRec::class.java)
                ?: IdGenRec().apply {
                    id = 1
                    value = 0
                }
        }

        fun JsonDBTemplate.findAllCustomers() = findAll(CustomerRec::class.java)
        fun JsonDBTemplate.findAllReadings() = findAll(CustomerReadingRec::class.java)
        fun CustomerReadingRec.map() = CustomerReading(id, customerId, reading, dateTime)
        fun CustomerReading.map() = CustomerReadingRec(id, customerId, reading, dateTime)
        fun CustomerRec.map() = Customer(id, name)
        fun Customer.map() = CustomerRec(id, name)
    }


}
