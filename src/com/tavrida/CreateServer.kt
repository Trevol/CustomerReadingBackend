package com.tavrida

import com.tavrida.server.CustomerReadingDB
import com.tavrida.server.ServerApplication
import io.ktor.server.cio.*
import java.io.File

fun createServer(wait: Boolean, dbFilesLocation: File? = null): ServerApplication<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
    val dbFilesLocation = dbFilesLocation ?: File("db_files")
    val db = CustomerReadingDB(dbFilesLocation)
    return ServerApplication(db, 8080, wait = wait, factory = CIO)
}