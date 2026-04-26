package com.example.demop4app.db

object DatabaseProvider {
    private var database: NotesDatabase? = null

    fun getDatabase(driverFactory: DatabaseDriverFactory): NotesDatabase {
        return database ?: NotesDatabase(
            driverFactory.createDriver()
        ).also { database = it }
    }
}