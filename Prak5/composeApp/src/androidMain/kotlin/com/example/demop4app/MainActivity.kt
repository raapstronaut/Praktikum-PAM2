package com.example.demop4app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.demop4app.db.DatabaseDriverFactory
import com.example.demop4app.settings.SettingsFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                driverFactory = DatabaseDriverFactory(this),
                settingsFactory = SettingsFactory(this)
            )
        }
    }
}