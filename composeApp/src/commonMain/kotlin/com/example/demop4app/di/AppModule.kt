package com.example.demop4app.di

import com.example.demop4app.db.DatabaseProvider
import com.example.demop4app.platform.DeviceInfo
import com.example.demop4app.repository.NotesRepository
import com.example.demop4app.settings.SettingsRepository
import com.example.demop4app.settings.SettingsViewModel
import com.example.demop4app.viewmodel.NotesViewModel
import org.koin.dsl.module

val commonModule = module {
    single { DatabaseProvider.getDatabase(get()) }
    single { NotesRepository(get()) }

    single { SettingsRepository(get()) }

    single { DeviceInfo() }

    factory { NotesViewModel(get()) }
    factory { SettingsViewModel(get()) }
}