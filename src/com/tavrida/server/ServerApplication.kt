package com.tavrida.server

import com.tavrida.models.Customer
import com.tavrida.models.CustomerReading
import com.tavrida.server.ServerApplication.Companion.formatter.formatAsDate
import com.tavrida.utils.Timestamp
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import kotlinx.html.*
import java.text.SimpleDateFormat
import java.util.*

class ServerApplication<TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration>(
    val db: CustomerReadingDB,
    port: Int,
    val wait: Boolean,
    factory: ApplicationEngineFactory<TEngine, TConfiguration>
) : AutoCloseable {
    private val server = embeddedServer(factory, port = port, module = { serverModule() })

    fun start() = this.also {
        server.start(wait)
    }

    fun stop(gracePeriodMillis: Long, timeoutMillis: Long) {
        server.stop(gracePeriodMillis, timeoutMillis)
    }

    override fun close() {
        stop(1000, 1000)
    }

    private companion object {
        object formatter {
            const val TIMESTAMP_FORMAT = "dd.MM.yyyy HH:mm"
            val dateFormatter = SimpleDateFormat(TIMESTAMP_FORMAT, Locale.US)
            fun Long.formatAsDate() = dateFormatter.format(this)
        }

        fun HTML.readingsView(readings: List<Pair<CustomerReading, Customer?>>) {
            head {
                title() {
                    +"Показания"
                }
            }
            body {
                a(href = "/customers") { +"Потребители" }
                table {
                    thead {
                        tr {
                            th { +"Потребитель" }
                            th { +"Показания" }
                            th { +"Дата" }
                        }
                    }
                    tbody {
                        for ((reading, customer) in readings) {
                            tr {
                                td { +(customer?.name ?: "Не найден!") }
                                td { +reading.reading.toString() }
                                td { +reading.dateTime.formatAsDate() }
                            }
                        }
                    }
                }
            }
        }

        fun HTML.customersView(customers: List<Customer>) {
            body {
                a(href = "/readings") { +"Показания" }
                table {
                    thead {
                        tr {
                            th { +"#" }
                            th { +"Потребитель" }
                        }
                    }
                    tbody {
                        for (customer in customers) {
                            tr {
                                td { +customer.id.toString() }
                                td { +customer.name }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun Application.serverModule() {
        install(ContentNegotiation) { json() }

        routing {
            route("/") {
                get {
                    call.respondHtml {
                        readingsView(db.allReadingsWithCustomer())
                    }
                }
            }
            route("/readings") {
                get {
                    call.respondHtml {
                        readingsView(db.allReadingsWithCustomer())
                    }
                }
            }
            route("/customers") {
                get {
                    call.respondHtml {
                        customersView(db.allCustomers())
                    }
                }
            }
            route("/api") {
                route("/customerReading") {
                    get {
                        call.respond(db.allReadings())
                    }
                    post {
                        val reading = call.receive<CustomerReading>()
                        val id = db.save(reading)
                        call.respond(id)
                    }
                }
            }
        }

    }
}
