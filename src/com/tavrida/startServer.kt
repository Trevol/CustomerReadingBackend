package com.tavrida

import com.tavrida.server.CustomerReadingDB
import com.tavrida.server.ServerApplication
import io.ktor.server.cio.*


fun main() {
    val db = CustomerReadingDB()
    ServerApplication(db, 8080, wait = true, factory = CIO).start()
}