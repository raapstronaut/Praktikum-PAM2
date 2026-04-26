package com.example.demop4app.settings

import com.russhwolf.settings.ObservableSettings

expect class SettingsFactory {
    fun createSettings(): ObservableSettings
}