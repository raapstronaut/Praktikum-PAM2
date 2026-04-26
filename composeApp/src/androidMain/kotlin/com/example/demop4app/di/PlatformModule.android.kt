package com.example.demop4app.di

import android.content.Context
import com.example.demop4app.db.DatabaseDriverFactory
import com.example.demop4app.platform.NetworkMonitor
import com.example.demop4app.settings.SettingsFactory
import org.koin.dsl.module

fun platformModule(context: Context) = module {
    single<Context> { context }

    single { DatabaseDriverFactory(get()) }
    single { SettingsFactory(get()) }
    single { get<SettingsFactory>().createSettings() }

    single { NetworkMonitor(get()) }
}