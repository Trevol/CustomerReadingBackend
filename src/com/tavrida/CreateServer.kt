package com.tavrida

import com.tavrida.server.CustomerReadingDB
import com.tavrida.server.ServerApplication
import io.ktor.server.cio.*
import java.io.File

fun createServer(wait: Boolean): ServerApplication<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
    val dbFilesLocation = File("")
    val db = CustomerReadingDB(dbFilesLocation)
    return ServerApplication(db, 8080, wait = wait, factory = CIO)
}