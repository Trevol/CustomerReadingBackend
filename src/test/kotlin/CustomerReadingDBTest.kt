package test.kotlin

import com.tavrida.models.Customer
import com.tavrida.models.CustomerReading
import com.tavrida.server.CustomerReadingDB
import com.tavrida.utils.log
import io.jsondb.JsonDBTemplate
import io.jsondb.annotation.Document
import io.jsondb.annotation.Id
import java.io.File

fun main() {
    val dbFilesLocation = File("src/test/resources/CustomerReadingDBTest")
    dbFilesLocation.deleteRecursively()
    val db = CustomerReadingDB(dbFilesLocation)

    assert(db.allCustomers().isEmpty())

    db.save(Customer(1, "Customer_1"))
    db.save(Customer(2, "Customer_2"))
    assert(db.allCustomers().size == 2)

    db.save(CustomerReading(-1, 1, 111, 11111))
    db.save(CustomerReading(-1, 3, 333, 33333))

    db.allReadingsWithCustomer().log()
}
