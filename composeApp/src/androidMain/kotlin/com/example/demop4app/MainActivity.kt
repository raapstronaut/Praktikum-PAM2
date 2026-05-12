package com.example.demop4app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.demop4app.di.appModules
import com.example.demop4app.di.platformModule
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            modules(
                appModules + platformModule(applicationContext)
            )
        }

        setContent {
            App()
        }
    }
}