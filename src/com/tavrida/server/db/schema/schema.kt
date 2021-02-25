package com.tavrida.server.db.schema

import io.jsondb.annotation.Document
import io.jsondb.annotation.Id

@Document(collection = "id_record", schemaVersion = "1.0")
class IdGenRec {
    @Id
    var id = 0
    var value = 0
}

@Document(collection = "customer_readings", schemaVersion = "1.0")
class CustomerReadingRec(
    @Id
    var id: Int,
    var customerId: Int,
    var reading: Int,
    var dateTime: Long
) {
    constructor(): this(0, 0, 0, 0)
}

@Document(collection = "customers", schemaVersion = "1.0")
class CustomerRec(
    @Id
    var id: Int,
    var name: String
) {
    constructor() : this(0, "")
}