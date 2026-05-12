package com.example.demop4app.di

import com.example.demop4app.db.DatabaseProvider
import com.example.demop4app.platform.DeviceInfo
import com.example.demop4app.repository.NotesRepository
import com.example.demop4app.repository.SqlDelightNotesRepository
import com.example.demop4app.settings.SettingsRepository
import com.example.demop4app.settings.SettingsViewModel
import com.example.demop4app.viewmodel.NotesViewModel
import org.koin.dsl.module

val dataModule = module {
    single { DatabaseProvider.getDatabase(get()) }

    single<NotesRepository> {
        SqlDelightNotesRepository(get())
    }

    single { SettingsRepository(get()) }

    single { DeviceInfo() }
}

val viewModelModule = module {
    factory { NotesViewModel(get()) }
    factory { SettingsViewModel(get()) }
}

val appModules = listOf(
    dataModule,
    viewModelModule
)