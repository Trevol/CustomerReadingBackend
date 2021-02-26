package test.kotlin

import com.tavrida.models.Customer
import com.tavrida.models.CustomerReading
import com.tavrida.server.CustomerReadingDB
import com.tavrida.utils.log
import io.jsondb.JsonDBTemplate
import io.jsondb.annotation.Document
import io.jsondb.annotation.Id
import java.io.File
import kotlin.random.Random

fun main() {
    val dbFilesLocation = File("src/test/resources/test_data")
    dbFilesLocation.deleteRecursively()
    val db = CustomerReadingDB(dbFilesLocation)

    assert(db.allCustomers().isEmpty())

    val n_customers = 5
    for (i in 1..n_customers) {
        db.save(Customer(i, "Customer_$i"))
    }
    assert(db.allCustomers().size == n_customers)

    val msInHour = 1000 * 60 * 60 //ms_in_sec * sec_in_min * min_in_hour
    val n_readings = 10

    for (i in 1..n_readings) {
        db.save(
            CustomerReading(
                -1,
                Random.nextInt(1, n_customers + 1),
                i * 1000,
                System.currentTimeMillis() - i * msInHour
            )
        )
    }

    db.allReadingsWithCustomer().also {
        it.log()
        assert(it.size == n_readings)
    }

}
